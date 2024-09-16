package com.home;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * TODO: fit it
 */
public class ConcurrentOperatorsTest {

    private static Map<Integer, String> map = new ConcurrentHashMap();
    private static Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
    private static Map<Integer, String> syncMap = Collections.synchronizedMap(map);

    /**
     * TODO: replace the HashMap to Concurrent Map because HashMap is not-ThreadSafe
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSizeWeakAffectWithoutNullInInsertion() throws ExecutionException, InterruptedException {

        AtomicReference<String> ref = new AtomicReference<>();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future futureA = executorService.submit(() -> {
            IntStream.range(0, 500000).forEach(
                    (i) -> {
                        Integer key = ThreadLocalRandom.current().nextInt(1000);
                        String value = "A" + key;
                        map.put(key, "A" + key); // use putIfAbsent for performance enhancement
                        String returnValue = map.get(key);
                        if (!value.equals(returnValue)) {
                            ref.compareAndSet(null, "Non Thread Safe Map presents");
                        }
                    }
            );
        });

        Future futureB = executorService.submit(() -> {
            IntStream.range(0, 500000).forEach(
                    (i) -> {
                        Integer key = ThreadLocalRandom.current().nextInt(1000);
                        String value = "A" + key;
                        map.put(key, "A" + key);
                        String returnValue = map.get(key);
                        if (!value.equals(returnValue)) {
                            ref.compareAndSet(null, "Non Thread Safe Map presents");
                        }
                    }
            );
        });

        futureA.get();
        futureB.get();

        if (!executorService.awaitTermination(3, TimeUnit.SECONDS))
            executorService.shutdownNow();

        Assert.assertNull(ref.get());
        concurrentHashMap.clear();
        map.clear();
    }


    /**
     * TODO: Fix the test with Concurrent Map
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testConcurrentOperationFailure() throws ExecutionException, InterruptedException {
//        ForkJoinPool.commonPool().submit(()->{}); why not
        CompletableFuture<Void> futureA = CompletableFuture.supplyAsync(() -> {
            IntStream.range(0, 100).forEach(
                    (i) -> {
                        map.put(i, "O");
                        sleep(1);
                    }
            );
            return null;
        });

        CompletableFuture<Void> futureB = CompletableFuture.supplyAsync(read(map));
        CompletableFuture.allOf(futureA, futureB).get(); // blocking operator - wait till two completablefuture are finished and return result

        map.clear();
        concurrentHashMap.clear();
    }

    /**
     * TODO: Fix the test using Collections.synchronizedMap(map) - wrapper
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSyncMap() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> futureA = CompletableFuture.supplyAsync(() -> {
            IntStream.range(0, 100).forEach(
                    (i) -> {
                        map.put(i, "O");
                        sleep(1);
                    }
            );
            return null;
        });

        // iteration over the Map
        CompletableFuture<Void> futureB = CompletableFuture.supplyAsync(read(map));
        CompletableFuture.allOf(futureA, futureB).get();
        map.clear();
        concurrentHashMap.clear();
    }

    private Supplier<Void> read(Map<Integer, String> map) {
        // iteration through the map cause ConcurrentModificationException
        return () -> {
            sleep(50); // the size is changing
            map.forEach((key, value) -> {
                if (key % 10 == 0)
                    System.out.println(key + ":" + value);
                sleep(1);
            });
            return null;
        };
    }


    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
