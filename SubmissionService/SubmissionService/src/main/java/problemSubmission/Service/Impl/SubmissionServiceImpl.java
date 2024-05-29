package problemSubmission.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import problemSubmission.Model.DTO.RecentSubmissionDto;
import problemSubmission.Model.Entities.Submissions;
import problemSubmission.Model.Request.SubmissionRequest;
import problemSubmission.Model.Response.RecentSubmissionResponse;
import problemSubmission.Repository.SubmissionRepository;
import problemSubmission.Service.SubmissionService;

import java.util.*;
import java.util.stream.Collectors;

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
                .difficulty(request.getDifficulty())
                .title(request.getTitle())
                .problemNo(request.getProblemNo())
                .build();
        log.info("successfully added submission");
         submissionRepository.save(submissions);
    }

    @Override
    public Map<String, Long> countUniqueAcceptedProblemsByDifficulty(String userId) {
        List<Submissions> submissions = submissionRepository.findSolvedSubmissionsByUserId(userId);

        Map<String, Set<String>> problemsByDifficulty = new HashMap<>();
        problemsByDifficulty.put("easy", new HashSet<>());
        problemsByDifficulty.put("medium", new HashSet<>());
        problemsByDifficulty.put("hard", new HashSet<>());

        for (Submissions submission : submissions) {
            if (submission.isSolved()) {  // Ensure only solved submissions are considered
                String difficulty = submission.getDifficulty();
                if (difficulty != null) {
                    difficulty = difficulty.toLowerCase();
                    if (problemsByDifficulty.containsKey(difficulty)) {
                        problemsByDifficulty.get(difficulty).add(submission.getProblemId());
                    }
                }
            }
        }

        Map<String, Long> result = new HashMap<>();
        result.put("Easy", (long) problemsByDifficulty.get("easy").size());
        result.put("Medium", (long) problemsByDifficulty.get("medium").size());
        result.put("Hard", (long) problemsByDifficulty.get("hard").size());

        return result;
    }

    @Override
    public RecentSubmissionResponse getRecentSubmission(String userId) {

        Pageable pageable = PageRequest.of(0, 15, Sort.by(Sort.Direction.DESC, "submissionTime"));
        List<Submissions> recentSubmissions = submissionRepository.findRecentSubmissionsByUserId(userId, pageable);

        List<RecentSubmissionDto> submissionDTOs = recentSubmissions.stream()
                .map(submission -> new RecentSubmissionDto(
                        submission.getProblemNo(),
                        submission.getTitle(),
                        submission.getSubmissionTime()
                ))
                .collect(Collectors.toList());
        return new RecentSubmissionResponse(submissionDTOs);
    }

    @Override
    public List<Submissions> getSubmitSolution(String problemId) {
        return submissionRepository.findByProblemId(problemId);
    }
}
