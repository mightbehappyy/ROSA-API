package com.example.rosaapi.controllers.responses;


import com.example.rosaapi.model.dtos.CalendarEventDTO;
import com.example.rosaapi.model.dtos.CalendarWeekEventsDTO;

import lombok.Getter;

import java.util.List;

@Getter
public class CalendarWeekEventsResponse {

    private final String weekRange;
    private final List<CalendarEventDTO> weekEvents;
    
    public CalendarWeekEventsResponse(CalendarWeekEventsDTO calendarWeekEvents) {
        this.weekEvents = calendarWeekEvents.getWeekEvents();
        this.weekRange = calendarWeekEvents.getWeekRange();
    }
}
