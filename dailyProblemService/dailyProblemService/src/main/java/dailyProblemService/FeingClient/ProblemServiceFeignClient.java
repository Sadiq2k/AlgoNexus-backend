package dailyProblemService.FeingClient;

import dailyProblemService.Model.Dto.ProblemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PROBLEM-SERVICE")
public interface ProblemServiceFeignClient {

    @GetMapping("/problem/random")
    public ProblemDto getRandomProblem();

}
