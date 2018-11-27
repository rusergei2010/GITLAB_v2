package com;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;


public class TestThreadWaitNotify {

    final AtomicInteger counter = new AtomicInteger(0);


    /**
     * Fill in the gaps and insert instructions to make code executable
     */
    @Test
    public void testThread() throws InterruptedException {
        Thread thread1 = createThread(() -> {
            synchronized (counter) {
                try {
                    counter.wait();
                    counter.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = createThread(() -> {
            synchronized (counter) {
                try {
                    counter.wait();
                    counter.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();

        sleep(100);

        // ensure WAITING
        // ensure WAITING
        assertEquals(thread1.getState(), Thread.State.WAITING);
        assertEquals(thread2.getState(), Thread.State.WAITING);

        // TODO: notify thread
        // TODO: notify thread
        synchronized (counter) {
            counter.notifyAll();
        }
        // delay
        sleep(1000);

        assertEquals(counter.get(), 2);
    }

    private Thread createThread() {
        final Thread thread = new Thread();
        return thread;
    }

    private Thread createThread(Runnable runnable) {
        final Thread thread = new Thread(runnable);
        return thread;
    }

}