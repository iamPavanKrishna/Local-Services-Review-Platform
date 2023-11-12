package com.shk.userservice.services;

import com.shk.userservice.dtos.*;
import com.shk.userservice.models.Role;
import com.shk.userservice.models.User;
import com.shk.userservice.repositories.UserRepository;
import com.shk.userservice.thirdPartyIntegration.TPIUserNotification;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.DoubleStream;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TPIUserNotification tpiUserNotification;

    public User signUp(SignupRequestDto signupRequest) {
        var user = User.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword())) // Add closing parenthesis here
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);

         SendNotificationRequestDto sendNotificationRequestDto = SendNotificationRequestDto.builder()
                 .type("info")
                 .channel("email")
                 .subject("Welcome to Local Review Service")
                 .data("Thanks" + savedUser.getName() + "for registering to our service")
                 .build();


        SendNotificationResponseDto sendNotificationResponseDto = tpiUserNotification.sendNotification(sendNotificationRequestDto, "http://notificationservice.eu-north-1.elasticbeanstalk.com/notification/send/"+savedUser.getUserId());
        return savedUser;

//        var jwtToken = jwtService.generateToken(user); // Generate JWT token here
//        return AuthenticationResponseDto.builder()
//                .token(jwtToken)
//                .build();
    }

    public AuthenticationResponseDto login(LoginRequestDto loginRequest) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));

        var jwtToken = jwtService.generateToken(user); // Generate JWT token here
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user); // Generate refresh token here
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    public AuthenticationResponseDto validateToken(ValidateTokenRequestDto validateTokenRequest) {
        var userEmail = jwtService.extractUsername(validateTokenRequest.getToken());
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Token"));

        if(jwtService.isTokenValid(validateTokenRequest.getToken(), user)) {
            var jwtToken = jwtService.generateToken(user); // Generate JWT token here

            return AuthenticationResponseDto.builder()
                    .token(jwtToken)
                    .refreshToken(validateTokenRequest.getToken())
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid Token");
        }
    }

}