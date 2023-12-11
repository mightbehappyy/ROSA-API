package com.example.rosaapi.utils;

import com.example.rosaapi.model.dtos.CalendarEventDTO;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

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
}
