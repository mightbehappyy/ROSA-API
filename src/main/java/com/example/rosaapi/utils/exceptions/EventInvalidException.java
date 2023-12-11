package com.example.rosaapi.utils.exceptions;

public class EventInvalidException extends RuntimeException{

    public EventInvalidException(String message) {
        super("Error:" + message);

    }
}
