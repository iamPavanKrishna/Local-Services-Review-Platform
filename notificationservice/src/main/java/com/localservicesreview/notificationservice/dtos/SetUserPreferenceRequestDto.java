package com.localservicesreview.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetUserPreferenceRequestDto {
    private String user_id;
    private List<UserPreferenceRequestDto> preferences;
}
