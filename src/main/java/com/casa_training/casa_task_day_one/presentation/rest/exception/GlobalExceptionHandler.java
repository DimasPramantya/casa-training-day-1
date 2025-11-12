package com.casa_training.casa_task_day_one.presentation.rest.exception;

import com.casa_training.casa_task_day_one.presentation.rest.dto.res.BaseResponse;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
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
    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<BaseResponse<Object>> handleRateLimitException(
            RequestNotPermitted exception
    ) {
        BaseResponse<Object> body = new BaseResponse<>();
        body.setStatus("F");
        body.setMessage("Too many requests, please try again later.");
        body.setError("Too many requests");
        return new ResponseEntity<>(body, HttpStatus.TOO_MANY_REQUESTS);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<BaseResponse<Object>> handleExpiredJwtException(
            ExpiredJwtException exception
    ) {
        BaseResponse<Object> body = new BaseResponse<>();
        body.setStatus("F");
        body.setMessage("Token has expired. Please log in again.");
        body.setError("JWT_EXPIRED");

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<BaseResponse<Object>> handleSignatureException(
            SignatureException exception
    ) {
        BaseResponse<Object> body = new BaseResponse<>();
        body.setStatus("F");
        body.setMessage("Invalid JWT signature.");
        body.setError("JWT_INVALID_SIGNATURE");

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<BaseResponse<Object>> handleMalformedJwtException(
            MalformedJwtException exception
    ) {
        BaseResponse<Object> body = new BaseResponse<>();
        body.setStatus("F");
        body.setMessage("Invalid JWT token structure.");
        body.setError("JWT_MALFORMED");
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(
        Exception exception
    ){
        exception.printStackTrace();
        BaseResponse<Object> body = new BaseResponse<>();
        body.setStatus("F");
        body.setMessage("Internal Server Error");
        body.setError("Internal Server Error");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
