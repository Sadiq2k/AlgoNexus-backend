package dailyProblemService.Service;

import dailyProblemService.Model.Response.DailyProblemResponse;

import java.time.LocalDate;

public interface DailyProblemService {
    DailyProblemResponse getProblemForDate(LocalDate date);

    void assignDailyProblem();

    boolean isTodayProblem(String problemId, LocalDate date);
}
