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
               Thread main =Thread.currentThread();
        Thread thread1 = createThread(() -> {

            try {
                synchronized (this) {// TODO: design wait right way
                wait(1500);}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = createThread(() -> {

            try {
                synchronized (this){ // TODO: design wait right way

                    wait(1500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();


        Thread.sleep(1000);

        // TODO: make TIMED_WAITING
        // TODO: make TIMED_WAITING

        assertEquals(Thread.State.TIMED_WAITING,thread1.getState());
        assertEquals(Thread.State.TIMED_WAITING,thread2.getState());

        // TODO: Wait till both threads are completed or terminated

        thread1.join();
        thread2.join();

        // threads should run task to be put on hold
        assertEquals(Thread.State.TERMINATED, thread1.getState());
        assertEquals(Thread.State.TERMINATED, thread2.getState());



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

    private  Thread createThread(Runnable runnable) {
        final Thread thread = new Thread(runnable);
        return thread;
    }



    }