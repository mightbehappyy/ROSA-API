package com.example.rosaapi.controllers;


import com.example.rosaapi.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final TokenService tokenService;

    public AuthenticationController(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @GetMapping("/token")
    public String token(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }
}
