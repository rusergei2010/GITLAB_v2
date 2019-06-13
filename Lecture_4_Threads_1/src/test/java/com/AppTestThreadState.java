package com;

import org.junit.Test;

import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

public class AppTestThreadState {
    @Test
    public void testThreadState() throws InterruptedException {
        ArrayList<Integer> array = new ArrayList<>();
        // TODO: change instantiation
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                array.add(i);
                try {
                    sleep(1400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 10; i >= 0; i--) {
                array.add(i);
                try {
                    sleep(1400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        assertEquals(thread1.getState(), Thread.State.NEW);
        assertEquals(thread2.getState(), Thread.State.NEW);

        thread1.start();
        thread2.start();

        assertEquals(thread1.getState(), Thread.State.RUNNABLE);
        assertEquals(thread2.getState(), Thread.State.RUNNABLE);

        sleep(123);
        assertEquals(thread1.getState(), Thread.State.TIMED_WAITING);
        assertEquals(thread2.getState(), Thread.State.TIMED_WAITING);
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