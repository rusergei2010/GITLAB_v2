package com;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaForkJoinPoolParallelism {
    private static final Collection<String> strs = Arrays.asList(
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero",
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero",
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero",
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero",
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero",
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero",
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero",
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero",
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero",
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero",
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero",
            "1", "2", "3", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero"
            );
    private static Predicate<? super Integer> notNull = (x) -> x != null;

    public static void main(String[] args) {


        final int parallelism = 5; // play around
        final ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);

        try {
            String integers = forkJoinPool.submit(() -> {
                    System.out.println("Outer: " + Thread.currentThread().getName());
                        //parallel stream invoked here
                        return strs.parallelStream()
                                .peek((x) -> System.out.println(Thread.currentThread().getName()))
                                .map(JavaForkJoinPoolParallelism::convertToNumer)
                                .filter(notNull)
                                .map(String::valueOf)
                                .collect(Collectors.joining(","));
                    }
            ).get(); //this makes it an overall blocking call

            System.out.println(integers);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown(); //always remember to shutdown the pool
        }
    }

    private static Integer convertToNumer(String s) {
        if (Pattern.matches("\\d+", s)) {
            return Integer.valueOf(s);
        }
        return null;
    }
}
