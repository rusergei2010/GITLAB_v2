package com;

import java.util.concurrent.TimeUnit;
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
        Thread thread1 = createThread(() -> {
            int i = 0;
            while (true){
                i++;
                if (i==1000){
                    try {
                        Thread.currentThread().sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread thread2 = createThread(() -> {
            int i = 0;
            while (true){
                i++;
                if (i==1000){
                    try {
                        Thread.currentThread().sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        assertEquals(Thread.State.NEW, thread1.getState());
        assertEquals(Thread.State.NEW, thread2.getState());

        // TODO: fill the gap
        // TODO: fill the gap

        thread1.start();
        thread2.start();

        assertEquals(Thread.State.RUNNABLE, thread1.getState());
        assertEquals(Thread.State.RUNNABLE, thread2.getState());

        // Add delay if necessary
        // TODO: fill the gap

        thread1.sleep(500);
        thread2.sleep(500);

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
