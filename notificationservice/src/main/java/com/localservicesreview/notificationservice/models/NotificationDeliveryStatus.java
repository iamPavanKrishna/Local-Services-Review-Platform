package com.localservicesreview.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum NotificationDeliveryStatus {
    FAILED("failed"), DELIVERED("sent"), PENDING("pending");

    private String deliveryStatus;

    NotificationDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryStatus() {
        return this.deliveryStatus;
    }
    @JsonCreator
    public static NotificationDeliveryStatus fromString(String deliveryStatus) {
        for (NotificationDeliveryStatus notificationDeliveryStatus : NotificationDeliveryStatus.values()) {
            if (notificationDeliveryStatus.deliveryStatus.equalsIgnoreCase(deliveryStatus)) {
                return notificationDeliveryStatus;
            }
        }
        return null;
    }
}
