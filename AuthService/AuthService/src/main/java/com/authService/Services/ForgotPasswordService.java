package com.authService.Services;

import com.authService.Request.ForgotPasswordRequest;
import com.authService.Request.UpdatePasswordRequest;
import com.authService.Response.ForgotPasswordResponse;
import com.authService.Response.UpdatePasswordResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ForgotPasswordService {
    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    UpdatePasswordResponse updatePassword(UpdatePasswordRequest updatePasswordRequest);

    ResponseEntity<String> verifyAndUpdatePassword(String currentPassword, String newPassword, String confirmPassword, UUID id);

}
