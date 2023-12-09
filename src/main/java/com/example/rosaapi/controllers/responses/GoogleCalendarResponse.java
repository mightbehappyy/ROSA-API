package com.example.rosaapi.controllers.responses;


import com.example.rosaapi.model.dtos.CalendarEventDTO;
import com.example.rosaapi.model.dtos.CalendarWeekEventsDTO;
import com.google.api.services.calendar.model.Event;
import lombok.Getter;

import java.util.List;

@Getter
public class GoogleCalendarResponse {


    private final List<CalendarEventDTO> weekEvents;

    public GoogleCalendarResponse(CalendarWeekEventsDTO calendarWeekEvents) {
        this.weekEvents = calendarWeekEvents.getWeekEvents();
    }
}
