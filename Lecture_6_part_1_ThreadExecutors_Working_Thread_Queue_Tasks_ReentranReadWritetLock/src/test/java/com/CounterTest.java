package com;

import org.junit.Test;
import com.util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.assertEquals;

public class CounterTest {
    static class Counter {
        private int counter = 0;

        // External lock
        Lock lock = new ReentrantLock();

        public void inc() throws InterruptedException {
            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    Util.sleep (500);
                    counter++;
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Test
    public void testCounter() throws InterruptedException {

        Counter counter = new Counter();
        ExecutorService service = Executors.newFixedThreadPool(2);

        service.submit(() -> {
            try {
                counter.inc();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.submit(() -> {
            try {
                counter.inc();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.awaitTermination(2000, TimeUnit.MILLISECONDS);
        service.shutdown();

        // TODO: fix the test by changing timings
        assertEquals(2, counter.counter);
    }
}