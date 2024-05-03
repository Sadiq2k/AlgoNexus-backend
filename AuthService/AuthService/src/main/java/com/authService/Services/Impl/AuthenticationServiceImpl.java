package com.authService.Services.Impl;



import com.authService.Dto.UserDto;
import com.authService.Entities.Role;

import com.authService.FiegnClient.UserServiceFeignClient;
import com.authService.Request.AuthenticationRequest;
import com.authService.Request.RegistrationRequest;
import com.authService.Response.AuthenticationResponse;
import com.authService.Services.AuthenticationService;
import com.authService.email.EmailService;
import com.authService.email.EmailTemplateName;
import com.authService.Repository.RoleRepository;
import com.authService.JwtSecurtiy.JwtService;
import com.authService.Entities.Token;
import com.authService.Repository.TokenRepository;
import com.authService.Entities.User;
import com.authService.Repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    //feign client communication
    private final UserServiceFeignClient userServiceFeignClient;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;


        public String register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
            var userRole = roleRepository.findByName("USER")
                    .orElseThrow(()->new IllegalStateException("ROLE USER was not initialized"));



            final boolean b = validatePassword(request.getPassword());
            if (!b) {
                System.out.println("invalid password");
                return "Password must start with an uppercase letter....";
            }

            if(userRepository.existsByEmail(request.getEmail())){
                return "A user is already registered with this e-mail address.";
            }

                var user = User.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .accountLocked(false)
                        .enabled(false)
                        .roles(List.of(userRole))
                        .build();

                userRepository.save(user);
                sendValidationEmail(user);

                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setFirstname(user.getFirstname());
                userDto.setLastname(user.getLastname());
                userDto.setEmail(user.getEmail());
            userServiceFeignClient.sendUser(userDto);

            return "";
        }
    public static boolean validatePassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d?)[A-Za-z\\d@$!%*?&]{7,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches() && Character.isUpperCase(password.charAt(0));
    }

  public void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
//        System.out.println(newToken+">>>>>>>>>>>>>>>>>>>>>>>>");
        // send email token

        emailService.sendEmail(
                user.getEmail(),
                user.getFirstname(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"

        );


    }

    private String generateAndSaveActivationToken(User user) {
        String generadtedToken = generatedActivationCode(6);
        var token = Token.builder()
                .token(generadtedToken)
                .createdAt(LocalDateTime.now().plusMinutes(1))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generadtedToken;
    }

    private String generatedActivationCode(int length) {

        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));

        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String,Object>();
        User user = ((User)auth.getPrincipal());
        claims.put("fullName",user.getFirstname());
        var jwtToken = jwtService.generateToken(claims,user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(user)
                .build();
    }

    public void activateAccount(String token) {
        try {
            Token savedToken = tokenRepository.findByToken(token)
                    .orElseThrow(() -> new EmptyResultDataAccessException("Invalid token", 1));

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime tokenCreatedAt = savedToken.getCreatedAt();
//            LocalDateTime tokenExpirationTime = tokenCreatedAt.plusMinutes(2);


            if (now.isBefore(tokenCreatedAt)) {
                var user = userRepository.findByEmail(savedToken.getUser().getEmail())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                if (user.getEmail().equals(savedToken.getUser().getEmail())) {

                    user.setEnabled(true);
                    userRepository.save(user);

                    savedToken.setValidatedAt(now);
                    tokenRepository.save(savedToken);
                }
            } else {
                throw new RuntimeException("Token has expired");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Invalid token: " + token, e);
        }
    }


    public boolean blockUser(UUID userId) {
        var user = userRepository.findById(userId);
        if (user != null) {
            user.setAccountLocked(true);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean UnBlockUser(UUID userId) {
        var user = userRepository.findById(userId);
        if (user != null) {
            user.setAccountLocked(false);
            userRepository.save(user);
            return false;
        } else {
            return true;
        }
    }

    public boolean isUserBlocked(UUID userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            return user.isAccountLocked();
        } else {
            return false;
        }
    }

    public String getUserRole(UUID userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            List<String> roleNames = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());
            return roleNames.toString();
        }
        return null;
    }

    public void generateToken(String email) throws MessagingException {

            User user = userRepository.findByEmail(email).get();
                     sendValidationEmail(user);

    }

}
