package com.algo.nexus.userService.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateUserResponse {

    private String firstname;
    private String lastname;
    private String email;
    private UUID id;
}
