package dailyProblemService.Service;

import dailyProblemService.Model.Entities.DailyProblem;
import dailyProblemService.Model.Response.DailyProblemResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface DailyProblemService {
    DailyProblemResponse getProblemForDate(LocalDate date);

    void assignDailyProblem();

    boolean isTodayProblem(String problemId, LocalDate date);

    Page<DailyProblem> getAllDailyProblems(Integer page, Integer size);
}
