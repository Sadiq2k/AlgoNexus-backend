package com.authService.Services.Impl;

import com.authService.Entities.User;
import com.authService.FiegnClient.UserServiceFeignClient;
import com.authService.JwtSecurtiy.JwtService;
import com.authService.Request.ForgotPasswordRequest;
import com.authService.Request.UpdatePasswordRequest;
import com.authService.Response.ForgotPasswordResponse;
import com.authService.Response.UpdatePasswordResponse;
import com.authService.Services.AuthenticationService;
import com.authService.Services.ForgotPasswordService;
import com.authService.Services.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Objects;
import java.util.SimpleTimeZone;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordServiceImpl.class);


    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest emailRequest) {
        User user = (User) userService.findByEmail(emailRequest.getEmail()).orElse(null);

        if (user == null) {
            return ForgotPasswordResponse.builder()
                    .errorMessage("User not found for email: " + emailRequest.getEmail())
                    .build();
        }

        try {
            authenticationService.sendValidationEmail(user);

            var claims = new HashMap<String, Object>();
            claims.put("fullName", user.getFirstname());
            var jwtToken = jwtService.generateToken(claims, user);

            return ForgotPasswordResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed for email: " + emailRequest.getEmail());
            e.printStackTrace(); // Print the stack trace for detailed error analysis
            return ForgotPasswordResponse.builder()
                    .errorMessage("Authentication failed for email: " + emailRequest.getEmail())
                    .build();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public UpdatePasswordResponse updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        String newPassword = updatePasswordRequest.getPassword();
        String confirmPassword = updatePasswordRequest.getConfirmpassword();
        String email = updatePasswordRequest.getEmail();

        if (!newPassword.equals(confirmPassword)) {
            return new UpdatePasswordResponse("Password and confirm password do not match");
        }
        User user = (User) userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(user);
        return new UpdatePasswordResponse("Password updated successfully");
    }


    public ResponseEntity<String> verifyAndUpdatePassword(String currentPassword, String newPassword, String confirmPassword, UUID id) {
        User user = userService.findUser(id);
        String realPassword = user.getPassword();

        if (passwordEncoder.matches(currentPassword, realPassword)) {
            if (newPassword.equals(confirmPassword)) {
                String newEncodedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(newEncodedPassword);
                userService.saveUser(user);
                return ResponseEntity.ok().body("{\"message\": \"Password updated successfully\"}");
            } else {
                return ResponseEntity.badRequest().body("{\"error\": \"New password and confirm password do not match\"}");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Current password is incorrect\"}");
        }
    }
}
