package com.example.rosaapi.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CalendarWeekEventsDTO {
    private String weekRange;
    private List<CalendarEventDTO> weekEvents;

}
