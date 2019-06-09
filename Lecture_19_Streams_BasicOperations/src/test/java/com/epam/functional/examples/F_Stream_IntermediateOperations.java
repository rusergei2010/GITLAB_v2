package com.epam.functional.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.junit.Test;

import static java.util.stream.Collectors.joining;

public class F_Stream_IntermediateOperations {

    @Test
    public void filter() {
        Stream.of(1, 2, 3).filter(x -> x == 4).forEach(System.out::print); // no output
        Stream.of(1, 2, 3).filter(x -> x == 3).forEach(System.out::print);
    }

    /**
     * Without terminate operation thr intermediate will not be carried out
     */
    @Test
    public void peekEx() {
        final List<Integer> list = new ArrayList();
        Stream.of(1, 2, 3, 4, 5, 6)
                .peek(list::add);// will not collect

        Stream.of(1, 2, 3, 4, 5, 6)
                .peek(list::add)
                .findAny();// will not collect

        System.out.println(list.stream().map(String::valueOf).collect(joining()));
    }

    /**
     * Applied to IntStream, DoubleStream and etc.
     */
    @Test
    public void mapToObj() {
        Optional<String> result =
                DoubleStream.iterate(0, a -> a + 1)
                        .limit(10)
                        .mapToObj(String::valueOf)
                        .reduce((a, b) -> a + ", " + b );
        System.out.println(result);
    }

    /**
     * flatMap Example, reduce
     */
    @Test
    public void flatMapReduce() {

        int resultSum = Stream.of("1 2 3 4 5 6 7 8").flatMap(x -> {
            return Stream.of(x.split(" ")); // flatMap returns Stream but intercepts the object value
        }).mapToInt(Integer::valueOf).reduce((a, b) -> a + b).orElseThrow(() -> new RuntimeException());

        System.out.println("Sum [1..8] = " + resultSum);
    }

    @Test
    public void box() {
        DoubleStream.of(0.1, Math.PI)
                .boxed() // transfer primitive to Stream of object
                .map(Object::getClass)
                .forEach(System.out::println);
    }
}
