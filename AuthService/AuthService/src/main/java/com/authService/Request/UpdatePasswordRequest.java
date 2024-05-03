package com.authService.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdatePasswordRequest {

    @NotEmpty
    @NotBlank
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d?)[A-Za-z\\d@$!%*?&]{7,}$", message = "Password must contain at least one uppercase letter and one digit")
    private String password;

    @NotEmpty
    @NotBlank
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d?)[A-Za-z\\d@$!%*?&]{7,}$", message = "Password must contain at least one uppercase letter and one digit")
    private String confirmpassword;

    private String email;


}
