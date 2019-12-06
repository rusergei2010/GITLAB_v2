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

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        assertEquals(thread1.getState(), Thread.State.NEW);
        assertEquals(thread2.getState(), Thread.State.NEW);

        thread1.start();
        thread2.start();

        assertEquals(thread1.getState(), Thread.State.RUNNABLE);
        assertEquals(thread2.getState(), Thread.State.RUNNABLE);

        Thread.sleep(1000);
        Thread.sleep(1000);

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
