package com.example.rosaapi.controllers;


import com.example.rosaapi.controllers.responses.GoogleCalendarResponse;
import com.example.rosaapi.service.GoogleCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("api/reserva-lab")
@RequiredArgsConstructor
public class GoogleCalendarController {


    @GetMapping("/eventos")
    public ResponseEntity<GoogleCalendarResponse> getLastTenEvents() throws GeneralSecurityException, IOException {
        GoogleCalendarService googleCalendarService = new GoogleCalendarService();
        return ResponseEntity.ok(new GoogleCalendarResponse(googleCalendarService.getTenEvents()));

    }
}
