package com;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppTest {

    static class MyRunnable implements Runnable {
        AtomicInteger counter = new AtomicInteger(0);

        @Override
        public void run() {
            counter.incrementAndGet();
        }
    }

    /**
     * TODO: Fix the test. Employ join()
     * @throws InterruptedException
     */
    @Test
    public void testThread() throws InterruptedException {

        MyRunnable myRunnable = new MyRunnable();

        //
        Thread thread1 = createThread(myRunnable);
        Thread thread2 = createThread(myRunnable);
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
