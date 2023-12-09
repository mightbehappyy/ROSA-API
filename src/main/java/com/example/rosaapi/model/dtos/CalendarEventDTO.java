package com.example.rosaapi.model.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class CalendarEventDTO {
    private final String summary;
    private final String start;
    private final String end;
    private final String date;
    private final String dayOfWeek;


}
