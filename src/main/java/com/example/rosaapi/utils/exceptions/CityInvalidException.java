package com.example.rosaapi.utils.exceptions;

public class CityInvalidException extends RuntimeException {

    public CityInvalidException(String message) {
        super("Error:" + message);
    }
}
