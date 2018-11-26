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
        Thread thread1 = createThread(() -> {
            try {
                // TODO: design wait right way
                Thread.sleep(1000);

//                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = createThread(() -> {
            try {
                // TODO: design wait right way
                Thread.sleep(1000);
//                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        Thread.sleep(200);

        // TODO: make TIMED_WAITING
        // TODO: make TIMED_WAITING

        System.out.println(thread1.getState());
        System.out.println(thread2.getState());
        System.out.println(Thread.currentThread().getState());
        assertEquals(thread1.getState(), Thread.State.TIMED_WAITING);
        assertEquals(thread2.getState(), Thread.State.TIMED_WAITING);

        // TODO: Wait till both threads are completed or terminated

        thread1.join();
        thread2.join();

        // threads should run task to be put on hold
        assertEquals(thread1.getState(), Thread.State.TERMINATED);
        assertEquals(thread2.getState(), Thread.State.TERMINATED);

        System.out.println("Done!");
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