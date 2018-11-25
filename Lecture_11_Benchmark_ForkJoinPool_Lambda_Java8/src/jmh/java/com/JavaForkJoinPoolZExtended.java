package com;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaForkJoinPoolZExtended {
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


        new Thread(() -> {
            String integers = strs.parallelStream()
                    .peek(s -> sleep(10000))
                    .peek(x -> System.out.println("1: " + Thread.currentThread().getName()))
                    .map(JavaForkJoinPoolZExtended::convertToNumer)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            System.out.println(integers);
        }).start();

        System.out.println("Start new Hand-Made ForkJoin ThreadPool");

        final int parallelism = 4; // play around
        final ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);

        try {
            String integers = forkJoinPool.submit(() -> {
                    System.out.println("Outer: " + Thread.currentThread().getName());
                        //parallel stream invoked here
                return strs.parallelStream()
                                .peek(s -> sleep(100))
                                .peek(x -> System.out.println("2: " + Thread.currentThread().getName()))
                                .map(JavaForkJoinPoolZExtended::convertToNumer)
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

    private static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
