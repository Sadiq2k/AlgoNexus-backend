package com.algo.nexus.userService.Model.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NewUserRequest {

    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
}
