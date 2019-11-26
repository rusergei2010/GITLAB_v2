package com.epam.test;// Benchmark Шипилев: @link{https://www.youtube.com/watch?v=8pMfUopQ9Es}


import com.epam.test.util.Util;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

// TODO: Fix one of the timeouts in Anchor 1 or Anchor 2
public class SemaphoreTest {

    private static class Resource {

        private Semaphore semaphore;
        volatile AtomicInteger connections = new AtomicInteger(0);

        public Resource(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public void connect(String uri) {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(200, TimeUnit.MILLISECONDS); // Anchor 1
                if (permit) {
                    System.out.println("Connection established to " + uri);
                    Util.threadSleep(1); // Anchor 2
                    connections.addAndGet(1);
                } else {
                    System.out.println("Connection rejected");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (permit)
                    semaphore.release();
            }
        }
    }
    @Test
    public void testSemaphore() throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(5);

        Semaphore semaphore = new Semaphore(10);
        SemaphoreTest.Resource resource = new SemaphoreTest.Resource(semaphore);

        IntStream.range(0, 10).forEach((port) -> {
                    service.submit(() -> {
                        resource.connect("localhost, " + (100 + port));
                    });
                }
        );

        Thread.sleep(4000);
        assertEquals(10, resource.connections.get());

        putDown(service, 4);
    }

    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        service.shutdown(); // reject new threads
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}