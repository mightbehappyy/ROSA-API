package com.example.rosaapi.utils.exceptions;

public class CalendarCredentialsException extends  RuntimeException{

    public CalendarCredentialsException(String message) {
        super("Error:" + message);
    }
}
