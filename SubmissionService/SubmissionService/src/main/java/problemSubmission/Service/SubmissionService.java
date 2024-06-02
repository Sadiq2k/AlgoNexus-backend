package problemSubmission.Service;

import org.springframework.data.domain.Page;
import problemSubmission.Model.DTO.AllSubmissionDto;
import problemSubmission.Model.Entities.Submissions;
import problemSubmission.Model.Request.SubmissionRequest;
import problemSubmission.Model.Response.AllSubmissionResponse;
import problemSubmission.Model.Response.RecentSubmissionResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SubmissionService {
    void saveSubmission(SubmissionRequest submissionRequest);

    List<Submissions> getSubmitSolution(String problemId);

//    long countUniqueSolvedProblems(String userId);

    Map<String, Long> countUniqueAcceptedProblemsByDifficulty(String userId);

    RecentSubmissionResponse getRecentSubmission(String userId);

    Page<AllSubmissionDto> getAllSubmissions(String userId, int page, int size);

    Long getTotalSubmission();

    Long getTotalAcceptence();
}
