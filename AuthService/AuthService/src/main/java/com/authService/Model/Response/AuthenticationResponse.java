package com.authService.Model.Response;

import com.authService.Model.Entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {

    private User user;
    private String token;
}
