package problemsService.Model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import problemsService.Model.Dto.AcceptCase;
import problemsService.Model.Dto.RejectCase;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemVerificationResponse {

    @JsonProperty
    private String message;
    @JsonProperty
    private String status;
    @JsonProperty
    private List<AcceptCase> acceptedCases;
    @JsonProperty
    private List<RejectCase> rejectedCases;
}
