package com.localservicesreview.notificationservice.producer;

import com.localservicesreview.notificationservice.dtos.NotificationProducerDto;
import com.localservicesreview.notificationservice.dtos.UserDetailsDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitMQNotificationProducer implements NotificationProducer{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public String sendNotificationEvent(String exchangeName, String routingKey, UserDetailsDto userDetailsDto, UUID notificationId) {
        NotificationProducerDto notificationProducerDto = new NotificationProducerDto(userDetailsDto, notificationId);
        rabbitTemplate.convertAndSend(exchangeName,routingKey,notificationProducerDto);
        System.out.println("Message published to "+exchangeName+" with routing key "+routingKey);
        return "Message Published!";
    }
}
