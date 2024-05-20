package problemsService.Model.response;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import problemsService.Model.Judge0.JudgeSubmitStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonAutoDetect
public class JudgeSubmitResponse {

    @JsonProperty
    private String stdout;
    @JsonProperty
    private String stderr;
    @JsonProperty
    private String message;
    @JsonProperty
    private String time;
    @JsonProperty
    private String memory;
    @JsonProperty
    private String compile_output;
    @JsonProperty
    private JudgeSubmitStatus status;
}
