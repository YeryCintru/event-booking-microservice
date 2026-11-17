package com.eventbooking.booking_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "booking.exchange";
    public static final String QUEUE_NAME = "booking.created.queue";
    public static final String ROUTING_KEY = "booking.created.key";

    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue bookingCreatedQueue() {
        return new Queue(QUEUE_NAME, true); //true 
    }

    @Bean
    public Binding binding(Queue bookingQueue, TopicExchange bookingExchange) {
        return BindingBuilder.bind(bookingQueue).to(bookingExchange).with(ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new JacksonJsonMessageConverter());
        return template;
    }
}
