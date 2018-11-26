package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Admin on 8/6/2018.
 */
public class ATestThreadState {

    /**
     * Fill in the gaps and insert instructions to make code executable
     *
     * @throws InterruptedException
     */
    @Test
    public void testThreadState() throws InterruptedException {
        // TODO: change instantiation
        Thread thread1 = createThread(() -> {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = createThread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        assertEquals(Thread.State.NEW, thread1.getState());
        assertEquals(Thread.State.NEW, thread2.getState());

        thread1.start();
        thread2.start();

        assertEquals(Thread.State.RUNNABLE, thread1.getState());
        assertEquals(Thread.State.RUNNABLE, thread2.getState());

        Thread.sleep(100);

        assertEquals(Thread.State.TIMED_WAITING, thread1.getState());
        assertEquals(Thread.State.TIMED_WAITING, thread2.getState());
        assertEquals(Thread.State.RUNNABLE, Thread.currentThread().getState());
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
