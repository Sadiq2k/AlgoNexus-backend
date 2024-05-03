package com.authService.Request;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
public class ForgotPasswordRequest {

    @JsonProperty("email")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank
    @Email(message = "Email is not formatted")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    // Default constructor
    public ForgotPasswordRequest() {
    }

    // Constructor annotated with @JsonCreator
    @JsonCreator
    public ForgotPasswordRequest(@JsonProperty("email") String email) {
        this.email = email;
    }
}
