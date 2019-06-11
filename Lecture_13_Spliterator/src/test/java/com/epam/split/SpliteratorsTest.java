package com.epam.split;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.junit.Test;

/**
 * Explorer trySplit and creation of Spliterator:
 *  - from iterator
 *  - from array
 *  - custom spliterator
 *  - from collections and to collections (using StreamSupport)
 */
public class SpliteratorsTest {

    @Test
    public void testSpliteratorFromStream() throws InterruptedException {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        Spliterator spliterator = list.spliterator();

        StreamSupport.stream(spliterator, false).forEach(System.out::println);
    }


    @Test
    public void testCreateSpliteratorWithSortedCharacteristics() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("0");
        list.add("3");
        list.add("4");

        Spliterator<String> strSpliterator = Spliterators
                .spliterator(list, Spliterator.ORDERED | Spliterator.SORTED | Spliterator.NONNULL);

        // sorted will have no effect since it is a predefined characteristics
        // create Stream from spliterator
        StreamSupport.stream(strSpliterator, false).sorted().limit(10).forEach(System.out::println);
    }


    private static class MyIterator implements Iterator<Integer> {
        int i = 0;

        @Override public boolean hasNext() {
            return true;
        }

        @Override public Integer next() {
            return i++;
        }
    }

    @Test
    public void testCreateSpliteratorFromIterator() {
        Spliterator<Integer> integerSpliterator = Spliterators
                .spliteratorUnknownSize(new MyIterator(), Spliterator.ORDERED);

        long count = StreamSupport.stream(integerSpliterator, false).limit(100).count();
        System.out.println("Count = " + count);
    }


//    @Test
//    public void testCreateFromArraySpliterator() {
//        List<Integer> source = IntStream.range(0, 1000).boxed().collect(Collectors.toList());
//
//        MySpliterator<Integer> splits = new MySpliterator(source.toArray());
//        StreamSupport.stream(splits, false).limit(1000).forEach(System.out::println);
//    }

}
