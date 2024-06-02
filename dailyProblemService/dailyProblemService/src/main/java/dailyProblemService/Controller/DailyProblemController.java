package dailyProblemService.Controller;

import dailyProblemService.Model.Entities.DailyProblem;
import dailyProblemService.Model.Response.DailyProblemResponse;
import dailyProblemService.Service.DailyProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/daily-problem")
public class DailyProblemController {

    @Autowired
    private DailyProblemService dailyProblemService;

    @GetMapping("/get")
    public DailyProblemResponse getDailyProblem(@RequestParam("date") LocalDate date) {
        System.out.println("controller ===="+date);
        final DailyProblemResponse problemForDate = dailyProblemService.getProblemForDate(date);
        return problemForDate;
    }

//    @GetMapping("/set")
//    public void assingDailyProblem(){
//        dailyProblemService.assignDailyProblem();
//    }

    @GetMapping("/isTodayProblem")
    public boolean isTodayProblem(@RequestParam("problemId") String problemId , @RequestParam("date") LocalDate date){
      return dailyProblemService.isTodayProblem(problemId,date);
    }

}
