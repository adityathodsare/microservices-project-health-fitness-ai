package com.microservices_project_fitness.healthcare_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.healthcare.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.healthcare.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.healthcare.queue.name}")
    private String queueName;

    @Bean
    public Queue healthcareQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public DirectExchange healthcareExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding healthcareBinding() {
        return BindingBuilder.bind(healthcareQueue()).to(healthcareExchange()).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}