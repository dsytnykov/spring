package com.dsytnykov.rabbitmq_listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class MessageListener {

    @RabbitListener(queues = RabbitMqConfig.QUEUE_CONSUMER)
    public void consumer(CustomMessage message) {
        System.out.println(message);
    }
}
