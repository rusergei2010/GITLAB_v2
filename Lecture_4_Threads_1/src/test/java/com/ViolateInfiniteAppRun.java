package com;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViolateInfiniteAppRun {

    private static AtomicBoolean running = new AtomicBoolean(true);

    public static class MyThread extends Thread {
        public void run() {
            long counter = 0;
            while (running.get()) {
                counter++;
            }

            System.out.println("Counter = " + counter);
            System.out.println(Thread.currentThread().getName() + " exited");
        }
    }


    @Test
    public void testThread() throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.setName("Controlled Thread");
        myThread.start();

        Thread.sleep(100);

        // TODO: Has no effect???
        running.set(false);

        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + " exited");
        // TODO: App is running and cannot exit. Fix it.
        assertEquals(Thread.State.TERMINATED, myThread.getState());
    }
}
