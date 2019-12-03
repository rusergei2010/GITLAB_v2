package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Admin on 8/6/2018.
 */
public class AppTestThreadState {

    /**
     * Fill in the gaps and insert instructions to make code executable
     *
     * @throws InterruptedException
     */
    @Test
    public void testThreadState() throws InterruptedException {
        // TODO: change instantiation
        Thread thread1 = null;
        Thread thread2 = null;


        Runnable runnable = () -> {

            for (int i = 0; i < 10000000; i++) {
                if (i % 5000 == 0) {
                    System.out.println("");
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        thread1 = new Thread(runnable);
        thread2 = new Thread(runnable);
        assertEquals(thread1.getState(), Thread.State.NEW);
        assertEquals(thread2.getState(), Thread.State.NEW);

        // TODO: fill the gap
        thread1.start();
        // TODO: fill the gap
        thread2.start();
        assertEquals(thread1.getState(), Thread.State.RUNNABLE);
        assertEquals(thread2.getState(), Thread.State.RUNNABLE);

        // Add delay if necessary
        // TODO: fill the gap
        thread1.sleep(1000);
        thread2.sleep(1000);

        // threads should run task to be put on hold
        assertEquals(thread1.getState(), Thread.State.TIMED_WAITING);
        assertEquals(thread2.getState(), Thread.State.TIMED_WAITING);
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
