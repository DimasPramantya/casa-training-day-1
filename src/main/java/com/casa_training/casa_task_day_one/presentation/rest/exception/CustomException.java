package com.casa_training.casa_task_day_one.presentation.rest.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {
    private final String message;
    private final int statusCode;
    private final Object errors;

    public CustomException(String message, int statusCode, Object errors) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
        this.errors = errors;
    }

    public CustomException(String message, int statusCode) {
        this(message, statusCode, null);
    }
}