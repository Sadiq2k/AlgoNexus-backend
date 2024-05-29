package problemSubmission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import problemSubmission.Model.Entities.Submissions;
import problemSubmission.Model.Request.SubmissionRequest;
import problemSubmission.Model.Response.RecentSubmissionResponse;
import problemSubmission.Service.SubmissionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/submit-solution")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;


    @PostMapping("/add")
    public ResponseEntity<String> saveSubmission(@RequestBody SubmissionRequest submissionRequest){
        submissionService.saveSubmission(submissionRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("successfully added submission");
    }


    @GetMapping("/get-problem-submission/{problemId}")
    public List<Submissions> getSubmitSolution(@PathVariable("problemId") String problemId ){
        final List<Submissions> submitSolution = submissionService.getSubmitSolution(problemId);
        return submitSolution;
    }

//    @GetMapping("/solved")
//    public long countSolvedProblems(@RequestParam String userId){
//       return submissionService.countUniqueSolvedProblems(userId);
//    }

    @GetMapping("/solved-problems-by-difficulty/{userId}")
    public ResponseEntity<Map<String, Long>> getSolvedProblemsByDifficulty(@PathVariable("userId") String userId) {
        Map<String, Long> solvedProblemsByDifficulty = submissionService.countUniqueAcceptedProblemsByDifficulty(userId);
        return ResponseEntity.ok(solvedProblemsByDifficulty);
    }

    @GetMapping("/recent-sub/{userId}")
    public RecentSubmissionResponse getRecentSubmission(@PathVariable("userId") String userId){
           return submissionService.getRecentSubmission(userId);
    }

}
