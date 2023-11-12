package com.localservicesreview.notificationservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification extends BaseModel{
    @NotNull
    private UUID userId;
    private NotificationReadStatus readStatus = NotificationReadStatus.UNREAD;
    @Column(name = "message_body")
    @NotNull
    private String messageBody;
    private String subject;
    private String imageURL;
    private NotificationDeliveryStatus deliveryStatus = NotificationDeliveryStatus.PENDING;
    private int retryCount = 0;
    private NotificationType type;
    @NotNull
    private NotificationChannelType channelType;
}
