package problemSubmission.Model.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "DAILY-PROBLEM-SERVICE")
public interface DailyProblemServiceFeignClient {

    @GetMapping("/daily-problem/isTodayProblem")
    boolean isTodayProblem(@RequestParam("problemId") String problemId, @RequestParam("date") LocalDate date);

    @PostMapping("/streak/add")
    void addStreak(@RequestParam("userId") String userId);
}
