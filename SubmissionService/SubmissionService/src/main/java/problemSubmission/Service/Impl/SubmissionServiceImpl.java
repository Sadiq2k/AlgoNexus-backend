package problemSubmission.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import problemSubmission.Model.DTO.AllSubmissionDto;
import problemSubmission.Model.DTO.RecentSubmissionDto;
import problemSubmission.Model.Entities.Submissions;
import problemSubmission.Model.Request.SubmissionRequest;
import problemSubmission.Model.Response.RecentSubmissionResponse;
import problemSubmission.Model.feignClient.DailyProblemServiceFeignClient;
import problemSubmission.Model.feignClient.UserServiceFeignClient;
import problemSubmission.Repository.SubmissionRepository;
import problemSubmission.Service.SubmissionService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserServiceFeignClient userServiceFeignClient;
    @Autowired
    private DailyProblemServiceFeignClient dailyProblemServiceFeignClient;


    @Override
    public void saveSubmission(SubmissionRequest request) {
//        if (request.getSubmission().equals("ACCEPTED")){
//
//        }
        boolean isTodayProblem = isTodayProblem(request.getProblemId(), LocalDate.now());

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
                .isTodayProblem(isTodayProblem)
                .build();
        log.info("successfully added submission");
        submissionRepository.save(submissions);

        if (isTodayProblem){
            dailyProblemServiceFeignClient.addStreak(request.getUserId());
        }


    }
    //        if (request.isSolved()) {
//            Optional<Submissions> existingSubmission = submissionRepository.findByUserIdAndProblemIdAndIsSolved(
//                    request.getUserId(), request.getProblemId(), true);
//
//            System.out.println(existingSubmission);
//            if (!existingSubmission.isPresent()) {
//
//                final Long totalUsers = userServiceFeignClient.getTotalUsers();
//                System.out.println(totalUsers);
//            }
//        }

    private boolean isTodayProblem(String problemId, LocalDate date) {
        return dailyProblemServiceFeignClient.isTodayProblem(problemId, date);
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
    public Page<AllSubmissionDto> getAllSubmissions(String userId, int page, int size) {

        Page<Submissions> submissions = submissionRepository.findSubmissionsByUserId(userId, PageRequest.of(page, size));
        List<AllSubmissionDto> allSubmissionDtos =submissions.stream()
                .map(sub -> new AllSubmissionDto(
                        sub.getSubmissionTime(),
                        sub.getTitle(),
                        sub.isSolved(),
                        sub.getAverageTime()
                )).collect(Collectors.toList());
        return new PageImpl<>(allSubmissionDtos, PageRequest.of(page, size), submissions.getTotalElements());
    }

    @Override
    public Long getTotalSubmission() {
       return submissionRepository.count();
    }

    @Override
    public Long getTotalAcceptence() {
      return submissionRepository.countUniqueAcceptedProblems();
    }

    @Override
    public List<Submissions> getSubmitSolution(String problemId) {
        return submissionRepository.findByProblemId(problemId);
    }
}
