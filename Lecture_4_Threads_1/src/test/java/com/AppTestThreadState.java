package com;

import org.junit.Test;

import static java.lang.Thread.sleep;
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
        Runnable MyRunnable = () -> {
            System.out.println(Thread.currentThread().getName() + " performing");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // TODO: change instantiation
        Thread thread1 = createThread(MyRunnable);
        Thread thread2 = createThread(MyRunnable);

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
        // threads should run task to be put on hold
        Thread.sleep(200);
        assertEquals(thread1.getState(), Thread.State.TIMED_WAITING);
        assertEquals(thread2.getState(), Thread.State.TIMED_WAITING);
        System.out.println(thread1.getState());
        //Thread.sleep(3000);
        assertEquals(Thread.currentThread().getState(), Thread.State.RUNNABLE);
        System.out.println(thread1.getState());

        thread1.join();
        thread2.join();
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
