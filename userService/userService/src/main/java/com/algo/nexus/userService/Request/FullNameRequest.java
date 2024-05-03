package com.algo.nexus.userService.Request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class FullNameRequest {

    private String firstname;
    private String lastname;
    private UUID id;

}
