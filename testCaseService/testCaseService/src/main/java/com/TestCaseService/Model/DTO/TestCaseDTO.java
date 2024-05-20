package com.TestCaseService.Model.DTO;

import com.TestCaseService.Model.TestCases.TestCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDTO {

    private String testCaseInput;
    private String expectedOutput;
    private int idx;

    public TestCaseDTO(TestCase test, int index) {
        this.testCaseInput = test.getTestCaseInput();
        this.expectedOutput = test.getExpectedOutput();
        this.idx = index;
    }
}
