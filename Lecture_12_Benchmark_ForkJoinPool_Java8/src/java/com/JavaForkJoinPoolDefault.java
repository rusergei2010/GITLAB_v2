package com;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaForkJoinPoolDefault {
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

    public static void main(String[] args) {
        String integers = strs.parallelStream()
                .peek((x) -> System.out.println(Thread.currentThread().getName()))
                .map(JavaForkJoinPoolDefault::convertToNumer)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        System.out.println(integers);
    }

    private static Integer convertToNumer(String s) {
        if (Pattern.matches("\\d+", s)) {
            return Integer.valueOf(s);
        }
        return null;
    }
}
