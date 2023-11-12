package com.localservicesreview.notificationservice.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenRequestException extends Exception{
    public ForbiddenRequestException(String message){
        super(message);
    }
}
