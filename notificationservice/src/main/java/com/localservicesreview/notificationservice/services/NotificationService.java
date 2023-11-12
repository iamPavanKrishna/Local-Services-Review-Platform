package com.localservicesreview.notificationservice.services;

import com.localservicesreview.notificationservice.dtos.BulkNotificationRequestDto;
import com.localservicesreview.notificationservice.dtos.NotificationRequestDto;
import com.localservicesreview.notificationservice.dtos.NotificationResponseDto;
import com.localservicesreview.notificationservice.dtos.SendNotificationResponseDto;
import com.localservicesreview.notificationservice.exceptions.BadRequestException;
import com.localservicesreview.notificationservice.exceptions.ForbiddenRequestException;
import com.localservicesreview.notificationservice.exceptions.NotFoundException;

import java.util.List;

public interface NotificationService{
    void sendBulkNotification(BulkNotificationRequestDto bulkNotificationRequestDto) throws BadRequestException, NotFoundException;

    SendNotificationResponseDto sendSingleNotification(NotificationRequestDto bulkNotificationRequestDto, String userId) throws BadRequestException, NotFoundException;

    List<NotificationResponseDto> getAllNotifications(String userId) throws NotFoundException;
    NotificationResponseDto getNotification(String userId, String notificationId) throws NotFoundException, ForbiddenRequestException;

    void deleteNotification(String userId, String notificationId) throws NotFoundException, ForbiddenRequestException;

    void markNotificationAsRead(String userId, String notificationId) throws NotFoundException, ForbiddenRequestException;
}
