package com.localservicesreview.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum NotificationType {
    ALERT("alert"), PROMOTIONAL("promo"), INFORMATION("info");

    private String notifType;
    NotificationType(String notifType){
        this.notifType = notifType;
    }
    public String getNotificationType(){
        return this.notifType;
    }
    @JsonCreator
    public static NotificationType fromString(String notifType){
        for(NotificationType notificationType : NotificationType.values()){
            if(notificationType.notifType.equalsIgnoreCase(notifType)){
                return notificationType;
            }
        }
        return null;
    }
}
