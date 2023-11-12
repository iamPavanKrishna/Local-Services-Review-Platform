package com.localservicesreview.notificationservice.ThirdPartyUserService;

import com.localservicesreview.notificationservice.dtos.UserDetailsDto;

public interface ThirdPartyUserService {
    UserDetailsDto getUserDetails(String userId);
}
