package com.algo.nexus.userService.FiegnClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthenticationFeignClient {

    @GetMapping("/auth/isBlocked/{userId}")
    boolean isUserBlocked(@PathVariable("userId") UUID userId);
    @GetMapping("/auth/user-role/{userId}")
    String getRoles(@PathVariable("userId") UUID userId);
}
