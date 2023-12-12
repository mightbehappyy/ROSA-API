package com.example.rosaapi.utils.exceptions;

public class InvalidDateTimeException extends RuntimeException{

    public InvalidDateTimeException(String message) {
        super("Error:" + message);

    }
}
