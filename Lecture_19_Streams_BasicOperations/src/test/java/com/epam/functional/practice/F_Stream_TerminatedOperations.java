package com.epam.functional.practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class F_Stream_TerminatedOperations {

    @Test
    public void forEachOrdered() {
        Stream.of(1, 2, 3, 4, 5, 6).parallel().forEachOrdered(System.out::println);
        System.out.println("Unordered after parallel is applied");
        Stream.of(1, 2, 3, 4, 5, 6).parallel().forEach(System.out::println); // no output
    }

    /**
     * else, orEseThrow example
     */
    @Test
    public void terminalElse() {

        int resultSum = Stream.of("1 2 3 4 5 6 7 8").flatMap(x -> {
            return Stream.of(x.split(" "));
        }).mapToInt(Integer::valueOf)
                .filter(x -> x == -1)
                .findAny()
                .orElse(-1);

        System.out.println("Sum [1..8] = " + resultSum); // will result in -1
    }

    /**
     * else, orEseThrow example
     */
    @Test(expected = RuntimeException.class)
    public void terminalOrElseThrow() {

        int resultSum = Stream.of("1 2 3 4 5 6 7 8").flatMap(x -> {
            return Stream.of(x.split(" "));
        }).mapToInt(Integer::valueOf)
                .filter(x -> x == -1)
                .findAny()
                .orElseThrow(() -> {
                    throw new RuntimeException();
                }); // exception is thrown

        System.out.println("Sum [1..8] = " + resultSum); // will result in -1
    }

    @Test
    public void sum() {
        Integer sum = IntStream.rangeClosed(0, 100).parallel().sum();
        System.out.println(sum);
    }

    /**
     * Collector<Type source, Type accumulator, Type result> сollector =  Collector.of(
     * method accumulator initialization,
     * method processing each element
     * method concatenation of two accumulators,
     * [post processing of accumulators, used in parallel stream]
     * );
     */
    @Test
    public void collectStrings() {
        String result = Stream.of("11", "22", "abc", "a", "2", "", null)
                .filter(Objects::nonNull) // exclude null
                .filter(s -> {
                    return s.length() > 1; // length > 1
                })
                .map(String::toUpperCase) // to upper case
                .collect(
                        new Collector<CharSequence, StringBuilder, String>() { // see Collectors.joining() and simply copy here

                            @Override public Supplier<StringBuilder> supplier() {
                                return StringBuilder::new;
                            }

                            @Override public BiConsumer<StringBuilder, CharSequence> accumulator() {
                                return StringBuilder::append;
                            }

                            @Override public BinaryOperator<StringBuilder> combiner() {
                                return (StringBuilder r1, StringBuilder r2) -> {
                                    r1.append(r2);
                                    return r1;
                                };
                            }

                            @Override public Function<StringBuilder, String> finisher() {
                                return StringBuilder::toString;
                            }

                            @Override public Set<Characteristics> characteristics() {
                                return new HashSet() {{
                                    add(Characteristics.UNORDERED);
                                }};
                            }
                        }
                );

        System.out.println(result);
    }

    /**
     * The same collector but with Collector.of() factory method call instead of Impl class
     */
    @Test
    public void collectStrings2() {
        String result = Stream.of("11", "22", "abc", "a", "2", "", null)
                .filter(Objects::nonNull) // exclude null
                .filter(s -> {
                    return s.length() == 1; // length == 1
                })
                .map(String::toUpperCase) // to upper case
                .collect(
                        Collector.of(
                                StringBuilder::new, // method accumulator initialization
                                (b, s) -> b.append(s).append(" , "), // method processing each element
                                (b1, b2) -> b1.append(b2).append(" , "), // method concatenation of two accumulators
                                StringBuilder::toString // post processing of accumulators, used in parallel stream
                        ));

        System.out.println(result);
    }

    @Test
    public void collectWithArrayList() {
        Collector<String, List<String>, List<String>> toList = Collector.of(
                ArrayList::new, // method accumulator initialization
                List::add, // method processing each element
                (l1, l2) -> { l1.addAll(l2); return l1; } // method concatenation of two accumulators with parallel processing
        );
        List<String> distinct1 = Stream.of("1", "0", "0", "1").collect(toList);
        System.out.println("distinct = " + distinct1);

    }

    @Test
    public void testSkipMax() {
        Optional<String> s = Stream.of("100", "10", "0").skip(1).max(String::compareTo);
        System.out.println(s.orElse("None"));

        s = Stream.of("1", "10", "0").skip(5).max(String::compareTo);
        // print None
        System.out.println(s.orElse("None"));
    }

    @Test
    public void groupBy() {
        Map<Integer, List<String>> map = Stream.of("9", "a", "b", "ab", "8", "abc", "ggg", "mmm", "kk").collect(
                Collectors.groupingBy(
                        String::length)); // function classifier - String:length. It can be boolean or another.
        //        Map<Boolean, List<String>> map = Stream.of("9", "a", "b", "ab", "8", "abc", "ggg", "mmm", "kk").collect(
        //                Collectors.groupingBy(s -> s.length() > 2)); // function classifier - String:length. It can be boolean or another.

        // forEach is the same as peek but terminal operation
        map.entrySet().stream()
                .forEach(entry -> {
                    System.out.println();
                    System.out.println(entry.getKey() + " -> ");
                    entry.getValue().stream().map(s -> " " + s).forEach(System.out::print);
                });
    }

    /**
     * String sophisticated example with grouping by the first char
     */
    @Test
    public void collectGroupByExt() {

        // group by the first symbol match
        Map<String, List<String>> map = Stream.of("a123", "b123", "c123", "aaaa", "bbbb", "cccc")
                .collect(Collectors.groupingBy((String s) -> s.substring(0, 1)));

        map.entrySet().stream()
                .forEach(entry -> {
                    System.out.println();
                    System.out.println(entry.getKey() + " -> ");
                    entry.getValue().stream().map(s -> s).forEach(System.out::println);
                });
    }

    /**
     * groupBy for Boolean function classifier == partitionBy collect operation
     */
    @Test
    public void partitionBy() {

        // groupBy for boolean == partitionBy
        Map<Boolean, List<String>> map = Stream.of("9", "a", "b", "ab", "8", "abc", "ggg", "mmm", "kk").collect(
                Collectors.groupingBy(
                        s -> s.length() > 2)); // function classifier - String:length. It can be boolean or another.

        Map<Boolean, List<String>> map2 = Stream.of("9", "a", "b", "ab", "8", "abc", "ggg", "mmm", "kk").collect(
                Collectors.partitioningBy(
                        s -> s.length() > 2)); // function classifier - String:length. It can be boolean or another.

        System.out.println("---GROUP_BY---");
        map.entrySet().stream()
                .forEach(entry -> {
                    System.out.println();
                    System.out.println(entry.getKey() + " -> ");
                    entry.getValue().stream().map(s -> " " + s).forEach(System.out::print);
                });

        System.out.println("");
        System.out.println("");
        System.out.println("---PARTITION_BY---");
        map2.entrySet().stream()
                .forEach(entry -> {
                    System.out.println();
                    System.out.println(entry.getKey() + " -> ");
                    entry.getValue().stream().map(s -> " " + s).forEach(System.out::print);
                });
    }

    /**
     * String joining - collect
     */
    @Test
    public void collectJoiningBy() {

        // collect joining string
        String str = Stream.of("a", "b", "c").collect(Collectors.joining(",", "<", ">"));
        System.out.println(str);
    }

}