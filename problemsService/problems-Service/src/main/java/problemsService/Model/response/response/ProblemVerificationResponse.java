package problemsService.Model.response.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemVerificationResponse {

    @JsonProperty
    private String message;
    @JsonProperty
    private String status;
//    @JsonProperty
//    private List<AcceptCase> acceptedCases;
//
//    @JsonProperty
//    private List<RejectCase> rejectedCases;
}
