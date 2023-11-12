package com.localservicesreview.notificationservice.producer;

import com.localservicesreview.notificationservice.dtos.UserDetailsDto;

import java.util.UUID;

public interface NotificationProducer {
    String sendNotificationEvent(String exchangeName, String routingKey, UserDetailsDto userDetailsDto, UUID notificationId);
}
