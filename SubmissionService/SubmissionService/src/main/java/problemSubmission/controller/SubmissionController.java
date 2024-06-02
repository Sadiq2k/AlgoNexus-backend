package problemSubmission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import problemSubmission.Model.DTO.AllSubmissionDto;
import problemSubmission.Model.Entities.Submissions;
import problemSubmission.Model.Request.SubmissionRequest;
import problemSubmission.Model.Response.AllSubmissionResponse;
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

    @GetMapping("/solved-problems-by-difficulty/{userId}")
    public ResponseEntity<Map<String, Long>> getSolvedProblemsByDifficulty(@PathVariable("userId") String userId) {
        Map<String, Long> solvedProblemsByDifficulty = submissionService.countUniqueAcceptedProblemsByDifficulty(userId);
        return ResponseEntity.ok(solvedProblemsByDifficulty);
    }

    @GetMapping("/recent-sub/{userId}")
    public RecentSubmissionResponse getRecentSubmission(@PathVariable("userId") String userId){
           return submissionService.getRecentSubmission(userId);
    }

    @GetMapping("/get/all/submissions")
    public Page<AllSubmissionDto> getAllSubmissions(@RequestParam("userId") String userId,
                                                    @RequestParam(value = "page",defaultValue = "0",required = false) int page,
                                                    @RequestParam(value = "size",defaultValue = "20" , required = false) int size) {
        return submissionService.getAllSubmissions(userId, page, size);
    }

    @GetMapping("/getTotalSubmission")
    public Long getTotalSubmission(){
        return submissionService.getTotalSubmission();
    }

    @GetMapping("/getTotalAcceptence")
    public Long getTotalAcceptence(){
       return submissionService.getTotalAcceptence();

    }
}
