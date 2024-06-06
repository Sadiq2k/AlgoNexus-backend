package com.algo.nexus.userService.Model.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddEducationRequest {

    private UUID id;
    private String education;
}
