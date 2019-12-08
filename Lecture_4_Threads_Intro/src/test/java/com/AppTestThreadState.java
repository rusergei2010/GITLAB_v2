package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Admin on 8/6/2018.
 */
public class AppTestThreadState {

    /**
     * Fill in the gaps and insert instructions to make code executable
     * @throws InterruptedException
     */
    @Test
    public void testThreadState() throws InterruptedException {
        // TODO: change instantiation
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().join(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread1 = createThread(runnable);
        Thread thread2 = createThread(runnable);

        assertEquals(Thread.State.NEW, thread1.getState());
        assertEquals(Thread.State.NEW, thread1.getState());

        // TODO: fill the gap
        thread1.start();
        // TODO: fill the gap
        thread2.start();

        assertEquals(Thread.State.RUNNABLE, thread1.getState());
        assertEquals(Thread.State.RUNNABLE, thread2.getState());

        // Add delay if necessary
        // TODO: fill the gap

        Thread.sleep(10);

        // threads should run task to be put on hold
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
