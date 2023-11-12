package com.localservicesreview.notificationservice.consumer;

import com.localservicesreview.notificationservice.dtos.NotificationProducerDto;
import com.localservicesreview.notificationservice.dtos.UserDetailsDto;
import com.localservicesreview.notificationservice.models.Notification;
import com.localservicesreview.notificationservice.models.NotificationDeliveryStatus;
import com.localservicesreview.notificationservice.repositories.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RabbitMQNotificationConsumer {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private EmailService emailService;
    @RabbitListener(queues = "${rabbitmq.email.queue}")
    public void emailQueueConsumer(NotificationProducerDto notificationProducerDto){
        try{

            System.out.println("Message received from email queue");

            UserDetailsDto userDetailsDto = notificationProducerDto.getUserDetailsDto();
            UUID notificationId = notificationProducerDto.getNotificationId();

            Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
            if(!notificationOptional.isEmpty()){
                Notification notification = notificationOptional.get();

                Boolean mailSent = emailService.sendEmail(userDetailsDto.getEmail(),notification.getSubject(),notification.getMessageBody(),notification.getImageURL());
                if(!mailSent){
                    notification.setDeliveryStatus(NotificationDeliveryStatus.FAILED);
                }else{
                    notification.setDeliveryStatus(NotificationDeliveryStatus.DELIVERED);
                }
                notificationRepository.save(notification);
                System.out.println("Message consumed by the Consumer");
            }
        }catch (MailSendException e){
            System.out.println("Exception occurred while consuming the message from email queue: "+e.getMessage());
        }
    }
}
