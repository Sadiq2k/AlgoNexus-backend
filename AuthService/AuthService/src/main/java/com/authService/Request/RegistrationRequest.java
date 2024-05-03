package com.authService.Request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Firstname is mandatory")
    @NotBlank
    private String firstname;

    @NotEmpty(message = "Lastname is mandatory")
    @NotBlank
    private String lastname;

    @NotEmpty(message = "Email is mandatory")
    @NotBlank
    @Email(message = "Email is not formatted")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @NotEmpty
    @NotBlank
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d?)[A-Za-z\\d@$!%*?&]{7,}$", message = "Password must contain at least one uppercase letter and one digit")
    private String password;


}
