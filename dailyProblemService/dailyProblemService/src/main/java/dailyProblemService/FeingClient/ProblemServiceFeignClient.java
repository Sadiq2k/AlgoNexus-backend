package dailyProblemService.FeingClient;

import dailyProblemService.Model.Dto.ProblemDto;
import dailyProblemService.Model.Dto.ProblemNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PROBLEM-SERVICE")
public interface ProblemServiceFeignClient {

    @GetMapping("/problem/random")
    public ProblemDto getRandomProblem();

    @GetMapping("/problem/getProblem/name")
    ProblemNameDto getProblemName(@RequestParam("problemId") String problem);

}
