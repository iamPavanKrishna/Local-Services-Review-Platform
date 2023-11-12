package com.localservicesreview.notificationservice.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BulkNotificationRequestDto {
    private String service_id;
    private String data;
    private String image_url;
    private String channel;
    private String type;
    private String subject;
}
