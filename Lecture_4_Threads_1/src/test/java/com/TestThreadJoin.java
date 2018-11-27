package com;

import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestThreadJoin {

    private Object object1 = new Object();
    private Object object2 = new Object();

    /**
     * Fill in the gaps and insert instructions to make code executable
     */
    @Test
    public synchronized void testThread() throws InterruptedException {
        Thread thread1 = createThread(() -> {

            // TODO: design wait right way
            synchronized (object1) {
                try {
                    object1.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        Thread thread2 = createThread(() -> {
            synchronized (object2) {
                try {
                    // TODO: design wait right way
                    object2.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
        sleep(100);

        // TODO: make TIMED_WAITING
        // TODO: make TIMED_WAITING

        assertEquals(thread1.getState(), Thread.State.TIMED_WAITING);
        assertEquals(thread2.getState(), Thread.State.TIMED_WAITING);

        // TODO: Wait till both threads are completed or terminated

        notifyAll();
        thread1.join();
        thread2.join();
        // threads should run task to be put on hold
        assertEquals(thread1.getState(), Thread.State.TERMINATED);
        assertEquals(thread2.getState(), Thread.State.TERMINATED);

        // TODO: fill in action with Thread to exit loop
        while (!Thread.currentThread().isInterrupted()) {
            Thread.currentThread().interrupt();
        }

        assertTrue(Thread.currentThread().isInterrupted());
        assertEquals(Thread.currentThread().getState(), Thread.State.RUNNABLE);
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