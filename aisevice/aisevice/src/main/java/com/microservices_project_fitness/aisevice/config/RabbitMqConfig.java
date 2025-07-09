//package com.microservices_project_fitness.aisevice.config;
//
//
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMqConfig {
//
//    @Value("${rabbitmq.exchange.name}")
//    private String exchange;
//
//    @Value("${rabbitmq.routing.key}")
//    private String routingKey;
//
//    @Value("${rabbitmq.queue.name}")
//    private String queueName;
//
//    @Bean
//    public Queue activityQueue() {
//        return new Queue(queueName, true);
//    }
//
//    @Bean
//    public DirectExchange activityExchange() {
//        return new DirectExchange(exchange);
//    }
//
//    @Bean
//    public Binding activityBinding(Queue activityQueue, DirectExchange activityExchange) {
//        return BindingBuilder.bind(activityQueue()).to(activityExchange()).with(routingKey);
//    }
//
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//}

package com.microservices_project_fitness.aisevice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    // Fitness configuration
    @Value("${rabbitmq.fitness.exchange.name}")
    private String fitnessExchangeName;

    @Value("${rabbitmq.fitness.queue.name}")
    private String fitnessQueueName;

    @Value("${rabbitmq.fitness.routing.key}")
    private String fitnessRoutingKey;

    // Healthcare configuration
    @Value("${rabbitmq.healthcare.exchange.name}")
    private String healthcareExchangeName;

    @Value("${rabbitmq.healthcare.queue.name}")
    private String healthcareQueueName;

    @Value("${rabbitmq.healthcare.routing.key}")
    private String healthcareRoutingKey;

    // Fitness Exchange and Queue
    @Bean
    public DirectExchange fitnessExchange() {
        return new DirectExchange(fitnessExchangeName);
    }

    @Bean
    public Queue fitnessQueue() {
        return new Queue(fitnessQueueName, true);
    }

    @Bean
    public Binding fitnessBinding() {
        return BindingBuilder.bind(fitnessQueue())
                .to(fitnessExchange())
                .with(fitnessRoutingKey);
    }

    // Healthcare Exchange and Queue
    @Bean
    public DirectExchange healthcareExchange() {
        return new DirectExchange(healthcareExchangeName);
    }

    @Bean
    public Queue healthcareQueue() {
        return new Queue(healthcareQueueName, true);
    }

    @Bean
    public Binding healthcareBinding() {
        return BindingBuilder.bind(healthcareQueue())
                .to(healthcareExchange())
                .with(healthcareRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}