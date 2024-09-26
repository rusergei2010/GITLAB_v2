package home;

import java.util.Collections;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collector;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class Test3 {


    /**
     * TODO: Fix the issue by replacing the HashMap with ConcurrentHashMap
     * TODO: Consider the commonpool instead of CompletableFuture: "ForkJoinPool.commonPool().submit(()->{})"
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testConcurrentOperationFailure() throws ExecutionException, InterruptedException {
        ForkJoinPool.commonPool().submit(()->{});

        Map<Integer, String> map = new ConcurrentHashMap<>();
        CompletableFuture<Void> futureA = CompletableFuture.supplyAsync(() -> {
            IntStream.range(0, 100).forEach(
                (i) -> {
                    map.put(i, "Andrey");
                    sleep(1);
                }
            );
            return null;
        });

        // second traversal
        CompletableFuture<Void> futureB = CompletableFuture.supplyAsync(() -> {
            sleep(50); // the size is changing
            map.forEach((key, value) -> {
                if (key % 10 == 0)
                    System.out.println(key + ":" + value);
                sleep(1);
            });
            return null;
        });
        CompletableFuture.allOf(futureA, futureB).get(); // blocking operator - wait till two completablefuture are finished and return result

        map.clear();
    }


    /**
     * TODO: Read in between lines instructions
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testConcurrentOperationSuccess() throws ExecutionException, InterruptedException {
        Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();

        CompletableFuture<Void> futureA = CompletableFuture.supplyAsync(() -> {
            IntStream.range(0, 100).forEach(
                (i) -> {
                    concurrentHashMap.putIfAbsent(i, "X"); // Line 1
                    sleep(1);
                }
            );
            return null;
        });


        sleep(10); // TODO: Use ConcurrentHashMap to avoid "ConcurrentModificationException"

        CompletableFuture<Void> futureB = CompletableFuture.supplyAsync(() -> {
            IntStream.range(0, 100).forEach(
                (i) -> {
                    concurrentHashMap.put(i, "X"); // Line 2
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
        List<Integer> immutable = Collections.unmodifiableList(new ArrayList<>(mutableList)); // TODO: Fix in this line

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
