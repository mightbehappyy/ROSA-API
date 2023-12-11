package com.example.rosaapi.utils.exceptions;

public class CalendarServiceException extends RuntimeException{

    public CalendarServiceException(String message) {
        super("Error:" + message);
    }
}
