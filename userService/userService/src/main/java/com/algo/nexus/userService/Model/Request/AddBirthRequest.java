package com.algo.nexus.userService.Model.Request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AddBirthRequest {
    private UUID id;
    private String birthday;
}
