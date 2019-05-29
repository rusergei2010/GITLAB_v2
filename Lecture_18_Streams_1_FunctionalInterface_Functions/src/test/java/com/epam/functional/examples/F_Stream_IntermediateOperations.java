package com.epam.functional.examples;

import java.util.ArrayList;
import java.util.List;
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

}
