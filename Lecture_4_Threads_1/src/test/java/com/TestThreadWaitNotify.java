package com;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

/**
 * Explorer wait/notify mechanism for threads
 */
public class TestThreadWaitNotify {

    AtomicInteger counter = new AtomicInteger(0);

    /**
     * Fill in the gaps and insert instructions to make code executable
     *
     * @throws InterruptedException
     */
    @Test
    public void testThread() throws InterruptedException {
        Thread thread1 = createThread(() -> {
            try {
                synchronized (counter) {
                    counter.wait();
                    counter.incrementAndGet();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = createThread(() -> {
            try {
                synchronized (counter) {
                    counter.wait();
                    counter.incrementAndGet();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        thread1.start();
        thread2.start();

//
        Thread.sleep(100);
//
//        // ensure WAITING
//        // ensure WAITING
        System.out.println(thread1.getState());
        System.out.println(thread2.getState());
        assertEquals(thread1.getState(), Thread.State.WAITING);
        assertEquals(thread2.getState(), Thread.State.WAITING);
//
//        // TODO: notify thread
//        // TODO: notify thread
        synchronized (counter) {
            counter.notifyAll();
        }


        System.out.println(thread1.getState());
        System.out.println(thread2.getState());
        // delay
        Thread.sleep(1000);
        assertEquals(counter.get(), 2);

        //thread1.join();
        //thread2.join();
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