package dailyProblemService.Model.Entities;

import dailyProblemService.Model.Dto.ProblemDto;
import lombok.*;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "dailyProblems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyProblem {

    @Id
    private String id;
    private LocalDate date;
    private ProblemDto problem;

    public DailyProblem(LocalDate date, ProblemDto problem) {
        this.date = date;
        this.problem = problem;
    }


}
