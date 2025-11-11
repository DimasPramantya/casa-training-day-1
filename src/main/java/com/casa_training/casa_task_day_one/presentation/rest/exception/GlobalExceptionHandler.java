package com.casa_training.casa_task_day_one.presentation.rest.exception;

import com.casa_training.casa_task_day_one.presentation.rest.dto.res.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }

        BaseResponse<Object> body = new BaseResponse<>();
        body.setData(errors);
        body.setStatus("F");
        body.setMessage("Validation Failed");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(
            CustomException exception
    ) {
        BaseResponse<Object> body = new BaseResponse<>();
        body.setMessage(exception.getMessage());
        body.setStatus("F");
        body.setError(exception.getErrors());

        return new ResponseEntity<>(body, HttpStatusCode.valueOf(exception.getStatusCode()));
    }
}
