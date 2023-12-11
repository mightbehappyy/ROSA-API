package com.example.rosaapi.controllers.responses;

import lombok.Getter;

@Getter
public record ErrorResponse(int statusCode, String message) {
}