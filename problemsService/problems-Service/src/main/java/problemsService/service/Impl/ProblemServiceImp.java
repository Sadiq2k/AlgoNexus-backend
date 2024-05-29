package problemsService.service.Impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import problemsService.Exception.DuplicateValueException;
import problemsService.Exception.InternalServerException;
import problemsService.FiengClient.SubmissionFeignClient;
import problemsService.FiengClient.TestCaseFeignClient;
import problemsService.Model.Dto.AcceptCase;
import problemsService.Model.Dto.RejectCase;
import problemsService.Model.Dto.SubmissionDTO;
import problemsService.Model.Dto.TestCase;
import problemsService.Model.Enum.Submission;
import problemsService.Model.Judge0.error.SandboxCodeExecutionError;
import problemsService.Model.Judge0.error.SandboxCompileError;
import problemsService.Model.Judge0.error.SandboxError;
import problemsService.Model.Judge0.error.SandboxStandardError;
import problemsService.Model.entities.Difficulties;
import problemsService.Model.request.*;
import problemsService.Model.response.Judge0TokenResponse;
import problemsService.Model.response.JudgeSubmitResponse;
import problemsService.Model.response.ProblemVerificationResponse;
import problemsService.Repository.DifficultyRepository;
import problemsService.Repository.ProblemRepository;
import problemsService.Model.entities.Problem;
import problemsService.service.ProblemService;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
@Slf4j
public class ProblemServiceImp implements ProblemService {

    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private TestCaseFeignClient testCaseFeignClient;
    @Autowired
    private SubmissionFeignClient submissionFeignClient;
    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private  WebClient judgeWebClient;
    
    @Autowired
    private DifficultyRepository difficultyRepository;


    @Override
    public Page<Problem> getAllProblems(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page,size);
        Page<Problem> allProblem = problemRepository.findAll(pageable);
        List<Problem> allTopics = allProblem.getContent();
        Page<Problem> responsePage = new PageImpl<>(allTopics, pageable, allProblem.getTotalElements());
        return responsePage;

    }

    @Override
    public Optional<Problem> getProblemById(String problemId) {

        Optional<Problem> problemOptional = problemRepository.findById(problemId);

        if (problemOptional.isPresent()) {
            Problem problem = problemOptional.get();
            try {
                List<SubmissionDTO> submissionSolution = submissionFeignClient.getSubmissionSolution(problemId);
                if (submissionSolution != null) {
                    problem.setSubmissionDTO(submissionSolution);
                }
            } catch (Exception e) {
                log.info("Error fetching submission solution: " + e.getMessage());
            }
            return Optional.of(problem);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void addProblem(AddProblemRequest addProblemRequest, HttpServletRequest request) {
        Long generatedProblemNo = generateProblemNo();

        Problem problem = Problem.builder()
                .title(addProblemRequest.getTitle())
                .problemNo(generatedProblemNo)
                .category(addProblemRequest.getCategory())
                .driverCode(addProblemRequest.getDriverCode())
                .solutionTemplate(addProblemRequest.getSolutionTemplate())
                .languageId(addProblemRequest.getLanguageId())
                .createdAt(LocalDateTime.now())
                .examples(addProblemRequest.getExamples())
                .difficulty(getDifficulty(addProblemRequest.getDifficulty()))
                .description(addProblemRequest.getDescription())
                .constraints(addProblemRequest.getConstraints())
                .acceptanceRate(0F)
                .build();
        problem = problemRepository.save(problem);
        AddTestCaseRequest addTestCaseRequest = new AddTestCaseRequest(problem.getProblemId() ,addProblemRequest.getTestCases());
        String authorizationHeader = request.getHeader("Authorization");
        testCaseFeignClient.addTestCases(addTestCaseRequest,authorizationHeader);
    }

    private String getDifficulty(String id) {
        Optional<Difficulties> optionalDifficulty = difficultyRepository.findById(id);

        if (optionalDifficulty.isPresent()) {
            Difficulties difficulty = optionalDifficulty.get();
            difficulty.setTotalCount(difficulty.getTotalCount() + 1);
            difficultyRepository.save(difficulty);
            return difficulty.getName();
        }
        return null;
    }


    private Long generateProblemNo() {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "problemNo")).limit(1);
        Problem lastProblem = mongoOperations.findOne(query, Problem.class);
        return lastProblem != null ? (lastProblem.getProblemNo() != null ? lastProblem.getProblemNo() + 1 : 1L) : 1L;
    }

    @Override
    public ProblemVerificationResponse verifyProblem(ProblemVerificationRequest request) {
        Queue<AcceptCase> acceptedCases = new ConcurrentLinkedQueue<>();
        Queue<RejectCase> rejectedCases = new ConcurrentLinkedQueue<>();
        Queue<Double> timeTaken = new ConcurrentLinkedQueue<>();
        Queue<Double> memoryTaken = new ConcurrentLinkedQueue<>();
        Queue<RuntimeException> exception = new ConcurrentLinkedQueue<>();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<CompletableFuture<Void>> futures = request.getTestCases().stream()
                .map(test -> CompletableFuture.runAsync(() -> {
                    try {
                        JudgeSubmitResponse submissionResponse = executeAndGetResponse(request, test);
                        handleSubmissionResponse(submissionResponse, test, acceptedCases, rejectedCases, timeTaken, memoryTaken);
                    } catch (RuntimeException e) {
                        handleExecutionError(e);
                    }
                }, executor))
                .toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            allOf.get(2, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException e){
            throw new SandboxError(e, e.getMessage());
        }

        if (!exception.isEmpty()) {
            throw exception.peek();
        }
        if (!rejectedCases.isEmpty()) {
            log.info("Some test cases did not match the expected output");
            return new ProblemVerificationResponse("Expected output does not match with test cases given",
                    Submission.REJECTED.name(), List.copyOf(acceptedCases), List.copyOf(rejectedCases));
        } else {
            log.info("All test cases accepted!");
            return new ProblemVerificationResponse("Accepted",
                    Submission.ACCEPTED.name(), List.copyOf(acceptedCases), List.copyOf(rejectedCases));
        }
    }

    private void handleExecutionError(RuntimeException e) {
        log.error(e.getMessage());
        throw e;
    }

    @Override
    public ResponseEntity<ProblemVerificationResponse> badRequestWithMessageAndStatus(String message, Submission submission) {
        return ResponseEntity.badRequest().body(ProblemVerificationResponse.builder()
                .message(message)
                .status(submission.name())
                .build());
    }

    @Override
    public ResponseEntity<ProblemVerificationResponse> internalServerErrorWithMessage(String message) {
        return ResponseEntity.internalServerError().body(ProblemVerificationResponse.builder()
                .message(message)
                .build());
    }

    @Override
    public boolean invalidTestCases(ProblemVerificationRequest request) {
        List<TestCase> testCases = request.getTestCases();
        return testCases == null || testCases.isEmpty() || testCases.stream()
                .anyMatch(tc -> tc.getTestCaseInput().isEmpty() || tc.getExpectedOutput().isEmpty());
    }

    @Override
    public void checkDuplicateTitleExistsOrNot(String title) {
        if (problemRepository.existsByTitleIgnoreCase(title)) {
            throw new DuplicateValueException("There's already an existing issue with that title");
        }
    }


    private JudgeSubmitResponse executeAndGetResponse(ProblemVerificationRequest problemVerificationRequest, TestCase test) {
        Judge0Request request = new Judge0Request();
        request.setSource_code(problemVerificationRequest.getSourceCode());
        request.setLanguage_id(problemVerificationRequest.getLanguageId());
        request.setStdin(Base64.getEncoder().encodeToString(test.getTestCaseInput().getBytes()));

        Judge0TokenResponse submissionCreationResponse = createJudge0Submission(request);
        JudgeSubmitResponse submissionResponse;

        do {
            try {
                submissionResponse = getJudge0SubmissionResponse(submissionCreationResponse);
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new InternalServerException("Error while executing the program", e);
            }
        } while (submissionResponse.getStatus().getId() < 3);
        return submissionResponse;
    }

    private JudgeSubmitResponse getJudge0SubmissionResponse(Judge0TokenResponse submissionCreationResponse) {
        try {
            return judgeWebClient.get()
                    .uri("/submissions/" + submissionCreationResponse.getToken())
                    .retrieve()
                    .bodyToMono(JudgeSubmitResponse.class)
                    .block();
        } catch (HttpClientErrorException e) {
            throw new SandboxCodeExecutionError(e, e.getMessage());
        }
    }

    private void handleSubmissionResponse(JudgeSubmitResponse judgeSubmitResponse,
                                          TestCase test,
                                          Queue<AcceptCase> acceptedCases,
                                          Queue<RejectCase> rejectedCases,
                                          Queue<Double> timeTaken,
                                          Queue<Double> memoryTaken) {
        if (judgeSubmitResponse.getStatus().getId() ==13){
          throw new SandboxError(judgeSubmitResponse.getStatus().getDescription() +":"+judgeSubmitResponse.getMessage());
        }
       else if (judgeSubmitResponse.getCompile_output() != null) {
            logErrorAndThrow(new SandboxCompileError(judgeSubmitResponse.getCompile_output()));
        } else if (judgeSubmitResponse.getStderr() != null) {
            logErrorAndThrow(new SandboxStandardError(judgeSubmitResponse.getStderr()));
        } else if (judgeSubmitResponse.getStdout() != null) {
            handleOutput(judgeSubmitResponse, test, acceptedCases, rejectedCases, timeTaken, memoryTaken);
        }
    }

    private void handleOutput(JudgeSubmitResponse judgeSubmitResponse,
                              TestCase test,
                              Queue<AcceptCase> acceptedCases,
                              Queue<RejectCase> rejectedCases,
                              Queue<Double> timeTaken, Queue<Double> memoryTaken) {
        String output = judgeSubmitResponse.getStdout().replace("\n", "");
        if (test.getExpectedOutput().equals(output)) {
            log.info("Test case Accepted.");
            acceptedCases.add(new AcceptCase(test.getTestCaseInput(), test.getExpectedOutput()));
        } else {
            log.info("Test case Rejected.");
            rejectedCases.add(new RejectCase(test.getTestCaseInput(), output, test.getExpectedOutput()));
        }
        timeTaken.add(Double.parseDouble(judgeSubmitResponse.getTime()));
        memoryTaken.add(Double.parseDouble(judgeSubmitResponse.getMemory()));
    }

    private void logErrorAndThrow(RuntimeException e) {
        log.error("Error occurred during code submission: {}", e.getMessage());
        throw e;
    }


    private Judge0TokenResponse createJudge0Submission(Judge0Request judge0Request) {
        try {
            return judgeWebClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/submissions")
                            .queryParam("base64_encoded", "true")
                            .build())
                    .bodyValue(judge0Request)
                    .retrieve()
                    .bodyToMono(Judge0TokenResponse.class)
                    .block();
        } catch (HttpClientErrorException e) {
            System.out.println("HTTP CLIENT ERROR : {}"+ e.getMessage());
            throw new SandboxCodeExecutionError(e, e.getMessage());
        }
    }




}
