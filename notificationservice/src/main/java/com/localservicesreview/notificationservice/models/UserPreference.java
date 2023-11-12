package com.localservicesreview.notificationservice.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference extends BaseModel{
    @NotNull
    private UUID userId;
    private UUID serviceId;
    private NotificationType notificationType;
    @NotNull
    private NotificationChannelType notificationChannelType;
    private boolean subscribed = true;
    private NotificationFrequency notificationFrequency = NotificationFrequency.DAILY;
}
