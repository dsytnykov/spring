package com.dsytnykov.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> errors = new HashMap<>();

    public ValidationErrorResponse(int status, String message, LocalDateTime timestamp) {
        super(status, message, timestamp);
    }
}
