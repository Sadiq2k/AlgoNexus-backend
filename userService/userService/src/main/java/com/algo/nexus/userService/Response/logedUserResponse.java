package com.algo.nexus.userService.Response;

import com.algo.nexus.userService.Entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class logedUserResponse {
    private User user;
}
