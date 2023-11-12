package dev.localservicesreview.ratingservice.services;

import dev.localservicesreview.ratingservice.exceptions.InternalServerException;
import dev.localservicesreview.ratingservice.thirdpartyclients.notificationSvc.NotificationRequestDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface NotificationService {
    public void sendNotification(NotificationRequestDto notificationRequestDto, UUID userId)
            throws InternalServerException;
}
