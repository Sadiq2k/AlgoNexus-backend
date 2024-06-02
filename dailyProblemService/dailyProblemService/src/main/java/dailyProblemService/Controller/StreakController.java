package dailyProblemService.Controller;

import dailyProblemService.Service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/streak")
public class StreakController {

    @Autowired
    private StreakService streakService;

    @PostMapping("/add")
    public void addStreak(@RequestParam("userId") String userId){
            streakService.addStreak(userId);
    }

    @GetMapping("/get-streak")
    public Long getStreak(@RequestParam("userId") String userId){
       return streakService.getStreak(userId);
    }
}
