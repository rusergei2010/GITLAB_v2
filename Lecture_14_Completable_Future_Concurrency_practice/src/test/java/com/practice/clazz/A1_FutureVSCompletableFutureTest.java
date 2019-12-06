package com.practice.clazz;

import org.junit.Test;

import java.util.concurrent.*;


public class A1_FutureVSCompletableFutureTest {

    /**
     * FutureTask is a simplest Future that a single Thread inside and Stages to manage it like a basic Future
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testFuture() throws ExecutionException, InterruptedException {
        Future<String> future = new FutureTask<String>(() -> {
            Thread.sleep(2000);
            System.out.println("Task is executed");
            return "Future result";
        });
        ((FutureTask<String>) future).run();
        System.out.println(future.get());
    }

    @Test(expected = CancellationException.class)
    public void testFutureCancel() throws ExecutionException, InterruptedException {
        Future<String> future = new FutureTask<String>(() -> {
            Thread.sleep(2000);
            System.out.println("Task is executed");
            return "Future result";
        });
        future.cancel(true);
        System.out.println(future.get());
    }

    /**
     * Completable Future start here
     * CompletableFuture means that it can be explicitly completed
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void deferredCompleteResult() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        new Thread(() -> {
            sleep(1000);
            completableFuture.complete("Result is ready");
        }).start();

        System.out.println("Print: " + completableFuture.get());
    }

    @Test
    public void deferredCompleteResultExceptionally() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                completableFuture.complete("Result is ready");
            } catch (Throwable th) {
                System.out.println("Exception: " + th.getMessage());
            }
        }).start();
        sleep(100);
        CompletableFuture<String> completableFutureEx = new CompletableFuture<>();
        completableFutureEx.completeExceptionally(new RuntimeException("Complete with exception"));
        try {
            System.err.println("Print: " + completableFuture.get());
        } catch (Throwable ex) {
            System.out.println("Cause: " + ex.getCause());
            System.out.println("Exception: " + ex.getCause().getClass());
            System.out.println("Top class Exception: " + ex.getClass());
            ex.printStackTrace();
        }
    }

    private static void sleep(int delay) {
        try {
            Thread.currentThread().sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
