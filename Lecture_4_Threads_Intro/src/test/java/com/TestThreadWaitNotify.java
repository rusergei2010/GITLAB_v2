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
        Thread thread1 = createThread(() -> {
            try {
                synchronized (counter) {
                    counter.incrementAndGet();
                    counter.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = createThread(() -> {
            try {
                synchronized (counter) {
                    counter.incrementAndGet();
                    counter.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        // ensure WAITING
        // ensure WAITING
        Thread.sleep(123);
        assertEquals(Thread.State.WAITING, thread1.getState());
        assertEquals(Thread.State.WAITING, thread2.getState());

        // TODO: notify thread
        // TODO: notify thread

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
