package com.example.rosaapi.service;

import com.example.rosaapi.model.dtos.CalendarEventDTO;
import com.example.rosaapi.model.dtos.CalendarWeekEventsDTO;
import com.example.rosaapi.utils.DateTimeUtils;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;


import java.io.IOException;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GoogleCalendarService {

    private final Calendar service;

    public CalendarWeekEventsDTO getWeekEvents() throws IOException {

        LocalDate startOfWeek = getStartOfWeek();
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

        DateTime startTime = new DateTime(startOfWeek.atStartOfDay().toInstant(ZoneOffset.ofHours(-3))
                .toEpochMilli());
        DateTime endTime = new DateTime(endOfWeek.atStartOfDay().toInstant(ZoneOffset.ofHours(-3))
                .toEpochMilli());

        Events events = fetchEvents(service, startTime, endTime);

        return getCalendarWeekEventsDTO(events);
    }

    private static CalendarWeekEventsDTO getCalendarWeekEventsDTO(Events events) {
        List<Event> items = events.getItems();
        CalendarWeekEventsDTO calendarWeekEvents = new CalendarWeekEventsDTO();
        List<CalendarEventDTO> calendarEventDTOS = new ArrayList<>();

        for (Event event : items) {
            long start = event.getStart().getDateTime().getValue();
            long end = event.getEnd().getDateTime().getValue();
            String summary = event.getSummary();
            CalendarEventDTO calendarEventDTO = DateTimeUtils.convertUnixToDTO(start, end, summary);
            calendarEventDTOS.add(calendarEventDTO);
        }

        calendarWeekEvents.setWeekEvents(calendarEventDTOS);
        return calendarWeekEvents;
    }

    private static Events fetchEvents(Calendar service, DateTime startTime, DateTime endTime) throws IOException {
        return service.events().list("primary")
                .setTimeMin(startTime)
                .setTimeMax(endTime)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
    }

    private static LocalDate getStartOfWeek() {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        if (currentDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentDateTime.getDayOfWeek() == DayOfWeek.SUNDAY)
        {
            return currentDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).toLocalDate();
        } else {
            return currentDateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate();
        }
    }
}
