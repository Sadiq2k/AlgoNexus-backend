package com.TestCaseService.Model.TestCases;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestCase {

    private String testCaseInput;
    private String expectedOutput;
    public void setTestCaseInput(String testCaseInput) {
        this.testCaseInput = testCaseInput != null ? testCaseInput.trim() : null;
    }
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput != null ? expectedOutput.trim() : null;
    }
}
