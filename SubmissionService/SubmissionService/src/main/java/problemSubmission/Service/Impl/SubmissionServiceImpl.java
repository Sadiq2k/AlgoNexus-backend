package problemSubmission.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problemSubmission.Model.Entities.Submissions;
import problemSubmission.Model.Request.SubmissionRequest;
import problemSubmission.Repository.SubmissionRepository;
import problemSubmission.Service.SubmissionService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;
    @Override
    public void saveSubmission(SubmissionRequest request) {
        Submissions submissions = Submissions.builder()
                .submission(request.getSubmission())
                .acceptedCases(request.getAcceptedCases())
                .averageMemory(request.getAverageMemory())
                .averageTime(request.getAverageTime())
                .submissionTime(request.getSubmissionTime())
                .isSolved(request.isSolved())
                .rejectedCases(request.getRejectedCases())
                .sourceCode(request.getSourceCode())
                .userId(request.getUserId())
                .problemId(request.getProblemId())
                .build();
        log.info("successfully added submission");
         submissionRepository.save(submissions);
    }

    @Override
    public List<Submissions> getSubmitSolution(String problemId) {
        return submissionRepository.findByProblemId(problemId);
    }
}
