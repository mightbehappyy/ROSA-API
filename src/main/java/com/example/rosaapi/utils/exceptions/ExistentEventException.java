package com.example.rosaapi.utils.exceptions;

public class ExistentEventException extends RuntimeException {

    public ExistentEventException(String message) {
        super("Error: " + message);
    }
}
