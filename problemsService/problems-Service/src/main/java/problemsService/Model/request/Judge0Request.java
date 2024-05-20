package problemsService.Model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Judge0Request {

    @JsonProperty
    private int language_id;
    @JsonProperty
    private String source_code;
    @JsonProperty
    private String stdin;
}
