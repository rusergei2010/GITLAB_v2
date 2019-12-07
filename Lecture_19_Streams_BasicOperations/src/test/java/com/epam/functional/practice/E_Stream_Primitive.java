package com.epam.functional.practice;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class E_Stream_Primitive {

    /**
     *   - IntStream для int,
     *   - LongStream для long,
     *   - DoubleStream для double.
     */
    @Test
    public void primitiveStream() {

        List<String> list = new ArrayList<>();
        IntStream.iterate(0, x -> x + 1).limit(10).map(x -> x * x)
                ///.map(String::valueOf) // assumes that the pipe will remain int - primitive
                .mapToObj(String::valueOf) // need to map to Object
                .forEach(x -> {
                    list.add(x);
                }
        );
    }

    /**
     * Double -> String
     * Joining strings
     */
    @Test
    public void forStream() {

        final String str = DoubleStream.iterate(0, x -> x + 1).limit(10).mapToObj(x -> String.valueOf(x))
                .collect(Collectors.joining(", "));
        System.out.println(str);
        assertEquals("0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0", str);
    }
}
