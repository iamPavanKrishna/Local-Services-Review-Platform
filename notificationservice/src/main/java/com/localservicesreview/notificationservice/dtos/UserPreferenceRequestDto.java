package com.localservicesreview.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPreferenceRequestDto {
    private String service_id;
    private String channel;
    private String type;
    private String frequency;
}
