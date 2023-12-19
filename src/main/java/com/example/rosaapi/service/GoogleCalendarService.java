package com.example.rosaapi.service;

import com.example.rosaapi.model.dtos.CalendarEventDTO;
import com.example.rosaapi.model.dtos.CalendarWeekEventsDTO;
import com.example.rosaapi.utils.DateTimeUtils;
import com.example.rosaapi.utils.exceptions.ExistentEventException;
import com.example.rosaapi.utils.exceptions.InvalidDateTimeException;
import com.example.rosaapi.utils.exceptions.InvalidEventException;
import com.example.rosaapi.utils.functions.FormatDateString;
import com.example.rosaapi.utils.functions.LabIdFunction;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static com.example.rosaapi.utils.DateTimeUtils.getStartOfWeek;

@RequiredArgsConstructor
public class GoogleCalendarService {

    private final Calendar service;

    public CalendarWeekEventsDTO getWeekEvents(int lab) throws IOException {
        LocalDate startOfWeek = getStartOfWeek();
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

        DateTime startTime = new DateTime(startOfWeek.atStartOfDay().toInstant(ZoneOffset.ofHours(-3))
                .toEpochMilli());
        DateTime endTime = new DateTime(endOfWeek.atStartOfDay().toInstant(ZoneOffset.ofHours(-3))
                .toEpochMilli());

        Events events = fetchEvents(startTime, endTime, lab);

        return CalendarDTOMapper.getWeekEventsInDTO(events);
    }

    public CalendarEventDTO postEvents(CalendarEventDTO calendarEventDTO) {
        try {
            Event event = new Event().setSummary(calendarEventDTO.getSummary());

            DateTime startDateTime = new DateTime(
                    calendarEventDTO.getDate() + "T" + calendarEventDTO.getStart() + ":00" +"-03:00");
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("America/Recife");

            DateTime endDateTime = new DateTime(
                    calendarEventDTO.getDate() + "T" + calendarEventDTO.getEnd() + ":00" +"-03:00");
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

    public CalendarWeekEventsDTO fetchAllDayEvents(String startTime, int lab) throws IOException, ParseException {
        String date = FormatDateString.getFormattedString(startTime);

        DateTime start = new DateTime(date + "T" + "00:00:00" + "-03:00");
        DateTime end = new DateTime(date + "T" + "23:59:59" + "-03:00");
        Events events = service.events().list(LabIdFunction.getLabId(lab))
                .setTimeMin(start)
                .setTimeMax(end)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        return CalendarDTOMapper.getWeekEventsInDTO(events);

    }


    private boolean checkOverlappingEvents(DateTime startDateTime, DateTime endDateTime) throws IOException {
        List<Event> items = fetchEvents(startDateTime, endDateTime, 1).getItems();
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

    private Events fetchEvents(DateTime startTime, DateTime endTime, int lab) throws IOException {
        return service.events().list(LabIdFunction.getLabId(lab))
                .setTimeMin(startTime)
                .setTimeMax(endTime)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

    }
}