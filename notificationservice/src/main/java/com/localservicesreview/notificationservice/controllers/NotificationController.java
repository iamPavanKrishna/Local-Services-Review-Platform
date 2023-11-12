package com.localservicesreview.notificationservice.controllers;

import com.localservicesreview.notificationservice.dtos.BulkNotificationRequestDto;
import com.localservicesreview.notificationservice.dtos.NotificationRequestDto;
import com.localservicesreview.notificationservice.dtos.SendNotificationResponseDto;
import com.localservicesreview.notificationservice.exceptions.BadRequestException;
import com.localservicesreview.notificationservice.exceptions.ForbiddenRequestException;
import com.localservicesreview.notificationservice.exceptions.NotFoundException;
import com.localservicesreview.notificationservice.services.NotificationService;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }
    @PostMapping("/send")
    public ResponseEntity<?> sendBulkNotification(@RequestBody BulkNotificationRequestDto bulkNotificationRequestDto) throws BadRequestException, NotFoundException {
        notificationService.sendBulkNotification(bulkNotificationRequestDto);
        SendNotificationResponseDto sendNotificationResponseDto = new SendNotificationResponseDto();
        sendNotificationResponseDto.setMessage("Notifications are being sent...");
        sendNotificationResponseDto.setService_id(bulkNotificationRequestDto.getService_id());
        return new ResponseEntity<>(sendNotificationResponseDto, HttpStatus.OK);
    }

    @PostMapping("/send/{userid}")
    public ResponseEntity<?> sendSingleNotification(@RequestBody NotificationRequestDto notificationRequestDto, @PathVariable("userid") String userId) throws BadRequestException, NotFoundException {
        SendNotificationResponseDto sendNotificationResponseDto = notificationService.sendSingleNotification(notificationRequestDto,userId);
        return new ResponseEntity<>(sendNotificationResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getNotifications(@RequestParam("user") String userid, @Nullable @RequestParam("notification") String notificationid) throws NotFoundException, ForbiddenRequestException, BadRequestException {
        if(userid==null){
            throw new BadRequestException("User_Id should be passed as a query parameter in the request");
        }
        if(notificationid!=null){
            return new ResponseEntity<>(notificationService.getNotification(userid, notificationid), HttpStatus.OK);
        }
        return new ResponseEntity<>(notificationService.getAllNotifications(userid), HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteNotification(@Nullable @RequestParam("user") String userId, @Nullable @RequestParam("notification") String notificationId) throws NotFoundException, ForbiddenRequestException, BadRequestException {
        if(userId==null || notificationId==null){
            System.out.println("This worked");
            throw new BadRequestException("Parameters user and notification should be passed as query parameters in the request");
        }
        notificationService.deleteNotification(userId,notificationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/read")
    public ResponseEntity<?> markNotificationAsRead(@Nullable @RequestParam("user") String userId, @Nullable @RequestParam("notification") String notificationId) throws NotFoundException, ForbiddenRequestException, BadRequestException {
        if(userId==null || notificationId==null){
            throw new BadRequestException("Parameters user and notification should be passed as query parameters in the request");
        }
        notificationService.markNotificationAsRead(userId,notificationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
