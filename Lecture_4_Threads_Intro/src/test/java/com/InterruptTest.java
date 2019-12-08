package com;

import org.junit.Test;
import prepare.util.Util;

import static org.junit.Assert.assertEquals;

/**
 * TODO: Fix the test case
 * Read the code and corect it a better way
 */
public class InterruptTest {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread: " + Thread.currentThread().getName() + " started");

            while(isInterrupted())
                Util.threadSleep(100);

            System.out.println("MyThread: " + Thread.currentThread().getName() + " completed");
        }
    }


    @Test
    public void testInterrupt(){
        final Thread thread = new MyThread();
        thread.start();
        thread.interrupt();

        try {
            thread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(Thread.State.TERMINATED, thread.getState());
        // outdated version
//        thread.suspend();
//        thread.resume();
    }
}
