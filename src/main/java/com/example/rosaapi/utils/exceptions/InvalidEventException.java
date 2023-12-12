package com.example.rosaapi.utils.exceptions;

public class InvalidEventException extends RuntimeException{

    public InvalidEventException(String message) {
        super("Error:" + message);

    }
}
