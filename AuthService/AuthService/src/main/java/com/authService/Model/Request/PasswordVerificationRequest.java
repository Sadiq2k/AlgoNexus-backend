package com.authService.Model.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PasswordVerificationRequest {

    private UUID id;

    @NotEmpty
    @NotBlank
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d?)[A-Za-z\\d@$!%*?&]{7,}$", message = "Password must contain at least one uppercase")
    private String currentPassword;
    @NotEmpty
    @NotBlank
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d?)[A-Za-z\\d@$!%*?&]{7,}$", message = "Password must contain at least one uppercase")
    private String newPassword;
    @NotEmpty
    @NotBlank
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d?)[A-Za-z\\d@$!%*?&]{7,}$", message = "Password must contain at least one uppercase")
    private String confirmPassword;

}
