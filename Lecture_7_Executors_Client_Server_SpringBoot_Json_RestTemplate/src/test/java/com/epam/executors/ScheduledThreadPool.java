package com.epam.executors;

import com.epam.threads.util.Util;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

// TODO: Fix the Future (cancellation)
public class ScheduledThreadPool {

    @Test
    public void testFuture() throws InterruptedException, ExecutionException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture<String> result = executor.schedule(ScheduledThreadPool::healthCheck, 500, TimeUnit.MILLISECONDS);

        Thread.sleep(10);

        result.cancel(true);
        assertEquals(true, result.isCancelled());
        executor.shutdown();
    }

    @Test
    public void testFutureCancel() throws InterruptedException, ExecutionException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture<String> result = executor.schedule(ScheduledThreadPool::healthCheck, 500, TimeUnit.MILLISECONDS);

        Thread.sleep(10);

        assertEquals("Result", result.get());
        result.cancel(false);
        executor.shutdown();
    }

    private static String healthCheck() {
        Util.sleep(1000);
        return "Result";
    }

}
