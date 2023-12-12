package com.example.rosaapi.utils.exceptions;

public class InvalidCityException extends RuntimeException {

    public InvalidCityException(String message) {
        super("Error: " + message);
    }
}
