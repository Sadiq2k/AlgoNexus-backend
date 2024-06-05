package dailyProblemService.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyProblemListResponse {

    private String id;
    private LocalDate date;
    private String title;
    private String difficulty;
}
