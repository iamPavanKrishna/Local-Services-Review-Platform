package dev.localservicesreview.ratingservice.services;

import dev.localservicesreview.ratingservice.exceptions.InternalServerException;
import dev.localservicesreview.ratingservice.thirdpartyclients.notificationSvc.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("TPANotificationService")
@Primary
public class TPANotificationService implements NotificationService {
    private final NotificationSvcClient notificationSvcClient;

    public TPANotificationService(NotificationSvcClient notificationSvcClient) {
        this.notificationSvcClient = notificationSvcClient;
    }

    @Override
    public void sendNotification(NotificationRequestDto notificationRequestDto, UUID userId) throws InternalServerException {
        notificationSvcClient.sendNotification(notificationRequestDto, userId);
    }
}
