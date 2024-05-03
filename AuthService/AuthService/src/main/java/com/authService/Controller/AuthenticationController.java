package com.authService.Controller;

import com.authService.Request.*;
import com.authService.Response.AuthenticationResponse;
import com.authService.Response.ForgotPasswordResponse;
import com.authService.Response.UpdatePasswordResponse;
import com.authService.Services.AuthenticationService;
import com.authService.Services.ForgotPasswordService;
import com.authService.Services.Impl.AuthenticationServiceImpl;
import com.authService.Services.Impl.ForgotPasswordServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationServiceImpl;
    private final ForgotPasswordServiceImpl forgotPasswordServiceImpl;
    private final ForgotPasswordService forgotPasswordService;
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request){

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @GetMapping("/activate-account")
    public void confirm(@RequestParam String token) throws MessagingException {
        System.out.println(token);
        authenticationService.activateAccount(token);
    }

    @PostMapping("/forgot")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        System.out.println("hello hello");
        ForgotPasswordResponse response = forgotPasswordService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/update")
    public ResponseEntity<UpdatePasswordResponse> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        String password = updatePasswordRequest.getPassword();
        String confirmPassword = updatePasswordRequest.getConfirmpassword();

        if (!password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().build();
        }
        UpdatePasswordResponse response = forgotPasswordService.updatePassword(updatePasswordRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/block/{userId}")
    public boolean blockUser(@PathVariable UUID userId) {
       boolean response =  authenticationService.blockUser(userId);
        return response;
    }

    @GetMapping("/unBlock/{userId}")
    public boolean unBlockUser(@PathVariable UUID userId) {
        boolean response =  authenticationService.UnBlockUser(userId);
        return response;
    }

    @GetMapping("/isBlocked/{userId}")
    public ResponseEntity<Boolean> isUserBlocked(@PathVariable UUID userId) {
        boolean isBlocked = authenticationService.isUserBlocked(userId);

        return ResponseEntity.ok(isBlocked);
    }
    @GetMapping("/user-role/{userId}")
    public String getRole(@PathVariable UUID userId){
         String userRole = authenticationService.getUserRole(userId);
        return userRole;

    }
//    @GetMapping("/token-expired/{token}")
//    public void tokenExpired(@PathVariable String token){
//        authenticationServiceImpl.expiredToken(token);
//
//    }

    @GetMapping("/generate-token/{email}")
    public void generateToken(@PathVariable("email") String email) throws MessagingException {

        authenticationService.generateToken(email);
    }

    @PostMapping("/verify-password")//reset-password
    public ResponseEntity<String> verifyPassword(@RequestBody PasswordVerificationRequest request) {
        final ResponseEntity<String> response = forgotPasswordService.verifyAndUpdatePassword(
                request.getCurrentPassword(),
                request.getNewPassword(),
                request.getConfirmPassword(),
                request.getId());
        return response;

    }



}
