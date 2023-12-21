package com.example.rosaapi.utils;

import com.example.rosaapi.model.dtos.CalendarEventDTO;
import com.google.api.services.calendar.model.Event;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.TimeZone;

public final class DateTimeUtils {

        private DateTimeUtils() {

        }

    public static CalendarEventDTO convertUnixToDTO(long start, long end, String summary) {

            SimpleDateFormat timeConverter = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dateConverter = new SimpleDateFormat("dd-MM-yyyy");

            dateConverter.setTimeZone(TimeZone.getTimeZone("GMT-3"));
            timeConverter.setTimeZone(TimeZone.getTimeZone("GMT-3"));

            SimpleDateFormat dayOfWeekConverter = new SimpleDateFormat("EEE");
            dayOfWeekConverter.setTimeZone(TimeZone.getTimeZone("GMT-3"));

            String formattedDay = dayOfWeekConverter.format(start);
            String formattedDate = dateConverter.format(start);
            String formattedStart = timeConverter.format(start);
            String formattedEnd = timeConverter.format(end);

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
                SimpleDateFormat dateConverter = new SimpleDateFormat("dd-MM-yyyy");

                String formattedFirstStartDate = dateConverter.format(firstStart);
                String formattedLastEndDate = dateConverter.format(lastEnd);

                return formattedFirstStartDate + " " + formattedLastEndDate;
        }
}
