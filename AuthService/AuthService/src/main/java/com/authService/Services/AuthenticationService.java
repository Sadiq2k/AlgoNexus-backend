package com.authService.Services;

import com.authService.Entities.User;
import com.authService.Request.AuthenticationRequest;
import com.authService.Request.RegistrationRequest;
import com.authService.Response.AuthenticationResponse;
import jakarta.mail.MessagingException;

import java.util.UUID;

public interface AuthenticationService {

    String register(RegistrationRequest request) throws MessagingException;

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void activateAccount(String token);

    boolean blockUser(UUID userId);

    boolean UnBlockUser(UUID userId);

    boolean isUserBlocked(UUID userId);

    String getUserRole(UUID userId);

    void sendValidationEmail(User user) throws MessagingException;

    void generateToken(String email) throws MessagingException;

}
