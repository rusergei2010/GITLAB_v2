package com.epam.test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * TODO: fix the test (hint - countDown() is missing)
 */
public class CountDownLatchTest {

    public static class Task implements Runnable{
        private final CountDownLatch latchStop;
        private final CountDownLatch latchStart;

        public Task(CountDownLatch latchStop, CountDownLatch latchStart) {
            this.latchStart = latchStart;
            this.latchStop = latchStop;
        }

        public void run() {
            System.out.println("Task is started");
            try {
                latchStart.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executeJob();
            System.out.println("Job is Executed");
            latchStop.countDown();
        }

        private void executeJob() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testCountDownLatch() throws InterruptedException {

        CountDownLatch latchStart = new CountDownLatch(1);
        CountDownLatch latchStop = new CountDownLatch(3);

        ExecutorService service = Executors.newFixedThreadPool(3);

        IntStream.range(0,3).forEach((x) -> {
            service.submit(new Task(latchStop, latchStart));
        });
        Thread.sleep(100);

        latchStart.countDown();
        latchStop.await(1000, TimeUnit.MILLISECONDS);

        assertEquals(0, latchStop.getCount());
        Thread.sleep(100);
        System.out.println("Exit Main");

        shutdown(service);
    }

    private static void shutdown(ExecutorService service) throws InterruptedException {
        service.shutdown();
        if(service.awaitTermination(5000,TimeUnit.MILLISECONDS)) {
            service.shutdownNow();
        }
    }

}
