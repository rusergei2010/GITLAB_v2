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


    @Test
    public void findFirst(){
        IntStream stream = IntStream.rangeClosed(0, 9);
        OptionalInt result = stream.parallel().filter(i -> i < 5 && i > 0).findFirst();
        System.out.println("result: " + result.getAsInt());
    }

    @Test
    public void findAny(){
        IntStream stream = IntStream.rangeClosed(0, 9);
        OptionalInt result = stream.parallel().filter(i -> i < 5 && i > 0).findAny();
        System.out.println("result: " + result.getAsInt());
    }

}





