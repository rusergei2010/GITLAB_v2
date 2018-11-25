package com.practice.clazz;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import org.junit.Test;


public class A3_CompletableFutureExceptionTest {


    @Test
    public void testException() throws ExecutionException, InterruptedException {
        String name = null;
        CompletableFuture<String> handle = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException();
            }
            return "Sergey";
        }).handle((result, ex) -> result != null ? result : "Hey : " + ex.getCause().toString());

        System.out.println("Result: " + handle.get());
    }


    @Test
    public void testExecutor() throws ExecutionException, InterruptedException {
        String name = null;
        CompletableFuture<String> handle = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException();
            }
            return "Sergey";
        }, Executors.newSingleThreadExecutor()).handle((result, ex) -> result != null ? result : "Hey : " + ex.getCause().toString());

        System.out.println("Result: " + handle.get());
    }
}
