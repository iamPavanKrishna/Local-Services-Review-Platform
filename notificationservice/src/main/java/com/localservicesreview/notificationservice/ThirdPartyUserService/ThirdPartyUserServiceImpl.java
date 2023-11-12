package com.localservicesreview.notificationservice.ThirdPartyUserService;

import com.localservicesreview.notificationservice.dtos.UserDetailsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Primary
public class ThirdPartyUserServiceImpl implements ThirdPartyUserService{
    @Value("${userservice.baseurl}")
    private String userServiceURL;
    private RestTemplate restTemplate;

    public ThirdPartyUserServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    @Override
    public UserDetailsDto getUserDetails(String userId) {
        try{
            String url = userServiceURL+"/api/v1/"+userId;
            ResponseEntity<UserDetailsDto> userDetailsDtoResponseEntity = restTemplate.getForEntity(url,UserDetailsDto.class);
            return userDetailsDtoResponseEntity.getBody();
        }catch (Exception e){
            System.out.println("Exception in ThirdPartyUserDetailsImpl.getUserDetails()"+e.getMessage());
            return null;
        }
    }
}
