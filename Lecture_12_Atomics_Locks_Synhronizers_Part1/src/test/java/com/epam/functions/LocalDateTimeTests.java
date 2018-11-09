package com.epam.functions;


import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class LocalDateTimeTests {

    private static final DateTimeFormatter formatter_iso = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter formatter_iso_date_time = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Test
    public void collectors() throws Throwable {
        LocalDate localDate = LocalDate.now();
        System.out.println("Local Date: " + localDate.format(formatter_iso));

        System.out.println("Local Date time: " + localDate);
        System.out.println("Local Date time: " + localDate.format(formatter_iso_date_time));
//        System.out.println("Local Date time: " + localDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }
}





