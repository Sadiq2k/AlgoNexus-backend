package problemsService.Model.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class Example {

    private String testCaseInput;
    private String expectedOutput;

    public Example(String testCaseInput, String expectedOutput) {
        this.testCaseInput = testCaseInput;
        this.expectedOutput = expectedOutput;
    }
}