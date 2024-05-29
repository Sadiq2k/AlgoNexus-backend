package com.algo.nexus.userService.Model.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private boolean block;
    private String role;

}
