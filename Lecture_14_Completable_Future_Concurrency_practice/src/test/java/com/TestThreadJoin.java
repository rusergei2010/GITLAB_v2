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
        Thread thread1 = createThread(() -> {
            Long count = 0L;
            for (int i = 0; i < 50; i++) {
                count = i + 1L;
                synchronized (this) {
                    try {
                        // TODO: design wait right way
                        System.out.println("before wait " + i);
                        wait(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
                try {
                    System.out.println("Before sleep");
                    Thread.sleep(5000);
                    System.out.println("After sleep");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });
        //Thread thread2 = thread1;
//            createThread(() -> {
//            synchronized (this) {
//                try {
//                    // TODO: design wait right way
//                    wait(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        thread1.start();
       // thread2.start();

        Thread.sleep(200);
        //thread1.sleep(3000);
        //thread2.sleep(1000);

        // TODO: make TIMED_WAITING
        // TODO: make TIMED_WAITING

        assertEquals(thread1.getState(), Thread.State.TIMED_WAITING);
        //assertEquals(thread2.getState(), Thread.State.TIMED_WAITING);

        // TODO: Wait till both threads are completed or terminated

        thread1.join();
        System.out.println("After join");
        //thread2.join();

        // threads should run task to be put on hold
        assertEquals(thread1.getState(), Thread.State.TERMINATED);
        //assertEquals(thread2.getState(), Thread.State.TERMINATED);

        // TODO: fill in action with Thread to exit loop
        while (!Thread.currentThread().isInterrupted()) {
            return;
        }

        assertTrue(Thread.currentThread().isInterrupted());
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