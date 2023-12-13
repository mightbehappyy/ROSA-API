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

@RestController
@RequestMapping("api/reserva-lab")
@RequiredArgsConstructor
public class GoogleCalendarController {


    private final GoogleCalendarService googleCalendarService =
            new GoogleCalendarService(GoogleAuthService.getCalendarService());

    @RateLimiter(name="calendarAPILimiter", fallbackMethod = "calendarAPIFallbackMethod")
    @GetMapping("/eventos-da-semana")
    public ResponseEntity<?> getWeekEvents(){
        return ResponseEntity.ok(new CalendarWeekEventsResponse(googleCalendarService.getWeekEvents()));
    }

    @RateLimiter(name="calendarAPILimiter", fallbackMethod = "calendarAPIFallbackMethod")
    @PostMapping("/criar-evento")
    public ResponseEntity<?> postEvent(@RequestBody CalendarEventDTO calendarEventDTO)  {
            return ResponseEntity.ok(new CalendarEventResponse(googleCalendarService.postEvents(calendarEventDTO)));
        }

    private ResponseEntity<?> calendarAPIFallbackMethod(Throwable ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests were made");
    }
}