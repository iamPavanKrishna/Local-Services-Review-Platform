package com.localservicesreview.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum NotificationChannelType {
    EMAIL("email"), SMS("sms"), PUSH("push");

    private String channelType;

    NotificationChannelType(String channelType) {
        this.channelType = channelType;
    }
    public String getChannelType() {
        return this.channelType;
    }
    @JsonCreator
    public static NotificationChannelType fromString(String channelType) {
        for (NotificationChannelType notificationChannelType : NotificationChannelType.values()) {
            if (notificationChannelType.channelType.equalsIgnoreCase(channelType)) {
                return notificationChannelType;
            }
        }
        return null;
    }
}
