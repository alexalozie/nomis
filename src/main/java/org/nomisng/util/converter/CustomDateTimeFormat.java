package org.nomisng.util.converter;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomDateTimeFormat {

    public static LocalDate LocalDateByFormat(LocalDate date, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(formatter.format(date),formatter);
    }

    public static LocalTime LocalTimeByFormat(LocalTime time, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalTime.parse(formatter.format(time),formatter);
    }
}
