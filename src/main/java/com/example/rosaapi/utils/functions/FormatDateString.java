package com.example.rosaapi.utils.functions;

import com.example.rosaapi.utils.exceptions.InvalidDateTimeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FormatDateString {


    public static String getFormattedString(String date) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return  myFormat.format(originalFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            throw  new InvalidDateTimeException("Erro na formatação");
        }
    }
}
