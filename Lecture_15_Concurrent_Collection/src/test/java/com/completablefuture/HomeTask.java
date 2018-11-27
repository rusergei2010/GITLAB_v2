package com.completablefuture;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class HomeTask {


    /**
     * TODO: Fix the issue by replacing the HashMap with ConcurrentHashMap
     * TODO: Consider the commonpool instead of CompletableFuture: "ForkJoinPool.commonPool().submit(()->{})"
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testConcurrentOperationFailure() throws ExecutionException, InterruptedException {
        Map<Integer, String> map = new ConcurrentHashMap<>();

        ForkJoinPool.commonPool().submit(()->{IntStream.range(0, 100).forEach(
                (i) -> {
                    map.put(i, "Andrey");
                    sleep(1);
                }
        );});

        AtomicReference<String> reference = new AtomicReference<>();
        // second traversal
        ForkJoinPool.commonPool().submit(() -> {
            sleep(60); // the size is changing
            map.forEach((key, value) -> {
                if (key % 10 == 0)
                    System.out.println(key + ":" + value);
                sleep(1);
            });
            reference.set("Done");
        });

        String str;
        while (((str = reference.get()) == null)){
        }
        map.clear();
    }


    /**
     * TODO: Read in between lines
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testConcurrentOperationSuccess() throws ExecutionException, InterruptedException {
        Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();

        CompletableFuture<Void> futureA = CompletableFuture.supplyAsync(() -> {
            IntStream.range(0, 100).forEach(
                    (i) -> {
                        concurrentHashMap.put(i, "X"); // Line 1
                        sleep(1);
                    }
            );
            return null;
        });


        sleep(10); // TODO: Use ConcurrentHashMap to avoid "ConcurrentModificationException"

        CompletableFuture<Void> futureB = CompletableFuture.supplyAsync(() -> {
            IntStream.range(0, 100).forEach(
                    (i) -> {
                        concurrentHashMap.putIfAbsent(i, "O"); // Line 2
                        sleep(1);
                    }
            );
            return null;
        });

        concurrentHashMap.entrySet().forEach((entry) -> {
            System.out.println("key:" + entry.getKey() + " value:" + entry.getValue());
            sleep(1);
        });

        CompletableFuture.allOf(futureA, futureB).get();

        if (concurrentHashMap.entrySet().stream().map(Map.Entry::getValue).anyMatch((String value) -> value.equals("O"))) {
            throw new RuntimeException("Found wrong symbol"); // TODO: Fix this exception in Line 1,2. Symbol should be "X"
        }

        concurrentHashMap.clear();
    }


    // TODO: Fix with Unmodifiable collection
    @Test(expected = UnsupportedOperationException.class)
    public void immutableCollections() throws Throwable {
        ArrayList<Integer> mutableList = new ArrayList<>();
        IntStream.range(0, 10).forEach(mutableList::add);
        List<Integer> immutable = Collections.unmodifiableList(mutableList); // TODO: Fix in this line

        try {
            CompletableFuture.supplyAsync(() -> {
                IntStream.range(0, 10).forEach((i) -> {
                    immutable.add(-1);
                    sleep(100);
                });
                return null;
            }).get();
        } catch(ExecutionException e) {
            throw e.getCause();
        }

        final Iterator<Integer> iterator = immutable.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            sleep(100);
        }
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
