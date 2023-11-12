package com.localservicesreview.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendNotificationResponseDto {
    private String message;
    private String service_id;
}
