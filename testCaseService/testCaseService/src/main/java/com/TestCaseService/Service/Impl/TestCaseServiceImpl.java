package com.TestCaseService.Service.Impl;

import com.TestCaseService.Exceptions.InternalServerException;
import com.TestCaseService.Judge0.error.ClientSandboxCodeExecutionError;
import com.TestCaseService.Judge0.error.SandboxCompileError;
import com.TestCaseService.Judge0.error.SandboxError;
import com.TestCaseService.Judge0.error.SandboxStandardError;
import com.TestCaseService.Model.Entities.TestCaseEntity;
import com.TestCaseService.Model.Enum.Submission;
import com.TestCaseService.Model.Request.Judge0Request;
import com.TestCaseService.Model.Request.SolutionSubmitRequest;
import com.TestCaseService.Model.Response.JudgeSubmitResponse;
import com.TestCaseService.Model.Response.JudgeTokenResponse;
import com.TestCaseService.Model.Response.TestCaseRunResponse;
import com.TestCaseService.Model.DTO.TestCaseDTO;
import com.TestCaseService.Model.TestCases.AcceptCase;
import com.TestCaseService.Model.TestCases.RejectCase;
import com.TestCaseService.Model.TestCases.TestCase;
import com.TestCaseService.Repository.TestCaseRepository;
import com.TestCaseService.Model.Request.AddTestCaseRequest;
import com.TestCaseService.Service.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class TestCaseServiceImpl implements TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private  WebClient judgeWebClient;


    @Override
    public void addTestCase(AddTestCaseRequest addTestCaseRequest) {
        TestCaseEntity test = TestCaseEntity.builder()
                .problemId(addTestCaseRequest.getProblemId())
                .testCases(addTestCaseRequest.getTestCases())
                .build();
        testCaseRepository.save(test);
    }

    @Override
    public List<TestCaseDTO> getProblemTestCases(String problemId) {
        List<TestCase> testcases = testCaseRepository.findByProblemId(problemId).get(0).getTestCases();

        List<TestCaseDTO> testCaseList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(0);
        testcases.forEach(test -> {
            index.getAndIncrement();
            testCaseList.add(new TestCaseDTO(test,index.get()));
        });
        return testCaseList;
    }

    @Override
    public TestCaseRunResponse runAndTest(SolutionSubmitRequest solutionSubmitRequest) {
        Queue<AcceptCase> acceptedCases = new ConcurrentLinkedQueue<>();
        Queue<RejectCase> rejectedCases = new ConcurrentLinkedQueue<>();
        Queue<RuntimeException> exceptions = new ConcurrentLinkedQueue<>();
        Queue<Double> timeTaken = new ConcurrentLinkedQueue<>();
        Queue<Double> memoryTaken = new ConcurrentLinkedQueue<>();

        List<TestCaseEntity> allTestCases = testCaseRepository.findByProblemId(solutionSubmitRequest.getProblemId());

        Executor executor = Executors.newFixedThreadPool(10);

        List<CompletableFuture<Void>> futures = allTestCases.get(0).getTestCases()
                .stream()
                .map(testCase -> CompletableFuture.runAsync(() -> {
                    try {
                        JudgeSubmitResponse submissionResponse = executeAndGetResponse(solutionSubmitRequest, testCase);
                        handleSubmissionResponse(submissionResponse, testCase, acceptedCases, rejectedCases, timeTaken, memoryTaken);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        exceptions.add(e);
                    }
                }, executor))
                .toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        try {
            allOf.get(2, TimeUnit.MINUTES);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SandboxError(e, e.getMessage());
        }

        if (!exceptions.isEmpty()) {
            throw exceptions.peek();
        }

        return buildTestCaseRunResponse(solutionSubmitRequest, allTestCases.get(0).getTestCases().size(), acceptedCases, rejectedCases, timeTaken, memoryTaken);
    }

    private TestCaseRunResponse buildTestCaseRunResponse(SolutionSubmitRequest solutionSubmitRequest,
                                                         int totalTestCases, Queue<AcceptCase> acceptedCases, Queue<RejectCase> rejectedCases, Queue<Double> timeTaken, Queue<Double> memoryTaken) {
        TestCaseRunResponse response = new TestCaseRunResponse();

        double avgMemory = calculateAverage(memoryTaken);
        double avgTime = calculateAverage(timeTaken);

        response.setAverageMemory(convertToMB(avgMemory));
        response.setAverageTime(convertToSeconds(avgTime));
        response.setSubmission(rejectedCases.isEmpty() ? Submission.ACCEPTED : Submission.REJECTED);
        response.setRejectedCases(new ArrayList<>(rejectedCases));
        response.setAcceptedCases(new ArrayList<>(acceptedCases));
        response.setTotalTestCases(totalTestCases);
        response.setSourceCode(solutionSubmitRequest.getSolutionCode());
        response.setSubmissionTime(LocalDateTime.now());
        return response;
    }

    private double calculateAverage(Queue<Double> values) {
        return values.stream().reduce(0D, Double::sum) / values.size();
    }

    private double convertToMB(double valueInKB) {
        return Math.round(valueInKB / 1024.0 * 100.0) / 100.0;
    }

    private double convertToSeconds(double valueInMS) {
        return Math.round(valueInMS * 1000.0 * 100.0) / 100.0;
    }



    private JudgeSubmitResponse executeAndGetResponse(SolutionSubmitRequest solutionSubmitRequest, TestCase test) {
        Judge0Request request = new Judge0Request();
        String srcCode = solutionSubmitRequest.getSolutionCode() + "\n" + solutionSubmitRequest.getDriverCode() ;
        request.setSource_code(srcCode);
        request.setLanguage_id(solutionSubmitRequest.getLanguageId());
        request.setStdin(Base64.getEncoder().encodeToString(test.getTestCaseInput().getBytes()));

        JudgeTokenResponse submissionCreationResponse = createJudge0Submission(request);
        JudgeSubmitResponse submissionResponse;

        do {
            try {
                submissionResponse = judgeWebClient.get()
                        .uri("/submissions/" + submissionCreationResponse.getToken())
                        .retrieve()
                        .bodyToMono(JudgeSubmitResponse.class)
                        .block();
            } catch (HttpClientErrorException e) {
                System.out.println("CLIENT ERROR : {}"+ e.getMessage());
                throw new ClientSandboxCodeExecutionError(e, e.getMessage());
            } catch (Exception e) {
                System.out.println("ERROR : {}"+ e.getMessage());
                throw new SandboxError(e, e.getMessage());
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted while sleeping: {}"+ e.getMessage());
                throw new InternalServerException("Something went wrong while executing the program", e);
            }
        } while (Objects.requireNonNull(submissionResponse).getStatus().getId() < 3);
        return submissionResponse;
    }

    private void handleSubmissionResponse(JudgeSubmitResponse judgeSubmitResponse,
                                          TestCase test,
                                          Queue<AcceptCase> acceptedCases,
                                          Queue<RejectCase> rejectedCases,
                                          Queue<Double> timeTaken, Queue<Double> memoryTaken) {
        if (judgeSubmitResponse.getCompile_output() != null) {
            System.out.println("submit the code error occur compile output: {}"+ judgeSubmitResponse.getCompile_output());
            throw new SandboxCompileError(judgeSubmitResponse.getCompile_output());

        } else if (judgeSubmitResponse.getStderr() != null) {
            System.out.println("submit the code error occur: {}"+ judgeSubmitResponse.getStderr());
            throw new SandboxStandardError(judgeSubmitResponse.getStderr());

        } else if (judgeSubmitResponse.getStdout() != null) {
            String output = judgeSubmitResponse.getStdout().replace("\n", "");
            if (test.getExpectedOutput().equals(output)) {
                System.out.println("Test case Accepted.");
                acceptedCases.add(new AcceptCase(test.getTestCaseInput(), test.getExpectedOutput()));

            } else {
                System.out.println("Test case Rejected.");
                rejectedCases.add(new RejectCase(test.getTestCaseInput(), output, test.getExpectedOutput()));
            }
            timeTaken.add(Double.parseDouble(judgeSubmitResponse.getTime()));
            memoryTaken.add(Double.parseDouble(judgeSubmitResponse.getMemory()));
        }
    }

    private JudgeTokenResponse createJudge0Submission(Judge0Request judge0Request) {
        try {
            System.out.println("request object : {}" + judge0Request);
            return judgeWebClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/submissions")
                            .queryParam("base64_encoded", "true")
                            .build())
                    .bodyValue(judge0Request)
                    .retrieve()
                    .bodyToMono(JudgeTokenResponse.class)
                    .block();
        } catch (HttpClientErrorException e) {
            System.out.println("HTTP CLIENT ERROR : {}"+ e.getMessage());
            throw new ClientSandboxCodeExecutionError(e, e.getMessage());
        } catch (Exception e) {
            System.out.println("HTTP REQUEST EXCEPTION : {}"+ e.getMessage());
            throw new SandboxError(e, e.getMessage());
        }
    }

}
