package com.epam.functions;

import com.epam.functions.model.SearchRequestInstant;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * TODO: Fix it
 */
public class CollectorsAndStreamsTests {

    @Test
    public void collectors() throws Throwable {
        SearchRequestInstant.Builder builder = SearchRequestInstant.newBuilder()
                .setProduct("Product 1")
                .setRequestId("Request 1")
                .setSearchRequestType(SearchRequestInstant.SearchRequestType.ADD)
                .setUserId(Optional.of("User 1"));
        SearchRequestInstant sr = builder.build();

        List<SearchRequestInstant> lists = new ArrayList<>();
        lists.add(sr);

        builder = SearchRequestInstant.newBuilder()
                .setProduct("Product 2")
                .setRequestId("Request 2")
                .setSearchRequestType(SearchRequestInstant.SearchRequestType.DELETE)
                .setUserId(Optional.of("User 2"));
        sr = builder.build();

        lists.add(sr);

        // Apply the colector to convert to the String

        final String listString = lists.stream().map(SearchRequestInstant::getProduct).collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Collectors product: " + listString);
        final int sum = lists.stream().mapToInt(this::extractId).sum();
        System.out.println("Summer: " + sum);

        // grouping
        builder = SearchRequestInstant.newBuilder()
                .setProduct("Product 3")
                .setRequestId("Request 3")
                .setSearchRequestType(SearchRequestInstant.SearchRequestType.DELETE)
                .setUserId(Optional.of("User 3"));
        sr = builder.build();

        Map<SearchRequestInstant.SearchRequestType, List<SearchRequestInstant>> map = lists.stream().collect(Collectors.groupingBy(SearchRequestInstant::getSearchRequestType));
        System.out.println("Keys");
        map.keySet().forEach(System.out::println);
        System.out.println("Values");
        map.values().forEach(System.out::println);
        lists.add(sr);
    }

    @Test
    public void testMatchAny() {
        IntStream stream = IntStream.rangeClosed(0, 10);

        Function<Integer, String> mapper = (Integer intValue) -> String.valueOf(intValue);

        OptionalDouble option = stream.average();
        System.out.println("option" + option);

//        String str = stream.map(mapper::apply).collect(Collectors.joining(",", "[", "]"));
    }

    private int extractId(SearchRequestInstant t) {
        final String reqStr = t.getProduct();
        return Integer.valueOf(reqStr.substring(reqStr.length() - 1, reqStr.length()));
    }

    @Test
    public void findFirst() {
        IntStream stream = IntStream.rangeClosed(0, 9);
        OptionalInt result = stream.parallel().filter(i -> i < 5 && i > 0).findFirst();
        System.out.println("result: " + result.getAsInt());
    }

    @Test
    public void findAny() {
        IntStream stream = IntStream.rangeClosed(0, 9);
        OptionalInt result = stream.parallel().filter(i -> i < 5 && i > 0).findAny();
        System.out.println("result: " + result.getAsInt());
    }
}

