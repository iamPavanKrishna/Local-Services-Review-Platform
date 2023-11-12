package com.localservicesreview.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum NotificationReadStatus {
    UNREAD(0), READ(1);

    private int value;
    NotificationReadStatus(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    @JsonCreator
    public static NotificationReadStatus fromString(int value){
        for(NotificationReadStatus notificationReadStatus : NotificationReadStatus.values()){
            if(notificationReadStatus.value == value){
                return notificationReadStatus;
            }
        }
        return null;
    }
}
