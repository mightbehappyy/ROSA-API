package com.example.rosaapi.utils.exceptions;


import com.example.rosaapi.controllers.responses.ErrorResponse;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCityException.class)
    public ResponseEntity<Object> handleInvalidCityException(InvalidCityException ex) {
        return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(InvalidEventException.class)
    public ResponseEntity<Object> handleInvalidEventException(InvalidEventException ex) {
        return buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex) {
        return buildResponseEntity(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(InvalidDateTimeException.class)
    public ResponseEntity<Object> handleHandleIllegalDateTimeFormatException(InvalidDateTimeException ex) {
        return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(CalendarServiceException.class)
    public ResponseEntity<Object> handleCalendarServiceErrorException(CalendarServiceException ex) {
        return buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(CalendarCredentialsException.class)
    public ResponseEntity<Object> handleCalendarCredentialsException(CalendarCredentialsException ex) {
        return buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
    @ExceptionHandler(ExistentEventException.class)
    public ResponseEntity<Object> handleExistentEventException(ExistentEventException ex) {
        return buildResponseEntity(new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Object> handleExistentEventException(RequestNotPermitted ex) {
        return buildResponseEntity(new ErrorResponse(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}