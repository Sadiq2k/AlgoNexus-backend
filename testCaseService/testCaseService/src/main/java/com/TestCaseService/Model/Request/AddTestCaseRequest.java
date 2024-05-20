package com.TestCaseService.Model.Request;

import com.TestCaseService.Model.TestCases.TestCase;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddTestCaseRequest {

    private String problemId;
    private List<TestCase> testCases;
}
