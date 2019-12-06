package com.practice.clazz;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


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


    @Test
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

        System.out.println("Result: " + handle.get());
    }
}
