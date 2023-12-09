package com.example.rosaapi.utils;

import com.example.rosaapi.model.dtos.CalendarEventDTO;

import java.text.SimpleDateFormat;
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
}
