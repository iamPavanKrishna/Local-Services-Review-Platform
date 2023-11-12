package dev.localservicesreview.ratingservice.thirdpartyclients.notificationSvc;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NotificationRequestDto {
    private UUID service_id;
    private String data;
    private String image_url;
    private Channel channel = Channel.EMAIL;
    private String type = "alert";
    private String subject;
}

