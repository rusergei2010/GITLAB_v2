package com.epam.test;// Benchmark Шипилев: @link{https://www.youtube.com/watch?v=8pMfUopQ9Es}

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

/**
 * TODO: Fix tests
 */
public class InvokeAllBasicTest {

    private static class CallableTask implements Callable<String> {

        int value = 0;

        public CallableTask(final int value) {
            this.value = value;
        }

        @Override public String call() throws Exception { // return value
            Thread.sleep(1000);
            value = value * value;
            return "" + value; // add "" to make mapping afterwards for fun
        }
    }

    private static class RunnableTask implements Runnable {

        public int value = 0;

        public RunnableTask(final int value) {
            this.value = value;
        }

        @Override public void run() {
            try {
                Thread.sleep(1000); // Let it ibe delayed (REST call as an example then)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            value = value * value;
        }
    }

    @Test
    public void testInvokeAll() throws InterruptedException {

        final ExecutorService service = Executors.newFixedThreadPool(2);

        final Collection<CallableTask> callableTaskCollection = Arrays.asList(
                new CallableTask(10), // 10 * 10
                new CallableTask(100) // 100 * 100
        );

        // expected 100 + 10000 = ...

        final List<Future<String>> futures = service.invokeAll(callableTaskCollection);
        int sum = futures.stream().map(x -> {
                    try {
                        return x.get(); // will not return value till it is ready
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        ).filter(Objects::nonNull)
                .map(Integer::valueOf)
                .mapToInt(Integer::intValue)
                .sum();


        // TODO: fix just value. Investigate the stream mapping/filter  above
        assertEquals(10100 /*What is expected value, and why?*/, sum); // Future will not return value (future.get()) until it is calculated
        putDown(service, 2);
    }

    /**
     * Execute() operation  doesn't return value unlike submit all invoke
     * It demonstrates how difficult to work with Runnable nowadays and Callable<> is preferable
     *
     * TODO: Investigate the code. Pay attention to how many threads there and sleep delays.
     * TODO: Fix the test by changing delays
     * @throws InterruptedException
     */
    @Test
    public void testRunnableTask() throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(2);

        Collection<RunnableTask> callableTaskCollection = Arrays.asList(
                new RunnableTask(10) // 10 * 10
        );

        AtomicReference<Integer> referenceResult = new AtomicReference<>();


        // problematic - gather results for executable tasks.
        // How to return value from the Rannable interface that doesn't return Future or value????
        // We have to imply atomic reference class to solve this issue (really weired solution)
        // Use Future and submit() instead!
        callableTaskCollection.forEach(
                task -> {
                    // find square
                    service.execute(task); // put task into the executor and execute. See the timeout inside of the task
                    try {
                        Thread.sleep(1000); // TODO: increment this
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    referenceResult.set(task.value);
                }
        );

        Thread.sleep(500);// TODO: increment this


        // ### IMPOTRANT ###
        // Without delays we found it to be validated immediately and fail because Runnable had not been executed yet
        assertEquals(100, referenceResult.get().intValue()); // Future will not return value (future.get()) until it is calculated
        putDown(service, 4);
    }

    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        service.shutdown(); // reject new threads
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}