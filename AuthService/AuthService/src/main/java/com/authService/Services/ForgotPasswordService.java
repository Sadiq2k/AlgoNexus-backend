package com.authService.Services;



import com.authService.Model.Request.*;
import com.authService.Model.Response.*;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ForgotPasswordService {
    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    UpdatePasswordResponse updatePassword(UpdatePasswordRequest updatePasswordRequest);

    ResponseEntity<String> verifyAndUpdatePassword(String currentPassword, String newPassword, String confirmPassword, UUID id);

}
