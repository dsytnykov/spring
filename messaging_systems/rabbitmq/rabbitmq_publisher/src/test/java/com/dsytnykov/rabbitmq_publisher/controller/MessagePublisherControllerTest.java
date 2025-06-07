package com.dsytnykov.rabbitmq_publisher.controller;

import com.dsytnykov.rabbitmq_publisher.message.CustomMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest
class MessagePublisherControllerTest {


    private final MessagePublisherController controller;

    @Autowired
    public MessagePublisherControllerTest(MessagePublisherController controller) {
        this.controller = controller;
    }

    @Container
    static RabbitMQContainer container = new RabbitMQContainer(
            DockerImageName.parse("rabbitmq").withTag("management"));

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.port", container::getFirstMappedPort);
    }

    @Test
    public void shouldReturnStringMessageWithConsumer() {
        CustomMessage message = new CustomMessage();
        message.setMessage("something");

        String result = controller.publishMessage("consumer", message);

        assertEquals("Message published", result);
    }

    @Test
    public void shouldReturnStringMessageWithListener() {
        CustomMessage message = new CustomMessage();
        message.setMessage("something");

        String result = controller.publishMessage("listener", message);

        assertEquals("Message published", result);
    }

    @Test
    public void shouldThrowException() {
        CustomMessage message = new CustomMessage();
        message.setMessage("something");

        assertThrows(RuntimeException.class, () -> controller.publishMessage("exception", message));
    }

}
