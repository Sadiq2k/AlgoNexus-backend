package problemSubmission.Model.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceFeignClient {

    @GetMapping("/users/getAllUsersCount")
    Long getTotalUsers();

}
