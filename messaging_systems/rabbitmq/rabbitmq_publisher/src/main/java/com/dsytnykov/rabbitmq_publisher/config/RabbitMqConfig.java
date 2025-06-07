package com.dsytnykov.rabbitmq_publisher.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String QUEUE_CONSUMER = "message_queue_1";
    public static final String EXCHANGE = "message_exchange";
    public static final String QUEUE_LISTENER = "message_queue_2";

    @Bean
    public Queue queueConsumer() {
        return new Queue(QUEUE_CONSUMER);
    }

    @Bean
    public Queue queueListener() {
        return new Queue(QUEUE_LISTENER);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingConsumer(@Qualifier("queueConsumer") Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(queueConsumer().getName());
    }

    @Bean
    public Binding bindingListener(@Qualifier("queueListener") Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(queueListener().getName());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
