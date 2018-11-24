package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Admin on 8/6/2018.
 */
public class ATestThreadState {

    /**
     * Fill in the gaps and insert instructions to make code executable
     * @throws InterruptedException
     */
    @Test
    public void testThreadState() throws InterruptedException {
        // TODO: change instantiation
        Thread thread1 = createThread(()-> {
                Long count = 0L;
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                count = count + i+1;
                synchronized (this)
                {
                    try {
                        wait(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread thread2 = thread1;

        assertEquals(thread1.getState(), Thread.State.NEW);
        assertEquals(thread2.getState(), Thread.State.NEW);


        // TODO: fill the gap
        // TODO: fill the gap
        thread1.start();

        assertEquals(thread1.getState(), Thread.State.RUNNABLE);
        assertEquals(thread2.getState(), Thread.State.RUNNABLE);

        // Add delay if necessary
        // TODO: fill the gap
        thread1.sleep(3000);


        // threads should run task to be put on hold
        assertEquals(Thread.State.TIMED_WAITING, thread1.getState());
        assertEquals(Thread.State.TIMED_WAITING, thread2.getState());
        assertEquals(Thread.State.RUNNABLE, Thread.currentThread().getState());
    }

    private Thread createThread() {
        final Thread thread = new Thread();
        return thread;
    }

    private Thread createThread(Runnable runnable) throws InterruptedException{
        final Thread thread = new Thread(runnable);
        return thread;
    }

}
