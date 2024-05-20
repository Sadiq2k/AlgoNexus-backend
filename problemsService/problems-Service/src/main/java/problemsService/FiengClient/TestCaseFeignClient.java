package problemsService.FiengClient;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import problemsService.Model.request.AddTestCaseRequest;

@FeignClient(name = "TESTCASE-SERVICE")
public interface TestCaseFeignClient {

    @PostMapping({"/test-case/add-testcase"})
    @LoadBalanced
    ResponseEntity<String> addTestCases(@RequestBody AddTestCaseRequest addTestCaseRequest, @RequestHeader("Authorization") String authorizationHeader);

}
