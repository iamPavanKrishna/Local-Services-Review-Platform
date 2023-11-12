package com.localservicesreview.notificationservice.dtos;

import com.localservicesreview.notificationservice.models.UserPreference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserPreferenceResponseDto {
    private List<UserPreference> userPreferenceList;
    private String user_id;
}
