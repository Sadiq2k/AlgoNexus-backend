package com.TestCaseService.Controller;

import com.TestCaseService.Model.Request.SolutionSubmitRequest;
import com.TestCaseService.Model.Response.TestCaseRunResponse;
import com.TestCaseService.Model.DTO.TestCaseDTO;
import com.TestCaseService.Model.Request.AddTestCaseRequest;
import com.TestCaseService.Service.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test-case")
@Slf4j
@EnableFeignClients
public class TestCaseController {

    @Autowired
    private final TestCaseService testCaseService;

    @Autowired
    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }
    @PostMapping("/add-testcase")
    public ResponseEntity<String> addProblemTestCases(@RequestBody AddTestCaseRequest addTestCaseRequest){
        testCaseService.addTestCase(addTestCaseRequest);
        return ResponseEntity.ok("successfully added testcase");
    }

    @GetMapping("/get-problem-testcases/{problemId}")
    public ResponseEntity<List<TestCaseDTO>> getProblemTestCases(@PathVariable String problemId){
        return ResponseEntity.ok(testCaseService.getProblemTestCases(problemId));
    }

    @PostMapping("/run-and-test")
    public ResponseEntity<TestCaseRunResponse> runAndTestSolution(@RequestBody SolutionSubmitRequest solutionSubmitRequest){
        return ResponseEntity.ok(testCaseService.runAndTest(solutionSubmitRequest));
    }

}
