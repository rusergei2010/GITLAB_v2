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

        Runnable job = () -> {
            try {
                    Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread1 = createThread(job);
        Thread thread2 = createThread(job);

        assertEquals(thread1.getState(), Thread.State.NEW);
        assertEquals(thread2.getState(), Thread.State.NEW);


        // TODO: fill the gap
        // TODO: fill the gap
        thread1.start();
        thread2.start();

        assertEquals(thread1.getState(), Thread.State.RUNNABLE);
        assertEquals(thread2.getState(), Thread.State.RUNNABLE);

        // Add delay if necessary
        // TODO: fill the gap
        Thread.sleep(200);
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
