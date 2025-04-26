package com.Task_Forge.Microservice.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    public static String format(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static LocalDateTime parse(String dateTimeString){
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
