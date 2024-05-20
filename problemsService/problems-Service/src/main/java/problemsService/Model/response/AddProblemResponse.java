package problemsService.Model.response;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProblemResponse {
    private String message;
    private int status;
}
