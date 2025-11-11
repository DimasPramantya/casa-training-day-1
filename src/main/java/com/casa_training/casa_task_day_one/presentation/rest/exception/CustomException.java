package com.casa_training.casa_task_day_one.presentation.rest.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {
    private final String exceptionMessage;
    private final int statusCode;
    private final Object data;

    public CustomException(String exceptionMessage, int statusCode, Object data) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
        this.statusCode = statusCode;
        this.data = data;
    }

    public CustomException(String exceptionMessage, int statusCode) {
        this(exceptionMessage, statusCode, null);
    }
}