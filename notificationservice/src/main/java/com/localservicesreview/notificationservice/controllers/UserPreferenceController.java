package com.localservicesreview.notificationservice.controllers;

import com.localservicesreview.notificationservice.dtos.SetUserPreferenceRequestDto;
import com.localservicesreview.notificationservice.dtos.UnsetUserPreferenceRequestDto;
import com.localservicesreview.notificationservice.dtos.UserPreferenceResponseDto;
import com.localservicesreview.notificationservice.exceptions.NotFoundException;
import com.localservicesreview.notificationservice.models.UserPreference;
import com.localservicesreview.notificationservice.services.UserPreferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/preference")
public class UserPreferenceController {
    private UserPreferenceService userPreferenceService;

    public UserPreferenceController(UserPreferenceService userPreferenceService){
        this.userPreferenceService = userPreferenceService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> setPreference(@RequestBody SetUserPreferenceRequestDto setUserPreferenceRequestDto){
        List<UserPreference> userPreferences = userPreferenceService.setPreference(setUserPreferenceRequestDto.getUser_id(), setUserPreferenceRequestDto.getPreferences());
        UserPreferenceResponseDto userPreferenceResponseDtos = new UserPreferenceResponseDto();
        userPreferenceResponseDtos.setUserPreferenceList(userPreferences);
        userPreferenceResponseDtos.setUser_id(userPreferences.get(0).getUserId().toString());
        return new ResponseEntity<>(userPreferenceResponseDtos, HttpStatus.OK);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsetPreference(@RequestBody UnsetUserPreferenceRequestDto userPreferenceRequestDto) throws NotFoundException {
        Optional<List<UserPreference>> userPreferences = userPreferenceService.unsetPreference(userPreferenceRequestDto.getUser_id(),userPreferenceRequestDto.getServiceidList());
        if(userPreferences.isEmpty()){
            throw new NotFoundException("No user preferences found for the given service ids");
        }
        List<UserPreference> userPreferenceList = userPreferences.get();
        UserPreferenceResponseDto userPreferenceResponseDtos = new UserPreferenceResponseDto();
        userPreferenceResponseDtos.setUserPreferenceList(userPreferenceList);
        userPreferenceResponseDtos.setUser_id(userPreferenceList.get(0).getUserId().toString());
        return new ResponseEntity<>(userPreferenceResponseDtos, HttpStatus.OK);
    }
}
