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
    public  void  testThread() throws InterruptedException {
        Thread thread1 = createThread();
        Thread thread2 = createThread();
        thread1.start();
        thread2.start();

        Thread.sleep(100);

        // TODO: make TIMED_WAITING
        // TODO: make TIMED_WAITING
            thread1.sleep(1000);
            thread2.sleep(1000);
        assertEquals(thread1.getState(), Thread.State.TIMED_WAITING);
        assertEquals(thread2.getState(), Thread.State.TIMED_WAITING);

        // TODO: Wait till both threads are completed or terminated
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
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return thread;
    }

    private Thread createThread(Runnable runnable) {
        final Thread thread = new Thread(runnable);
        return thread;
    }

}