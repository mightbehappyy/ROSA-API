package com.example.rosaapi.utils;

import com.example.rosaapi.model.dtos.CalendarEventDTO;
import com.google.api.services.calendar.model.Event;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

public final class DateTimeUtils {

        private DateTimeUtils() {

        }
    public static CalendarEventDTO convertUnixToDTO(long start, long end, String summary) {
            Date convertedStart = new Date(start);
            Date convertedEnd = new Date(end);

            SimpleDateFormat timeConverter = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dateConverter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat dayOfWeekConverter = new SimpleDateFormat("EEE");


            String formattedDay = dayOfWeekConverter.format(convertedStart);
            String formattedDate = dateConverter.format(convertedStart);
            String formattedStart = timeConverter.format(convertedStart);
            String formattedEnd = timeConverter.format(convertedEnd);

            return new CalendarEventDTO(
                    summary,
                    formattedStart,
                    formattedEnd,
                    formattedDate,
                    formattedDay);

    }
        public static LocalDate getStartOfWeek() {
                ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("UTC-3"));
                if (currentDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentDateTime.getDayOfWeek() == DayOfWeek.SUNDAY)
                {
                        return currentDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).toLocalDate();
                } else {
                        return currentDateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate();
                }
        }

        public static String getWeekRange(List<Event> items) {
                long firstStart = items.get(0).getStart().getDateTime().getValue();
                long lastEnd = items.get(items.size() - 1).getEnd().getDateTime().getValue();

                LocalDate firstStartDate = Instant.ofEpochMilli(firstStart)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime().toLocalDate();

                LocalDate lastEndDate = Instant.ofEpochMilli(lastEnd)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                        .toLocalDate();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                String formattedFirstStartDate = firstStartDate.format(dateFormatter);
                String formattedLastEndDate = lastEndDate.format(dateFormatter);

            return formattedFirstStartDate + " " + formattedLastEndDate;

        }
}
