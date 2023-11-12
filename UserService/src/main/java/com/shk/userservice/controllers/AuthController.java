package com.shk.userservice.controllers;

import com.shk.userservice.dtos.AuthenticationResponseDto;
import com.shk.userservice.dtos.LoginRequestDto;
import com.shk.userservice.dtos.SignupRequestDto;
import com.shk.userservice.dtos.ValidateTokenRequestDto;
import com.shk.userservice.models.User;
import com.shk.userservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignupRequestDto signupRequest) {
        return ResponseEntity.ok(authService.signUp(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/validate")
    public ResponseEntity<AuthenticationResponseDto> validateToken(@RequestBody ValidateTokenRequestDto validateTokenRequest) {
        return ResponseEntity.ok(authService.validateToken(validateTokenRequest));
    }


}
