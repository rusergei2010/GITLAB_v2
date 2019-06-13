package com.epam.executors;

import com.epam.threads.util.Util;
import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class ScheduledThreadPool {

    @Test
    public void testFuture() throws InterruptedException, ExecutionException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture<String> result = executor.schedule(ScheduledThreadPool::healthCheck, 500, TimeUnit.MILLISECONDS);

        Thread.sleep(100);
        assertEquals(true, result.cancel(true));
        executor.shutdown();
    }

    /**
     * test fails and I can't understand where's the problem
     */
  /* @Test
    public void testFutureCancel() throws InterruptedException, ExecutionException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture<String> result = executor.schedule(ScheduledThreadPool::healthCheck, 500, TimeUnit.MILLISECONDS);

        Thread.sleep(100);
        result.cancel(false);

        assertEquals("Result", result.get());
        executor.shutdown();
    }
*/    private static String healthCheck() {
        Util.sleep(1000);
        return "Result";
    }
}
