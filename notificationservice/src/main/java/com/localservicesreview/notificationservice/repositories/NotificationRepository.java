package com.localservicesreview.notificationservice.repositories;

import com.localservicesreview.notificationservice.models.Notification;
import com.localservicesreview.notificationservice.models.NotificationDeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findAllByUserId(UUID uuid);
}
