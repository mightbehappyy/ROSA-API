package com.example.rosaapi.controllers.responses;


import com.example.rosaapi.model.dtos.CalendarEventDTO;
import lombok.Getter;

@Getter
public class CalendarEventResponse {
    private final String summary;
    private final String start;
    private final String end;

    public CalendarEventResponse(CalendarEventDTO calendarEventDTO) {
        this.summary = calendarEventDTO.getSummary();
        this.start = calendarEventDTO.getStart();
        this.end = calendarEventDTO.getEnd();
    }
}
