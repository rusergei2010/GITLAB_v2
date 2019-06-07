package com;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Demonstrates all essential mechanisms of work of collections (immutable, concurernt)
 * size(), isEmpty() - weak
 * Iterations
 * Exceptions in Collections while iteration, insertion and retrieval
 */
public class ConcurrentOperators {

    private static Map<Integer, String> map = new HashMap();
    private static Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();


    /**
     * Size(), also isEmpty() operation is weak in the map and return right value just in single threaded apps
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSizeInSingleThread() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future futureA = executorService.submit(() -> {
            IntStream.range(0, 100000).forEach(
                    (i) -> {
                        Integer key = ThreadLocalRandom.current().nextInt(1000);
                        String value = "A" + key;
                        map.put(key, value);
                        if (!value.equals(map.get(key))){
                            System.out.println("Error match");
                        }
                    }
            );
        });
        futureA.get();

        System.out.println("Map size = " + map.size());

        if (!executorService.awaitTermination(2, TimeUnit.SECONDS))
            executorService.shutdownNow();

        map.clear();
    }


    /**
     * Size() operation is weak in the map and return right value just in single threaded apps (!= 1000)
     * size() can be violated for HashMap even if it is calculated after all modifications
     * But it doesn't cause ConcurrentModification Exception of iterator if it is not extracted.
     * Insertion is not atomic for HashMap if it is performed in different threads
     * Run several times the test
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSizeWeakAffectAndPutNull_Cannot_Insert_A_NEW_VALUE() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future futureA = executorService.submit(() -> {
            IntStream.range(0, 100000).forEach(
                    (i) -> {
                        Integer key = ThreadLocalRandom.current().nextInt(1000);
                        String value = "A" + key;
                        map.put(key, "A" + key);
                        String returnValue = map.get(key);
                        if (!value.equals(returnValue)){
                            System.out.println("B : Error match key:" + key + " value:" + value + " returnValue:" + returnValue);
                        }
                    }
            );
        });

        Future futureB = executorService.submit(() -> {
            IntStream.range(0, 100000).forEach(
                    (i) -> {
                        Integer key = ThreadLocalRandom.current().nextInt(1000);
                        String value = "A" + key;
                        map.put(key, "A" + key);
                        String returnValue = map.get(key);
                        if (!value.equals(returnValue)){
                            System.out.println("A : Error match key:" + key + " value:" + value + " returnValue:" + returnValue);
                        }
                    }
            );
        });

        futureA.get();
        futureB.get();
        System.out.println("Map size = " + map.size());

        map.entrySet().stream().filter(e -> !e.getKey().toString().equals(String.valueOf(e.getValue()))).forEach((entry) -> {
            System.out.println("" + entry.getKey() + ":" + entry.getValue());
        });

        if (!executorService.awaitTermination(3, TimeUnit.SECONDS))
            executorService.shutdownNow();

        map.clear();
    }

    /**
     * Size() operation is weak even in ConcurrentHashMap during com.data modification (but in the end of modifications it gives correct size)
     * But in the end of all modifications it gives correct result
     * putIfAbsent() - Atomic operation of Insertion for ConcurrentHashMap but not for Map im general
     * computeIfAbsent() - good another sample
     * Run several times the test
     * ThreadSafe Concurrent Case
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSizeWeakAffectWithoutNullInInsertion() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future futureA = executorService.submit(() -> {
            IntStream.range(0, 500000).forEach(
                    (i) -> {
                        Integer key = ThreadLocalRandom.current().nextInt(1000);
                        String value = "A" + key;
                        concurrentHashMap.put(key, "A" + key); // use putIfAbsent for performance enhancement
                        String returnValue = concurrentHashMap.get(key);
                        if (!value.equals(returnValue)){
                            System.out.println("B : Error match key:" + key + " value:" + value + " returnValue:" + returnValue);
                        }
                    }
            );
        });

        Future futureB = executorService.submit(() -> {
            IntStream.range(0, 500000).forEach(
                    (i) -> {
                        Integer key = ThreadLocalRandom.current().nextInt(1000);
                        String value = "A" + key;
                        concurrentHashMap.put(key, "A" + key);
                        String returnValue = concurrentHashMap.get(key);
                        if (!value.equals(returnValue)){
                            System.out.println("A : Error match key:" + key + " value:" + value + " returnValue:" + returnValue);
                        }
                    }
            );
        });

        futureA.get();
        futureB.get();

        // Will give 1000 all time when operations are completed (guaranteed)
        System.out.println("Map size = " + concurrentHashMap.size());

        concurrentHashMap.entrySet().stream().filter(e -> !e.getKey().toString().equals(String.valueOf(e.getValue()))).forEach((entry) -> {
            System.out.println("" + entry.getKey() + ":" + entry.getValue());
        });

        if (!executorService.awaitTermination(3, TimeUnit.SECONDS))
            executorService.shutdownNow();

        concurrentHashMap.clear();
    }


    /**
     * In this example 'ExecutionException' is thrown because of iteration and modification over the map
     *
     * Concurrent modification of HashMap (INSERTION) without ITERATION through it doesn't cause any issues or exceptions
     * It is demonstrated above.
     * Use of CompletableFuture (Consider ForkJoinPool.commonPool().submit()) instead
     * Fail-Fast example with HashSet - Iteration is not allowed if new elements are submitted into the NonThread-Safe Collection
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test(expected = ExecutionException.class) // !!! Exception occurred
    public void testConcurrentOperationFailure() throws ExecutionException, InterruptedException {
//        ForkJoinPool.commonPool().submit(()->{});
        CompletableFuture<Void> futureA = CompletableFuture.supplyAsync(() -> {
            IntStream.range(0, 100).forEach(
                    (i) -> {
                        map.put(i, "O");
                        sleep(1);
                    }
            );
            return null;
        });

        // second traversal
        CompletableFuture<Void> futureB = CompletableFuture.supplyAsync(read(map));
        CompletableFuture.allOf(futureA, futureB).get(); // blocking operator - wait till two completablefuture are finished and return result

        map.clear();
    }


    /**
     * Unlike HashMap:
     * ConcurrentHashMap allows insertion and doesn't throw ConcurrentException
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testConcurrentOperationSuccess() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> futureA = CompletableFuture.supplyAsync(() -> {
            IntStream.range(0, 100).forEach(
                    (i) -> {
                        concurrentHashMap.put(i, "O");
                        sleep(1);
                    }
            );
            return null;
        });


        CompletableFuture<Void> futureB = CompletableFuture.supplyAsync(read(concurrentHashMap));
        CompletableFuture.allOf(futureA, futureB).get();

        concurrentHashMap.clear();
    }

    private Supplier<Void> read(Map<Integer, String> map) {
        // through the map cause ConcurrentModificationException
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
