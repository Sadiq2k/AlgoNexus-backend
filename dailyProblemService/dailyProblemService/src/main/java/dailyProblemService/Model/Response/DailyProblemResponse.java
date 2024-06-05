package dailyProblemService.Model.Response;

import dailyProblemService.Model.Dto.ProblemDto;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyProblemResponse {

    private LocalDate date;
    private String problemId;

}
