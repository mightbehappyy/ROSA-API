package com.example.rosaapi.controllers;


import com.example.rosaapi.controllers.responses.CalendarEventResponse;
import com.example.rosaapi.controllers.responses.GoogleCalendarResponse;
import com.example.rosaapi.model.dtos.CalendarEventDTO;
import com.example.rosaapi.service.GoogleAuthService;
import com.example.rosaapi.service.GoogleCalendarService;
import com.example.rosaapi.utils.exceptions.EventInvalidException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reserva-lab")
@RequiredArgsConstructor
public class GoogleCalendarController {

    private final GoogleCalendarService googleCalendarService =
            new GoogleCalendarService(GoogleAuthService.getCalendarService());
    @GetMapping("/eventos-da-semana")
    public ResponseEntity<GoogleCalendarResponse> getWeekEvents(){
        return ResponseEntity.ok(new GoogleCalendarResponse(googleCalendarService.getWeekEvents()));
    }

    @PostMapping("/criar-evento")
    public ResponseEntity<CalendarEventResponse> postEvent(@RequestBody CalendarEventDTO calendarEventDTO)  {
            return ResponseEntity.ok(new CalendarEventResponse(googleCalendarService.postEvents(calendarEventDTO)));
        }
    }

