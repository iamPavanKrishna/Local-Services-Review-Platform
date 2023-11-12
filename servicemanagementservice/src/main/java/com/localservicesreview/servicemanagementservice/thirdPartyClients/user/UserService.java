package com.localservicesreview.servicemanagementservice.thirdPartyClients.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.localservicesreview.servicemanagementservice.dtos.AuthenticationResponseDto;
import com.localservicesreview.servicemanagementservice.dtos.LoginRequestDto;
import com.localservicesreview.servicemanagementservice.dtos.ReviewDto;
import com.localservicesreview.servicemanagementservice.dtos.SignupRequestDto;
import com.localservicesreview.servicemanagementservice.dtos.SignupResponseDto;
import com.localservicesreview.servicemanagementservice.dtos.ValidateTokenRequestDto;

@Service
public class UserService {

    private RestTemplate restTemplate;
    private String usersUrl;
    private String signupEndpoint;
    private String loginEndpoint;
    private String validateEndpoint;
    public UserService(RestTemplateBuilder restTemplateBuilder, @Value("${users.url}") String usersUrl,
        @Value("${users.api.signup.endpoint}") String signupEndpoint, @Value("${users.api.login.endpoint}") String loginEndpoint, @Value("${users.api.validate.endpoint}") String validateEndpoint){

        restTemplate = restTemplateBuilder.build();
        this.usersUrl = usersUrl;
        this.signupEndpoint = signupEndpoint;
        this.loginEndpoint = loginEndpoint;
        this.validateEndpoint = validateEndpoint;
    }

    public boolean validateToken(String token){
        ValidateTokenRequestDto dto = new ValidateTokenRequestDto();
        dto.setToken(token);
        ResponseEntity<AuthenticationResponseDto> response = restTemplate.postForEntity(usersUrl+validateEndpoint, dto,
            AuthenticationResponseDto.class);
        if(!response.getBody().getToken().isEmpty()){
            return true;
        }
        return false;
    }

    public SignupResponseDto createUser(SignupRequestDto requestDto){
        ResponseEntity<SignupResponseDto> response = restTemplate.postForEntity(usersUrl+signupEndpoint, requestDto,
            SignupResponseDto.class);
        return response.getBody();
    }

    public AuthenticationResponseDto loginUser(LoginRequestDto requestDto){
        ResponseEntity<AuthenticationResponseDto> response = restTemplate.postForEntity(usersUrl+loginEndpoint, requestDto,
            AuthenticationResponseDto.class);
        return response.getBody();
    }

}
