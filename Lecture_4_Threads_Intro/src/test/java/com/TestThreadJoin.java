package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestThreadJoin {
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
                    // TODO: design wait right way
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread1 = createThread(runnable);
        Thread thread2 = createThread(runnable);

        thread1.start();
        thread2.start();

        thread1.join(500);
        thread2.join(500);


        // TODO: make TIMED_WAITING
        // TODO: make TIMED_WAITING

        assertEquals(thread1.getState(), Thread.State.TIMED_WAITING);
        assertEquals(thread2.getState(), Thread.State.TIMED_WAITING);
        Thread.sleep(1000);
        // TODO: Wait till both threads are completed or terminated
        // threads should run task to be put on hold
        assertEquals(thread1.getState(), Thread.State.TERMINATED);
        assertEquals(thread2.getState(), Thread.State.TERMINATED);

        // TODO: fill in action with Thread to exit loop
        while (Thread.currentThread().isInterrupted()) {
        }

        Thread.currentThread().interrupt();
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