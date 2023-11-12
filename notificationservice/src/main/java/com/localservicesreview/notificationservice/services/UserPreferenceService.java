package com.localservicesreview.notificationservice.services;

import com.localservicesreview.notificationservice.dtos.UserPreferenceRequestDto;
import com.localservicesreview.notificationservice.exceptions.NotFoundException;
import com.localservicesreview.notificationservice.models.NotificationChannelType;
import com.localservicesreview.notificationservice.models.NotificationFrequency;
import com.localservicesreview.notificationservice.models.NotificationType;
import com.localservicesreview.notificationservice.models.UserPreference;
import com.localservicesreview.notificationservice.repositories.UserPreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserPreferenceService {
    private UserPreferenceRepository userPreferenceRepository;
    public UserPreferenceService(UserPreferenceRepository userPreferenceRepository){
        this.userPreferenceRepository = userPreferenceRepository;
    }
    public List<UserPreference> setPreference(String userId,List<UserPreferenceRequestDto> setUserPreferenceRequestDto) {
        List<UserPreference> userPreferenceList = new ArrayList<>();
        for(UserPreferenceRequestDto userPreferenceRequest : setUserPreferenceRequestDto){
            UserPreference userPreference = new UserPreference();
            userPreference.setUserId(UUID.fromString(userId));
            userPreference.setServiceId(UUID.fromString(userPreferenceRequest.getService_id()));
            userPreference.setSubscribed(true);
            userPreference.setNotificationType(NotificationType.fromString(userPreferenceRequest.getType()));
            userPreference.setNotificationChannelType(NotificationChannelType.fromString(userPreferenceRequest.getChannel()));
            userPreference.setNotificationFrequency(NotificationFrequency.fromString(userPreferenceRequest.getFrequency()));
            userPreferenceList.add(userPreference);
        }
        List<UserPreference>savedUserPreferenceList = userPreferenceRepository.saveAll(userPreferenceList);
        return savedUserPreferenceList;
    }

    public Optional<List<UserPreference>> unsetPreference(String userId,List<String> serviceIdList) throws NotFoundException {
        List<UserPreference> userPreferenceOptional = userPreferenceRepository.findOneByUserId(UUID.fromString(userId));
        if(userPreferenceOptional.size()==0){
            throw new NotFoundException("No user preferences found for the given user id: "+userId);
        }
        List<UUID>serviceIds = new ArrayList<>();
        for(String serviceId : serviceIdList){
            serviceIds.add(UUID.fromString(serviceId));
        }
        Optional<List<UserPreference>> userPreferenceOptionalList = userPreferenceRepository.deleteByServiceIdInAndUserId(serviceIds,UUID.fromString(userId));
        if(userPreferenceOptionalList.get().isEmpty()){
            throw new NotFoundException("No user preferences found for the given service ids");
        }
        return Optional.of(userPreferenceOptionalList.get());
    }
}
