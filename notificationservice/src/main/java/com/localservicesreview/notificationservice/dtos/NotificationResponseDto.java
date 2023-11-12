package com.localservicesreview.notificationservice.dtos;

import com.localservicesreview.notificationservice.models.NotificationChannelType;
import com.localservicesreview.notificationservice.models.NotificationDeliveryStatus;
import com.localservicesreview.notificationservice.models.NotificationReadStatus;
import com.localservicesreview.notificationservice.models.NotificationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Getter
@Setter
public class NotificationResponseDto {
    private String id;
    private String userId;
    private String readStatus;
    private String messageBody;
    private String imageURL;
    private String deliveryStatus;
    private String notificationType;
    private String NotificationChannel;
    private String subject;
}
