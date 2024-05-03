package com.algo.nexus.userService.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddGenderRequest {

    private String gender;
    private UUID id;
}
