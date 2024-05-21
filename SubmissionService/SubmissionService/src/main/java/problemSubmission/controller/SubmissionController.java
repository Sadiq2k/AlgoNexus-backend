package problemSubmission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import problemSubmission.Model.Entities.Submissions;
import problemSubmission.Model.Request.SubmissionRequest;
import problemSubmission.Service.SubmissionService;

import java.util.List;
import java.util.Optional;

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
}
