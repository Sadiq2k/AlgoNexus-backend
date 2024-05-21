package problemSubmission.Service;

import problemSubmission.Model.Entities.Submissions;
import problemSubmission.Model.Request.SubmissionRequest;

import java.util.List;
import java.util.Optional;

public interface SubmissionService {
    void saveSubmission(SubmissionRequest submissionRequest);

    List<Submissions> getSubmitSolution(String problemId);
}
