package com.epam.functional.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class E_Stream_ParallelStream {

    @Test
    public void paralel() {
        IntStream.iterate(0, x -> x + 1).limit(10).forEach(
                x -> {
                    System.out.println(x);
                }
        );
        // parallel doesn't guarantee ordering
        System.out.println("--- Parallel");
        IntStream.iterate(0, x -> x + 1).parallel().limit(10).forEach(
                x -> {
                    System.out.println(x);
                }
        );
        // parallel doesn't guarantee ordering
        System.out.println("--- Parallel enforced ordered");
        IntStream.iterate(0, x -> x + 1).parallel().limit(10).forEachOrdered(
                x -> {
                    System.out.println(x);
                }
        );
    }

    @Test
    public void non_safe_operation_lambda(){
        List<Integer> list = new ArrayList();
        IntStream.range(0, 1000000).forEach(x -> {
            list.add(x);
        });

        List<Integer> parallel = new ArrayList();

        IntStream.range(0, 1000000).parallel().forEach(x -> {
            parallel.add(x); // non thread safe
        });
        assertNotEquals(parallel.size(), list.size()); // unpredictable
    }



}
