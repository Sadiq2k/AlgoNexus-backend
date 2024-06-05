package dailyProblemService.Config;

import dailyProblemService.Service.DailyProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyProblemScheduler {

    @Autowired
    private DailyProblemService dailyProblemService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleDailyProblemAssignment() {
        dailyProblemService.assignDailyProblem();
    }
}
