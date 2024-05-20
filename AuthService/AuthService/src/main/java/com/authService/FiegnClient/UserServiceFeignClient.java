package com.authService.FiegnClient;

import com.authService.Model.Dto.UserDto;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceFeignClient {

    @PostMapping("/users/register")
    @LoadBalanced
    void sendUser(@RequestBody UserDto userDto);

}
