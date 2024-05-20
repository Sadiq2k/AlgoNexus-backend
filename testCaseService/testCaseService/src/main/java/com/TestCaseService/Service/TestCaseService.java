package com.TestCaseService.Service;

import com.TestCaseService.Model.Request.SolutionSubmitRequest;
import com.TestCaseService.Model.Response.TestCaseRunResponse;
import com.TestCaseService.Model.DTO.TestCaseDTO;
import com.TestCaseService.Model.Request.AddTestCaseRequest;

import java.util.List;

public interface TestCaseService {

    void addTestCase(AddTestCaseRequest addTestCaseRequest);

    List<TestCaseDTO> getProblemTestCases(String problemId);

    TestCaseRunResponse runAndTest(SolutionSubmitRequest solutionSubmitRequest);

}
