package problemsService.Model.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import problemsService.Model.Dto.TestCase;

import java.util.List;


@Getter
@Setter
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class ProblemVerificationRequest {

    @NotNull
    private List<TestCase> testCases;
    @NotNull
    private String sourceCode;
    @NotNull
    private Integer languageId;
}
