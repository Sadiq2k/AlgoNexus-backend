package dailyProblemService.Model;

import lombok.Getter;
import lombok.Setter;

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