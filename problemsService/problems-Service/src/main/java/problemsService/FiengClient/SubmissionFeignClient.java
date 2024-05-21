package problemsService.FiengClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import problemsService.Model.Dto.SubmissionDTO;

import java.util.List;

@FeignClient(name = "PROBLEM-SUBMISSION-SERVICE")
public interface SubmissionFeignClient {

    @GetMapping("/submit-solution/get-problem-submission/{problemId}")
    public List<SubmissionDTO> getSubmissionSolution(@PathVariable("problemId") String problemId);
}
