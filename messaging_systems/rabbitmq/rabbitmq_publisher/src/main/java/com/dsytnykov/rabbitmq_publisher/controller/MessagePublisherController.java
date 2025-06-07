package com.dsytnykov.rabbitmq_publisher.controller;

import com.dsytnykov.rabbitmq_publisher.config.RabbitMqConfig;
import com.dsytnykov.rabbitmq_publisher.message.CustomMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessagePublisherController {

    private final RabbitTemplate template;

    @PostMapping(value = "/publish/{consumer}")
    public String publishMessage(@PathVariable String consumer, @RequestBody CustomMessage message) {
        message.setId(UUID.randomUUID().toString());
        message.setDate(LocalDate.now());
        if("consumer".equals(consumer)) {
            template.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.QUEUE_CONSUMER, message);
        } else if("listener".equals(consumer)){
            template.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.QUEUE_LISTENER, message);
        } else {
            throw new RuntimeException("Invalid consumer path variable. Please use: consumer or listener");
        }

        return "Message published";
    }
}
