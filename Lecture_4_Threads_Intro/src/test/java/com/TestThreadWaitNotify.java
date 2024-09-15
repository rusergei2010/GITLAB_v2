package com;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

/**
 * Explorer wait/notify mechanism for threads
 */
public class TestThreadWaitNotify {

    AtomicInteger counter = new AtomicInteger(0);

    /**
     * Fill in the gaps and insert instructions to make code executable
     *
     * @throws InterruptedException
     */
    @Test
    public void testThread() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (counter) {
                        counter.wait();
                    }
                    counter.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread1 = createThread(runnable);
        Thread thread2 = createThread(runnable);

        thread1.start();
        thread2.start();

        Thread.sleep(100);

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
        Thread.sleep(1000);
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