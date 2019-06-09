package com.epam.barrier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.junit.Test;

public class SpliteratorTest {

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

    public static class MySpliterator<T> implements Spliterator<T> {

        public T[] source;

        public MySpliterator(T[] ints) {
            source = ints;
        }

        @Override public boolean tryAdvance(final Consumer<? super T> action) {
            System.out.println("tryAdance");

            for (int i = 0; i < source.length / 2; i++) {
                action.accept(source[i]);
                sleep(1);
            }
            return true;
        }

        @Override public Spliterator<T> trySplit() {
            System.out.println("trySplit");
            int mid = (int) Math.ceil((double) (source.length / 2));
            return new MySpliterator<T>(Arrays.copyOfRange(source, mid, source.length));
        }

        @Override public long estimateSize() {
            System.out.println("estimateSize");
            return source.length;
        }

        @Override public int characteristics() {
            System.out.println("characteristics");
            return ORDERED | DISTINCT | NONNULL | ORDERED;
        }

        private void sleep(final int i) {
            try {
                Thread.sleep(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testCreateCustomSpliterator() {
        List<Integer> source = IntStream.range(0, 1000).boxed().collect(Collectors.toList());


        MySpliterator<Integer> splits = new MySpliterator(source.toArray());
        StreamSupport.stream(splits, false).limit(1000).forEach(System.out::println);
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

        StreamSupport.stream(integerSpliterator, false).limit(10).forEach(System.out::println);
    }
}
