package home;

import org.junit.Test;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static org.junit.Assert.assertEquals;


public class Test1 {


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

    @Test
    public void deferredCompleteResult() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        new Thread(() -> {
            sleep(1000);
            completableFuture.complete("Result"); // TODO: Fix it in the string
        }).start();

        assertEquals("Result", completableFuture.get());
    }

    @Test(expected = RuntimeException.class)
    public void deferredCompleteResultExceptionally() throws Throwable {
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
        // TODO: Complete Exceptionally with RuntimeException
        // TODO: Code the exception in this line
        completableFuture.completeExceptionally(new RuntimeException());
        try {
            System.err.println("Print: " + completableFuture.get());
        } catch (Throwable ex) {
            throw ex.getCause();
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
