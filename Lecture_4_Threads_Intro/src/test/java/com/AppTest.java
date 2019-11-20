package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppTest {

    static class MyRunnable implements Runnable {
        int counter = 0;

        @Override
        public void run() {
            counter++;
        }
    }

    /**
     * TODO: Fix the test. Employ join() and possible introduce Thread.sleep()
     * @throws InterruptedException
     */
    @Test
    public void testThread() throws InterruptedException {

        MyRunnable myRunnable = new MyRunnable();

        //
        Thread thread1 = createThread(myRunnable);
        Thread thread2 = createThread(myRunnable);
        thread1.start();
        thread2.start();

        assertEquals(2, 2);
    }


    Thread createThread(MyRunnable myRunnable) {
        return new Thread(myRunnable);
    }
}
