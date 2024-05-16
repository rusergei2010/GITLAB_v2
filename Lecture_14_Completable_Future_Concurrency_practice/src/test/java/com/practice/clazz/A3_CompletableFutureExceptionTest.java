package com.practice.clazz;

import org.junit.Ignore;
import org.junit.Test;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

@Ignore
public class A3_CompletableFutureExceptionTest {


    @Test
    public void testException() throws ExecutionException, InterruptedException {
        String name = null;
        CompletableFuture<String> handle = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException(new Exception("Sergey Exception"));
            }
            return "Sergey";
        }).handle((result, ex) -> result != null ? result : "Cause : " + ex.getCause().toString());

        System.out.println("Result: " + handle.get());
    }


    @Test(expected = Exception.class)
    public void testExecutor() throws ExecutionException, InterruptedException {
        String name = null;
        final CompletableFuture<String> handle = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Sergey";
        }, Executors.newSingleThreadExecutor()).handle((result, ex) -> result != null ? result : "Cause : " + ex.getCause().toString());

        handle.completeExceptionally(new Exception("My exception"));

        System.out.println("Result: " + handle.get());
    }
}