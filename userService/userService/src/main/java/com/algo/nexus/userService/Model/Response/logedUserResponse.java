package com.algo.nexus.userService.Model.Response;

import com.algo.nexus.userService.Model.Entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class logedUserResponse {
    private User user;
}
