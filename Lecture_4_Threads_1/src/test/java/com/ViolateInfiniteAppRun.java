package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViolateInfiniteAppRun {

    private volatile static boolean running = true;

    public static class MyThread extends Thread {
        public void run() {
            long counter = 0;
            while (running) {
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


       // Thread.sleep(500);
        running = false;
        System.out.println(Thread.currentThread().getName() + " exited");
        myThread.join();
        System.out.println(myThread.getState());
        // TODO: App is running and cannot exit. Fix it.
        assertEquals(Thread.State.TERMINATED, myThread.getState());
    }
}
