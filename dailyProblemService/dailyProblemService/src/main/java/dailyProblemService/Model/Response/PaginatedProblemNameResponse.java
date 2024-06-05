package dailyProblemService.Model.Response;

import dailyProblemService.Model.Dto.DailyProblemListResponse;
import dailyProblemService.Model.Dto.ProblemNameDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedProblemNameResponse {

    private List<DailyProblemListResponse> problemNameDto;
    private int totalPages;
    private long totalElements;
}
