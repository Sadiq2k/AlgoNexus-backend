package com.TestCaseService.Model.Response;

import com.TestCaseService.Model.Enum.Submission;
import com.TestCaseService.Model.TestCases.AcceptCase;
import com.TestCaseService.Model.TestCases.RejectCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseRunResponse {

    private Submission submission;
    private Double averageTime;
    private Double averageMemory;
    private List<RejectCase> rejectedCases;
    private List<AcceptCase> acceptedCases;
    private int totalTestCases;
    private String sourceCode;
    private LocalDateTime submissionTime;
}
