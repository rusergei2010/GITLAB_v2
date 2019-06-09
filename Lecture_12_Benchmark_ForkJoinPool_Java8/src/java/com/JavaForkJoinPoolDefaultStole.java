package com;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The second ForkJoinPool effectively will run in only one Thread-1
 * Once the first parallel Stream is completed then the second will Grab released free Threads from ForkJoinPool
 */
public class JavaForkJoinPoolDefaultStole {
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

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            String integers = strs.parallelStream()
                    .peek(s -> sleep(100))
                    .peek(x -> System.out.println("1: " + Thread.currentThread().getName()))
                    .map(JavaForkJoinPoolDefaultStole::convertToNumer)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            System.out.println(integers);
        }).start();


        System.out.println("Start new ForkJoin ThreadPool");

        new Thread(() -> {
            String stuck = strs.parallelStream()
                    .peek(s -> sleep(100))
                    .peek(x -> System.out.println("2: " + Thread.currentThread().getName()))
                    .map(JavaForkJoinPoolDefaultStole::convertToNumer)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            System.out.println(stuck);
        }).start();

        System.out.println("Exit Main");


    }

    private static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Integer convertToNumer(String s) {
        if (Pattern.matches("\\d+", s)) {
            return Integer.valueOf(s);
        }
        return null;
    }

}
