package com.example.rosaapi.model.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class CalendarEventDTO {
    private final String summary;
    private final String start;
    private final String end;
    private String date;
    private String dayOfWeek;

    public CalendarEventDTO(String summary, String start, String end, String date, String dayOfWeek) {
        this.summary = summary;
        this.start = start;
        this.end = end;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
    }
    public CalendarEventDTO(String summary, String start, String end) {
        this.summary = summary;
        this.start = start;
        this.end = end;
    }


}
