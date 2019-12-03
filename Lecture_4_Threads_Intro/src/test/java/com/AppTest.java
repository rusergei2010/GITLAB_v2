package com;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class AppTest {

    static class MyRunnable implements Runnable {
        AtomicInteger counter = new AtomicInteger();

        @Override
        public void run() {
            counter.incrementAndGet();
        }
    }

    /**
     * TODO: Fix the test. Employ join() and possible introduce Thread.sleep()
     * @throws InterruptedException
     */
    @Test
    public void testThread() throws InterruptedException {

        MyRunnable myRunnable = new MyRunnable();

        //
        Thread thread1 = new Thread(myRunnable);
        Thread thread2 = new Thread(myRunnable);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(2, myRunnable.counter.get());
    }


    Thread createThread(MyRunnable myRunnable) {
        return new Thread(myRunnable);
    }
}
