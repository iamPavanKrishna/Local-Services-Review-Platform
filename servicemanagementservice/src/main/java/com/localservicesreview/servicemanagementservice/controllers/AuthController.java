package com.localservicesreview.servicemanagementservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.localservicesreview.servicemanagementservice.dtos.AuthenticationResponseDto;
import com.localservicesreview.servicemanagementservice.dtos.LoginRequestDto;
import com.localservicesreview.servicemanagementservice.dtos.SignupRequestDto;
import com.localservicesreview.servicemanagementservice.dtos.SignupResponseDto;
import com.localservicesreview.servicemanagementservice.thirdPartyClients.user.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> createUser(@RequestBody SignupRequestDto request){
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> loginUser(@RequestBody LoginRequestDto request){
        return new ResponseEntity<>(userService.loginUser(request), HttpStatus.OK);
    }

}
