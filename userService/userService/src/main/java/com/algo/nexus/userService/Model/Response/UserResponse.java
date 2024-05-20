package com.algo.nexus.userService.Model.Response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {


    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private boolean block;
    private String role;

}
