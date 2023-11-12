package com.localservicesreview.notificationservice.services;

import com.localservicesreview.notificationservice.ThirdPartyUserService.ThirdPartyUserService;
import com.localservicesreview.notificationservice.dtos.*;
import com.localservicesreview.notificationservice.exceptions.BadRequestException;
import com.localservicesreview.notificationservice.exceptions.ForbiddenRequestException;
import com.localservicesreview.notificationservice.exceptions.NotFoundException;
import com.localservicesreview.notificationservice.models.*;
import com.localservicesreview.notificationservice.producer.NotificationProducer;
import com.localservicesreview.notificationservice.repositories.NotificationRepository;
import com.localservicesreview.notificationservice.repositories.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Primary
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;
    private UserPreferenceRepository userPreferenceRepository;
    private ThirdPartyUserService thirdPartyUserService;

    private NotificationProducer rabbitMQProducer;
    @Value("${rabbitmq.email.routingkey}")
    private String emailRoutingKey;
    @Value("${rabbitmq.sms.routingkey}")
    private String smsRoutingKey;
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserPreferenceRepository userPreferenceRepository, NotificationProducer rabbitMQProducer, ThirdPartyUserService thirdPartyUserService) {
        this.notificationRepository = notificationRepository;
        this.userPreferenceRepository = userPreferenceRepository;
        this.rabbitMQProducer = rabbitMQProducer;
        this.thirdPartyUserService = thirdPartyUserService;
    }

    private NotificationResponseDto convertNotificationToNotificationResponseDto(Notification notification){
        NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
        notificationResponseDto.setId(notification.getId().toString());
        notificationResponseDto.setUserId(notification.getUserId().toString());
        notificationResponseDto.setReadStatus(NotificationReadStatus.fromString(notification.getReadStatus().getValue()).toString());
        notificationResponseDto.setNotificationChannel(notification.getChannelType().getChannelType());
        notificationResponseDto.setNotificationType(notification.getType().getNotificationType());
        notificationResponseDto.setDeliveryStatus(notification.getDeliveryStatus().getDeliveryStatus());
        notificationResponseDto.setImageURL(notification.getImageURL());
        notificationResponseDto.setMessageBody(notification.getMessageBody());
        notificationResponseDto.setSubject(notification.getSubject());
        return notificationResponseDto;
    }
    @Override
    @Async
    public void sendBulkNotification(BulkNotificationRequestDto bulkNotificationRequestDto) throws BadRequestException, NotFoundException {
        if(bulkNotificationRequestDto.getService_id()==null){
            throw new BadRequestException("Service Id is required");
        }
        NotificationChannelType notificationChannelType = NotificationChannelType.fromString(bulkNotificationRequestDto.getChannel());
        if(notificationChannelType==null){
            throw new BadRequestException("Invalid Notification Channel Type");
        }
        Optional<List<UserPreference>>userPreferenceListOptional = userPreferenceRepository.findByServiceIdAndSubscribed(UUID.fromString(bulkNotificationRequestDto.getService_id()),true);
        if(userPreferenceListOptional.isEmpty()){
            throw new NotFoundException("No user is subscribed to the given service: "+ bulkNotificationRequestDto.getService_id());
        }
        List<UserPreference>userPreferenceList = userPreferenceListOptional.get();
        for(UserPreference userPreference: userPreferenceList){
            Notification notification = Notification.builder().readStatus(NotificationReadStatus.UNREAD)
                    .userId(userPreference.getUserId())
                    .deliveryStatus(NotificationDeliveryStatus.PENDING)
                    .retryCount(0)
                    .messageBody(bulkNotificationRequestDto.getData())
                    .imageURL(bulkNotificationRequestDto.getImage_url())
                    .channelType(notificationChannelType)
                    .type(NotificationType.fromString(bulkNotificationRequestDto.getType()))
                    .subject(bulkNotificationRequestDto.getSubject())
                    .build();
            Notification savedNotification = notificationRepository.save(notification);
            if(savedNotification==null){
                continue;
            }
            UserDetailsDto userDetailsDto = thirdPartyUserService.getUserDetails(userPreference.getUserId().toString());
            if(userDetailsDto==null){
                continue;
            }
            rabbitMQProducer.sendNotificationEvent(exchangeName,emailRoutingKey,userDetailsDto, savedNotification.getId());
        }
    }

    @Override
    public SendNotificationResponseDto sendSingleNotification(NotificationRequestDto notificationRequestDto, String userId) throws BadRequestException, NotFoundException {
        NotificationChannelType notificationChannelType = NotificationChannelType.fromString(notificationRequestDto.getChannel());
        if(notificationChannelType==null){
            throw new BadRequestException("Invalid Notification Channel Type");
        }

        Notification notification = Notification.builder()
                .readStatus(NotificationReadStatus.UNREAD)
                .userId(UUID.fromString(userId))
                .deliveryStatus(NotificationDeliveryStatus.PENDING)
                .retryCount(0)
                .messageBody(notificationRequestDto.getData())
                .imageURL(notificationRequestDto.getImage_url())
                .channelType(notificationChannelType)
                .type(NotificationType.fromString(notificationRequestDto.getType()))
                .subject(notificationRequestDto.getSubject())
                .build();
        Notification savedNotification = notificationRepository.save(notification);

        if(savedNotification==null){
            throw new BadRequestException("Unable to save notification");
        }
        UserDetailsDto userDetailsDto = thirdPartyUserService.getUserDetails(userId);
        if(userDetailsDto==null){
            throw new NotFoundException("No user found for the given user id: "+userId);
        }
        if(notificationChannelType==NotificationChannelType.EMAIL){
            rabbitMQProducer.sendNotificationEvent(exchangeName,emailRoutingKey,userDetailsDto, savedNotification.getId());
        }
        if(notificationChannelType==NotificationChannelType.SMS){
            rabbitMQProducer.sendNotificationEvent(exchangeName,smsRoutingKey,userDetailsDto, savedNotification.getId());
        }
        SendNotificationResponseDto sendNotificationResponseDto = new SendNotificationResponseDto();
        sendNotificationResponseDto.setService_id(notificationRequestDto.getService_id());
        sendNotificationResponseDto.setMessage("Notification Sent Successfully");
        return sendNotificationResponseDto;
    }

    @Override
    public List<NotificationResponseDto> getAllNotifications(String userId) throws NotFoundException {
        List<Notification> notificationList = notificationRepository.findAllByUserId(UUID.fromString(userId));
        if(notificationList.size()==0){
            throw new NotFoundException("No notifications found for the given user id: "+userId);
        }
        List<NotificationResponseDto> notificationResponseDtoList = new ArrayList<>();
        for(Notification notification: notificationList){
            notificationResponseDtoList.add(convertNotificationToNotificationResponseDto(notification));
        }
        return notificationResponseDtoList;
    }

    @Override
    public NotificationResponseDto getNotification(String userId, String notificationId) throws NotFoundException, ForbiddenRequestException {
        Optional<Notification> notificationOptional = notificationRepository.findById(UUID.fromString(notificationId));
        if(notificationOptional.isEmpty()){
            throw new NotFoundException("No notification found for the given notification id: "+notificationId);
        }
        Notification notification = notificationOptional.get();
        if(!notification.getUserId().equals(UUID.fromString(userId))){
            throw new ForbiddenRequestException("Invalid Request: You are not authorized to view this notification");
        }
        NotificationResponseDto notificationResponseDto = convertNotificationToNotificationResponseDto(notification);
        return notificationResponseDto;
    }

    @Override
    public void deleteNotification(String userId, String notificationId) throws NotFoundException, ForbiddenRequestException {
        Optional<Notification> notificationOptional = notificationRepository.findById(UUID.fromString(notificationId));
        if(notificationOptional.isEmpty()){
            throw new NotFoundException("No notification found for the given notification id: "+notificationId);
        }
        Notification notification = notificationOptional.get();
        if(!notification.getUserId().equals(UUID.fromString(userId))){
            throw new ForbiddenRequestException("Invalid Request: You are not authorized to delete this notification");
        }
        notificationRepository.deleteById(UUID.fromString(notificationId));
    }

    @Override
    public void markNotificationAsRead(String userId, String notificationId) throws NotFoundException, ForbiddenRequestException {
        Optional<Notification> notificationOptional = notificationRepository.findById(UUID.fromString(notificationId));
        if(notificationOptional.isEmpty()){
            throw new NotFoundException("No notification found for the given notification id: "+notificationId);
        }
        Notification notification = notificationOptional.get();
        if(!notification.getUserId().equals(UUID.fromString(userId))){
            throw new ForbiddenRequestException("Invalid Request: You are not authorized to update this notification");
        }
        notification.setReadStatus(NotificationReadStatus.READ);
        notificationRepository.save(notification);
    }
}
