package problemsService.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import problemsService.Model.Enum.Submission;
import problemsService.Model.entities.Problem;
import problemsService.Model.request.AddProblemRequest;
import problemsService.Model.request.ProblemVerificationRequest;
import problemsService.Model.response.ProblemVerificationResponse;

import java.util.List;
import java.util.Optional;

public interface ProblemService {

    Page<Problem> getAllProblems(Integer page, Integer size);

    Optional<Problem> getProblemById(String problemId);

    void addProblem(AddProblemRequest addProblemRequest, HttpServletRequest servletRequest);

    ProblemVerificationResponse verifyProblem(ProblemVerificationRequest problemVerificationRequest);

    ResponseEntity<ProblemVerificationResponse> badRequestWithMessageAndStatus(String message, Submission submission);

    ResponseEntity<ProblemVerificationResponse> internalServerErrorWithMessage(String message);

    boolean invalidTestCases(ProblemVerificationRequest request);

    void checkDuplicateTitleExistsOrNot(String title);

}
