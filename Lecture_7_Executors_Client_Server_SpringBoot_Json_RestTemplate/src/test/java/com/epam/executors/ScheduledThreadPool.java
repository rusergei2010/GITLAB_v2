package com.epam.executors;

import static org.junit.Assert.assertEquals;

import com.epam.threads.util.Util;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

// TODO: Fix the Future (cancellation)
public class ScheduledThreadPool {

    @Test
    public void testFuture() throws InterruptedException, ExecutionException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture<String> result = executor.schedule(ScheduledThreadPool::healthCheck, 500, TimeUnit.MILLISECONDS);

        result.cancel(false);
        Thread.sleep(100);

        assertEquals(true, result.isCancelled());
        executor.shutdown();
    }

    @Test
    public void testFutureCancel() throws InterruptedException, ExecutionException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture<String> result = executor.schedule(ScheduledThreadPool::healthCheck, 500, TimeUnit.MILLISECONDS);

        Thread.sleep(100);

      assertEquals("Result", result.get());
      result.cancel(false);
      executor.shutdown();
    }

    private static String healthCheck() {
        Util.sleep(1000);
        return "Result";
    }

}
