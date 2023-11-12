package dev.localservicesreview.ratingservice.thirdpartyclients.notificationSvc;

import dev.localservicesreview.ratingservice.exceptions.InternalServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class NotificationSvcClient {
    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${notificationSvc.base.url}")
    private String notificationSvcBaseUrl = "https://demoNotificationSvc.com";

    @Value("${notificationSvc.send}")
    private String notificationSend = "/send";

    public NotificationSvcClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public void sendNotification(NotificationRequestDto notificationReqDto, UUID userId) throws InternalServerException {
        try {
            RestTemplate restTemplate = restTemplateBuilder.build();
            ResponseEntity<String> response =
                    restTemplate.postForEntity(notificationSvcBaseUrl + notificationSend + "/" + userId,
                            notificationReqDto, String.class);
//            var response =
//                    restTemplate.postForObject(notificationSvcBaseUrl + notificationSend + "/" + userId,
//                            notificationReqDto, ResponseEntity.class);

            System.out.println(response);

//            if(response.getStatusCode().equals(404))
//                throw new InternalServerException("Notification service returned not found");
//
//
//            if(response.getStatusCode().equals(500))
//                throw new InternalServerException("Notification service returned bad request");

//            return response;
        } catch (Exception e) {
            System.out.println(e);
            throw new InternalServerException("Error occurred at notification service.");
        }
    }
}
