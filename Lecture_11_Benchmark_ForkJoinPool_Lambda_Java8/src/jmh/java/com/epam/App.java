package com.epam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {

    //    @FunctionalInterface
    private interface MyPredicate<V> {
        boolean test(V object);

        //        boolean test2(V object);
        default void method(Object o) {

        }
    }

    public static void main(String[] args) {
        Collection<String> list = Arrays.asList("1", "2", "3", "Four", "Three");

        Predicate<String> number = number1 -> Pattern.matches("\\d+", number1);

        Function<String, Integer> function = str -> Integer.valueOf(str);
        Function<String, Integer> function2 = Integer::valueOf;

        List<String> doubleStrings = new ArrayList<>();

        Function<String, String> functionDouble = (String str) -> {
            String result = str + str;
            doubleStrings.add(result);
            return result;
        };


//        Function<String, User> functionUser = User::new;
//        BiFunction<String, String, User> biFunctionUser = User::new;
//        Supplier<User> supplier = User::new;

        Function<Integer, User> builder = User::new;


        List<String> collect = list.stream().filter(number).collect(Collectors.toList());
        List<Integer> ints = list.stream().filter(number).map(function).collect(Collectors.toList());

        List<String> strings = list.stream().filter(number).map(function).map(Object::toString).map(functionDouble).collect(Collectors.toList());
        doubleStrings.forEach(System.out::println);


//        IntStream.range(0, 10).mapToObj(Integer::valueOf).map(builder).collect();



    }

    private static class User {
        Integer age;
        Optional<String> house;

        public User() {
        }

        public User(Integer age) {
            this.age = age;
        }
    }
}
