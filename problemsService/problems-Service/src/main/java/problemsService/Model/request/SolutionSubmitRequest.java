package problemsService.Model.request;


import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class SolutionSubmitRequest {

    private String problemId;
    private Integer languageId;
    private String language;
    private String solutionCode;
    private String driverCode;
}
