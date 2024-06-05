package dailyProblemService.Controller;

import dailyProblemService.FeingClient.ProblemServiceFeignClient;
import dailyProblemService.Model.Dto.DailyProblemListResponse;
import dailyProblemService.Model.Dto.ProblemNameDto;
import dailyProblemService.Model.Entities.DailyProblem;
import dailyProblemService.Model.Response.DailyProblemResponse;
import dailyProblemService.Model.Response.PaginatedProblemNameResponse;
import dailyProblemService.Service.DailyProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/daily-problem")
public class DailyProblemController {

    @Autowired
    private DailyProblemService dailyProblemService;
    @Autowired
    private ProblemServiceFeignClient serviceFeignClient;

    @GetMapping("/get")
    public DailyProblemResponse getDailyProblem(@RequestParam("date") LocalDate date) {
        System.out.println("controller ===="+date);
        final DailyProblemResponse problemForDate = dailyProblemService.getProblemForDate(date);
        return problemForDate;
    }

    @GetMapping("/set")
    public void assingDailyProblem(){
        dailyProblemService.assignDailyProblem();
    }

    @GetMapping("/isTodayProblem")
    public boolean isTodayProblem(@RequestParam("problemId") String problemId , @RequestParam("date") LocalDate date){
      return dailyProblemService.isTodayProblem(problemId,date);
    }

    @GetMapping("/get/problem/list")
    public PaginatedProblemNameResponse getAllDailyProblem(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

        Page<DailyProblem> allDailyProblems = dailyProblemService.getAllDailyProblems(page, size);

        List<DailyProblemListResponse> problemNameDtos = allDailyProblems.stream()
                .map(problem ->{

                   ProblemNameDto problemName = serviceFeignClient.getProblemName(problem.getProblem().getProblemId());
                    String title = problemName.getTitle();
                    String difficulty = problemName.getDifficulty();
                    return new DailyProblemListResponse(
                        problem.getId(),
                        problem.getDate() ,
                        title,
                        difficulty

                );
                }).collect(Collectors.toList());

        PaginatedProblemNameResponse response = new PaginatedProblemNameResponse(
                problemNameDtos,
                allDailyProblems.getTotalPages(),
                allDailyProblems.getNumberOfElements()
        );
        return response;
    }

}
