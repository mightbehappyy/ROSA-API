package com.example.rosaapi.controllers.responses;

public record ErrorResponse(int statusCode, String message) {
}