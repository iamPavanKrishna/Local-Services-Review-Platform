package com.localservicesreview.notificationservice.producer;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPublisherNotificationConfig {

    @Value("${rabbitmq.email.queue}")
    private String emailQueueName;
    @Value("${rabbitmq.sms.queue}")
    private String smsQueueName;
    @Value("${rabbitmq.email.routingkey}")
    private String emailRoutingKey;
    @Value("${rabbitmq.sms.routingkey}")
    private String smsRoutingKey;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueueName, true);
    }
    @Bean
    public Queue smsQueue() {
        return new Queue(smsQueueName,true);
    }
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }
    @Bean
    public Binding bindingEmail(Queue emailQueue, TopicExchange exchange) {
        return BindingBuilder.bind(emailQueue).to(exchange).with(emailRoutingKey);
    }
    @Bean
    public Binding bindingSms(Queue smsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(smsQueue).to(exchange).with(smsRoutingKey);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
