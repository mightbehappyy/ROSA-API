package com.example.rosaapi.service;

import com.example.rosaapi.model.dtos.CalendarEventDTO;
import com.example.rosaapi.model.dtos.CalendarWeekEventsDTO;
import com.example.rosaapi.utils.DateTimeUtils;
import com.example.rosaapi.utils.exceptions.ExistentEventException;
import com.example.rosaapi.utils.exceptions.InvalidDateTimeException;
import com.example.rosaapi.utils.exceptions.InvalidEventException;
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

        Events events = fetchEvents(startTime, endTime);

        return setCalendarWeekEventsDTO(events);
    }

    private Events fetchEvents(DateTime startTime, DateTime endTime){
        try {
            return service.events().list("primary")
                    .setTimeMin(startTime)
                    .setTimeMax(endTime)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        } catch(IOException e) {
            throw new InvalidEventException("An error occurred when fetching events: " + e.getMessage());
        }
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

        calendarWeekEvents.setWeekRange(DateTimeUtils.getWeekRange(items));
        calendarWeekEvents.setWeekEvents(calendarEventDTOS);
        return calendarWeekEvents;
    }

    public CalendarEventDTO postEvents(CalendarEventDTO calendarEventDTO) {
        try {
            Event event = new Event().setSummary(calendarEventDTO.getSummary());

            DateTime startDateTime = new DateTime(calendarEventDTO.getStart()+"-03:00");
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("America/Recife");

            DateTime endDateTime = new DateTime(calendarEventDTO.getEnd()+"-03:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("America/Recife");
            if(checkOverlappingEvents(startDateTime, endDateTime)) {
                throw new ExistentEventException("An existing event was found");
            } else {
                event.setStart(start);
                event.setEnd(end);

                service.events().insert("primary", event).execute();

                return DateTimeUtils.convertUnixToDTO(
                        event.getStart().getDateTime().getValue(),
                        event.getEnd().getDateTime().getValue(),
                        event.getSummary()
                );
            }

        } catch (NumberFormatException e) {
            throw new InvalidDateTimeException(e.getMessage());
        } catch (IOException e) {
            throw new InvalidEventException("An error occurred when posting an event: " + e.getMessage());
        }
    }

    private boolean checkOverlappingEvents(DateTime startDateTime, DateTime endDateTime) {
        List<Event> items = fetchEvents(startDateTime, endDateTime).getItems();
        long end = endDateTime.getValue();

        if (!items.isEmpty()) {
            for (Event event : items) {
                long start = event.getStart().getDateTime().getValue();

                if (start < end) {
                    return true;
                }
            }
        }
        return false;
    }
}