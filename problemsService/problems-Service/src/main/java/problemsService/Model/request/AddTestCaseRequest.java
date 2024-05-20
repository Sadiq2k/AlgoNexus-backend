package problemsService.Model.request;

import lombok.*;
import problemsService.Model.Dto.TestCase;

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
