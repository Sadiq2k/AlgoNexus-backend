package com.TestCaseService.Model.DTO;

import com.TestCaseService.Model.Enum.Submission;
import com.TestCaseService.Model.TestCases.AcceptCase;
import com.TestCaseService.Model.TestCases.RejectCase;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class SubmissionDTO {

    private String userId;
    private Long problemNo;
    private String title;
    private String problemId;
    private Submission submission;
    private Double averageTime;
    private Double averageMemory;
    private String difficulty;
    private List<RejectCase> rejectedCases;
    private List<AcceptCase> acceptedCases;
    private String sourceCode;
    private LocalDateTime submissionTime;
    private boolean isSolved;
}
