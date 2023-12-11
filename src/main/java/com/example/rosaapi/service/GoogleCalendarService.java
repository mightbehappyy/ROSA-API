package com.example.rosaapi.service;

import com.example.rosaapi.model.dtos.CalendarEventDTO;
import com.example.rosaapi.model.dtos.CalendarWeekEventsDTO;
import com.example.rosaapi.utils.DateTimeUtils;
import com.example.rosaapi.utils.exceptions.DateTimeInvalidException;
import com.example.rosaapi.utils.exceptions.EventInvalidException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;


import java.io.IOException;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static com.example.rosaapi.utils.DateTimeUtils.getStartOfWeek;

@RequiredArgsConstructor
public class GoogleCalendarService {

    private final Calendar service;

    public CalendarWeekEventsDTO getWeekEvents() {

        LocalDate startOfWeek = getStartOfWeek();
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

        DateTime startTime = new DateTime(startOfWeek.atStartOfDay().toInstant(ZoneOffset.ofHours(-3))
                .toEpochMilli());
        DateTime endTime = new DateTime(endOfWeek.atStartOfDay().toInstant(ZoneOffset.ofHours(-3))
                .toEpochMilli());

        Events events = fetchEvents(service, startTime, endTime);

        return setCalendarWeekEventsDTO(events);
    }

    private CalendarWeekEventsDTO setCalendarWeekEventsDTO(Events events) {
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

    private Events fetchEvents(Calendar service, DateTime startTime, DateTime endTime){
        try {
            return service.events().list("primary")
                    .setTimeMin(startTime)
                    .setTimeMax(endTime)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        } catch(IOException e) {
            throw new EventInvalidException("An error occurred when fetching events: " + e.getMessage());
        }
    }

    public CalendarEventDTO postEvents(CalendarEventDTO calendarEventDTO) {
        try {
            Event event = new Event().setSummary(calendarEventDTO.getSummary());

            DateTime startDateTime = new DateTime(calendarEventDTO.getStart());
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("America/Recife");

            event.setStart(start);

            DateTime endDateTime = new DateTime(calendarEventDTO.getEnd());
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("America/Recife");

            event.setEnd(end);

            service.events().insert("primary", event).execute();
            return new CalendarEventDTO(calendarEventDTO.getSummary(), calendarEventDTO.getStart(), calendarEventDTO.getEnd());
        } catch (NumberFormatException e) {
            throw new DateTimeInvalidException("Invalid date/time format: " + e.getMessage());
        } catch (IOException e) {
            throw new EventInvalidException("An error occurred when posting an event: " + e.getMessage());
        }
    }
}