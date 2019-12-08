package com;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

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
//        Thread thread_1 = null;
//        Thread thread_2 = null;

        Thread thread_1 = createThread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread_2 = createThread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        assertEquals(thread_1.getState(), Thread.State.NEW);
        assertEquals(thread_2.getState(), Thread.State.NEW);


        // TODO: fill the gap
        // TODO: fill the gap

        thread_1.start();
        thread_2.start();

        assertEquals(thread_1.getState(), Thread.State.RUNNABLE);
        assertEquals(thread_2.getState(), Thread.State.RUNNABLE);

        // Add delay if necessary
        // TODO: fill the gap

        // threads should run task to be put on hold

        TimeUnit.SECONDS.sleep(4);

        assertEquals(thread_1.getState(), Thread.State.TIMED_WAITING);
        assertEquals(thread_2.getState(), Thread.State.TIMED_WAITING);
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
