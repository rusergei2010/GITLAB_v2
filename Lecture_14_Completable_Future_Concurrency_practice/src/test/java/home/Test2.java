package home;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

import static org.junit.Assert.assertEquals;


public class Test2 {

    @Test
    public void testSupply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "First Task");
        // TODO: Use lambda: (s -> s + " Second Task") with thenApply() termination operation for the CompletableFuture
        CompletableFuture<String> result = completableFuture.supplyAsync(() -> "First Task")
                .thenApply(s -> s + " / Second Task");

        assertEquals("First Task / Second Task", result.get());
    }

    @Test
    public void testAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "First Task");
        AtomicReference<String> reference = new AtomicReference<>();
        CompletableFuture<Void> result = completableFuture.thenAccept(s -> {
            reference.set("Hey!");
        });

        // TODO: Put the blocking operation here to wait for the CompletableFuture's result being received
        assertEquals(reference.get(), "Hey!");
    }


    @Test
    public void testCombine() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "First Task. Hello");

        BiFunction<String, String, String> func = String::concat; // TODO: Fix the return value in BiFunction

        CompletableFuture<String> combined = completableFuture.thenCombineAsync(CompletableFuture.supplyAsync(() -> " World"), func);

        assertEquals("First Task. Hello World", combined.get());
    }


    @Test
    public void testComposeAsyncSleep() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            sleep(5000); // TODO: Fix the timeout here
            return "First Task. Hello";
        });
        Thread.sleep(1000);
        completableFuture.complete("NEO"); // TODO: Pay attention to this line and its role
        CompletableFuture<String> combined = completableFuture.thenCompose((s) -> CompletableFuture.supplyAsync(() -> s + " World"));
        Thread.sleep(1000);
        assertEquals("NEO World", combined.get());
    }

    // TODO: fix with timeouts in line 1 or line 3
    @Test
    public void testOfAnyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            sleep(ThreadLocalRandom.current().nextInt(5000)); // line 1
            return "One";
        });
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            sleep(ThreadLocalRandom.current().nextInt(2000)); // line 3
            return "Two";
        });
        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(() -> {
            sleep(ThreadLocalRandom.current().nextInt(100)); // line 3
            return "Three";
        });

        CompletableFuture future = CompletableFuture.anyOf(completableFuture1, completableFuture2, completableFuture3);
        assertEquals("Three", future.get());
    }

    private static void sleep(int delay) {
        try {
            Thread.currentThread().sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
