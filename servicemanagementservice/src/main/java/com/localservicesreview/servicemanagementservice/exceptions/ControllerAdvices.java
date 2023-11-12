package com.localservicesreview.servicemanagementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.localservicesreview.servicemanagementservice.dtos.ExceptionDto;

@ControllerAdvice
public class ControllerAdvices {

    @ExceptionHandler(ServiceNotFoundException.class)
    private ResponseEntity<ExceptionDto> handleNotFoundException(ServiceNotFoundException ex) {
        return new ResponseEntity(
            new ExceptionDto(HttpStatus.NOT_FOUND, ex.getMessage()),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    private ResponseEntity<ExceptionDto> handleNotFoundException(CategoryNotFoundException ex) {
        return new ResponseEntity(
            new ExceptionDto(HttpStatus.NOT_FOUND, ex.getMessage()),
            HttpStatus.NOT_FOUND
        );
    }
}
