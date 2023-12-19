package com.example.rosaapi.controllers;


import com.example.rosaapi.controllers.responses.CalendarEventResponse;
import com.example.rosaapi.controllers.responses.CalendarWeekEventsResponse;
import com.example.rosaapi.model.dtos.CalendarEventDTO;
import com.example.rosaapi.model.dtos.CalendarWeekEventsDTO;
import com.example.rosaapi.service.GoogleAuthService;
import com.example.rosaapi.service.GoogleCalendarService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("api/reserva-lab")
@RequiredArgsConstructor
public class GoogleCalendarController {


    private final GoogleCalendarService googleCalendarService =
            new GoogleCalendarService(GoogleAuthService.getCalendarService());

    @GetMapping("/eventos-da-semana/{lab}")
    public ResponseEntity<?> getWeekEvents(@PathVariable("lab") int lab) throws IOException {
        return ResponseEntity.ok(new CalendarWeekEventsResponse(googleCalendarService.getWeekEvents(lab)));
    }

    @GetMapping("/eventos-do-dia/{lab}")
    public ResponseEntity<?> getDayEvents(@PathVariable("lab") int lab, @RequestBody CalendarEventDTO calendarEventDTO)
            throws IOException, ParseException {
        return ResponseEntity.ok(new CalendarWeekEventsResponse(googleCalendarService.fetchAllDayEvents(
                calendarEventDTO.getDate(), lab)));
    }

    @PostMapping("/criar-evento")
    public ResponseEntity<?> postEvent(@RequestBody CalendarEventDTO calendarEventDTO)  {
            return ResponseEntity.ok(new CalendarEventResponse(googleCalendarService.postEvents(calendarEventDTO)));
        }

    private ResponseEntity<?> calendarAPIFallbackMethod(Throwable ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests were made");
    }
}