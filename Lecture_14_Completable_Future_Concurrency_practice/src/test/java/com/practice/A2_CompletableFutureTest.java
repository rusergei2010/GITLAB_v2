package com.practice;

import org.junit.Test;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;


public class A2_CompletableFutureTest {


    @Test
    public void testFuture() throws ExecutionException, InterruptedException {

//        Future<String> future = new FutureTask<String>(() -> {
//            Thread.sleep(2000);
//            System.out.println("Task is executed");
//            return "Future result";
//        });
//        ((FutureTask<String>) future).run();
//        System.out.println(future.get());


        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        new Thread(() -> {
            completableFuture.complete("Task is executed");
        }).start();

        System.out.println("Result: " + completableFuture.get());
    }

    @Test
    public void testSupply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "First Task");
        CompletableFuture<String> result = completableFuture.thenApply(s -> s + "\n" + "Second Task");

        System.out.println(result.get());
    }

    @Test
    public void testAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "First Task");
        CompletableFuture<Void> result = completableFuture.thenAccept(s -> {
            System.out.println(s + "\n" + "Second Task");
        });

        System.out.println(result.get());
    }


    @Test
    public void testCombine() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "First Task. Hello");
        CompletableFuture<String> combined = completableFuture.thenCombineAsync(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2);

        System.out.println(combined.get());
    }

    @Test
    public void testCombineAsync() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Step 1");
            return "First Task. Hello";
        });
        CompletableFuture<String> combined = completableFuture.thenCombineAsync(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2);
        System.out.println("Step 2");
        Thread.sleep(1000);
        System.out.println(combined.get());
    }

    @Test
    public void testComposeAsync() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Step 1");
            return "First Task. Hello";
        });
        CompletableFuture<String> combined = completableFuture.thenComposeAsync((s) -> CompletableFuture.supplyAsync(() -> s + " World"));
        System.out.println("Step 2");
        Thread.sleep(1000);
        System.out.println(combined.get());
    }

    @Test
    public void testComposeAsyncSleep() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            System.out.println("Step 1");
            return "First Task. Hello";
        });
        System.out.println("Step 2");
        Thread.sleep(1000);
        System.out.println("Step 3");
        CompletableFuture<String> combined = completableFuture.thenCompose((s) -> CompletableFuture.supplyAsync(() -> s + " World"));
        System.out.println("Step 4");
        Thread.sleep(1000);
        System.out.println(combined.get());
    }

    @Test
    public void testOfAllOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Print One");
            sleep(ThreadLocalRandom.current().nextInt(4000));
            return "One";
        });
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Print Two");
            sleep(ThreadLocalRandom.current().nextInt(4000));
            return "Two";
        });
        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Print Three");
            sleep(ThreadLocalRandom.current().nextInt(4000));
            return "Three";
        });

        CompletableFuture future = CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3);
        System.out.println(future.isDone());
        System.out.println(future.join());

        System.out.println("Result: ");
        System.out.println(completableFuture1.get());
        System.out.println(completableFuture2.get());
        System.out.println(completableFuture3.get());
    }

    @Test
    public void testAnyOf() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Print One");
            sleep(ThreadLocalRandom.current().nextInt(4000));
            return "One";
        });
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Print Two");
            sleep(ThreadLocalRandom.current().nextInt(4000));
            return "Two";
        });
        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Print Three");
            sleep(ThreadLocalRandom.current().nextInt(4000));
            return "Three";
        });

        CompletableFuture future = CompletableFuture.anyOf(completableFuture1, completableFuture2, completableFuture3);
        System.out.println(future.isDone());
        System.out.println(future.join());

        System.out.println("Result: ");
        System.out.println(completableFuture1.get());
        System.out.println(completableFuture2.get());
        System.out.println(completableFuture3.get());
    }

    private static void sleep(int delay) {
        try {
            Thread.currentThread().sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
