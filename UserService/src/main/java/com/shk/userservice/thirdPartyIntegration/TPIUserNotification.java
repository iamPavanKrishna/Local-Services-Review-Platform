package com.shk.userservice.thirdPartyIntegration;

import com.shk.userservice.dtos.SendNotificationRequestDto;
import com.shk.userservice.dtos.SendNotificationResponseDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TPIUserNotification {

        private final RestTemplate restTemplate;

        public TPIUserNotification(RestTemplateBuilder restTemplateBuilder){
            this.restTemplate = restTemplateBuilder.build();
        }
        public SendNotificationResponseDto sendNotification(SendNotificationRequestDto sendNotificationRequestDto, String url) {

            ResponseEntity<SendNotificationResponseDto> response = restTemplate.postForEntity(url, sendNotificationRequestDto,SendNotificationResponseDto.class);

            return response.getBody();
        }

}
