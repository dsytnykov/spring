package com.dsytnykov.rabbitmq_publisher.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomMessage {
    private String id;
    private String message;
    private LocalDate date;
}
