package com.example.rosaapi.utils.exceptions;

public class DateTimeInvalidException extends RuntimeException{

    public DateTimeInvalidException(String message) {
        super("Error:" + message);

    }
}
