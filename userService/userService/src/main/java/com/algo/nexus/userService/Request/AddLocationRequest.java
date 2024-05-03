package com.algo.nexus.userService.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter

public class AddLocationRequest {

    private UUID id;
    private String location;
}
