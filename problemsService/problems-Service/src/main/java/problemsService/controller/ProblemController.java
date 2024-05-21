package problemsService.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import problemsService.Model.Enum.Submission;
import problemsService.Model.Judge0.TestCaseTimeOutException;
import problemsService.Model.Judge0.error.SandboxCodeExecutionError;
import problemsService.Model.Judge0.error.SandboxCompileError;
import problemsService.Model.Judge0.error.SandboxError;
import problemsService.Model.Judge0.error.SandboxStandardError;
import problemsService.Model.entities.Problem;
import problemsService.Model.request.AddProblemRequest;
import problemsService.Model.request.ProblemVerificationRequest;
import problemsService.Model.response.AddProblemResponse;
import problemsService.Model.response.ProblemVerificationResponse;
import problemsService.service.ExampleService;
import problemsService.service.ProblemService;
import problemsService.service.TestCaseService;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private ExampleService exampleService;
    @Autowired
    private TestCaseService testCaseService;



    @PostMapping
    public ResponseEntity<AddProblemResponse> addProblem(@RequestBody AddProblemRequest addProblemRequest, HttpServletRequest servletRequest) {
        problemService.addProblem(addProblemRequest,servletRequest);
        return ResponseEntity.ok(new AddProblemResponse("Problem added successfully", HttpStatus.OK.value()));

    }


    @GetMapping
    public ResponseEntity<List<Problem>> getAllProblems() {
        try {
            List<Problem> problems = problemService.getAllProblems();
            if (!problems.isEmpty()) {
                for (Problem problem : problems) {
                    String solutionTemplateBase64 = problem.getSolutionTemplate();
                    String solutionTemplate = new String(Base64.getDecoder().decode(solutionTemplateBase64));
                    problem.setSolutionTemplate(solutionTemplate);

                    String driverCodeBase64 = problem.getDriverCode();
                    String driverCode = new String(Base64.getDecoder().decode(driverCodeBase64));
                    problem.setDriverCode(driverCode);
                }
                return ResponseEntity.ok(problems);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }


    @GetMapping("/{problemId}")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallBack" )
    public ResponseEntity<Problem> getProblem(@PathVariable("problemId") String problemId){
        Optional<Problem> optionalProblem = problemService.getProblemById(problemId);
        return optionalProblem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Problem> ratingHotelFallBack(String problemId, Exception ex) {
        log.info("Fallback is executed because service is down", ex);
        Problem problem = new Problem();
        // Populate the problem object with default or cached data, if available
        problem.setProblemId(problemId);
        problem.setTitle("Default Title");
        problem.setDescription("Default Description");

        return new ResponseEntity<>(problem, HttpStatus.OK);
    }

    @PostMapping("/verify-problem")
    public ResponseEntity<ProblemVerificationResponse> verifyProblem(@Valid @RequestBody ProblemVerificationRequest request) {
        if (problemService.invalidTestCases(request)) {
            return ResponseEntity.badRequest().body(ProblemVerificationResponse.builder()
                    .message("Invalid test cases")
                    .build());
        }
        ProblemVerificationResponse response;
        try {
             response = problemService.verifyProblem(request);

        } catch (TestCaseTimeOutException e) {
            log.warn("Timed out!...");
            return ResponseEntity.badRequest().build();
        } catch (SandboxCompileError e) {
            log.error("Compile error {}", e.getMessage());
            return problemService.badRequestWithMessageAndStatus(e.getMessage(), Submission.COMPILE_ERR);
        } catch (SandboxStandardError e) {
            log.warn("Standard error {}", e.getMessage());
            return problemService.badRequestWithMessageAndStatus(e.getMessage(), Submission.STD_ERR);
        } catch (SandboxCodeExecutionError e) {
            log.error("Client error : {}", e.getMessage());
            return ResponseEntity.badRequest().body(ProblemVerificationResponse.builder()
                    .message("Client error " + e.getMessage())
                    .build());
        } catch (SandboxError e) {
            return problemService.internalServerErrorWithMessage(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
            return ResponseEntity.ok(response);
    }



}
