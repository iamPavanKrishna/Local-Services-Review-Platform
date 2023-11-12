package com.localservicesreview.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum NotificationFrequency {
    DAILY("d"), WEEKLY("w"), MONTHLY("m");

    private String frequency;
    NotificationFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFrequency() {
        return this.frequency;
    }
    @JsonCreator
    public static NotificationFrequency fromString(String frequency) {
        for (NotificationFrequency notificationFrequency : NotificationFrequency.values()) {
            if (notificationFrequency.frequency.equalsIgnoreCase(frequency)) {
                return notificationFrequency;
            }
        }
        return null;
    }
}
