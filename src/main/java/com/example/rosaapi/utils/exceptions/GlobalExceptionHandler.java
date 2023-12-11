package com.example.rosaapi.utils.exceptions;


import com.example.rosaapi.controllers.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CityInvalidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCityException(CityInvalidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(EventInvalidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEventException(EventInvalidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        String customErrorMessage = "The requested city name resource was not found";
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),customErrorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DateTimeInvalidException.class)
    public ResponseEntity<ErrorResponse> handleHandleIllegalDateTimeFormatException(DateTimeInvalidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CalendarServiceException.class)
    public ResponseEntity<ErrorResponse> handleCalendarServiceErrorException(CalendarServiceException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(CalendarCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleCalendarCredentialsException(CalendarCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


}