package com;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

public class TestThreadStop {

    static class Manageable extends Thread {

        // TODO: think of volatile, interrupt() or Atomic
        public static  AtomicBoolean running = new AtomicBoolean(true);
        public static  AtomicReference<String> str = new AtomicReference<>("");

        @Override
        public void run() {

            while (running.get()) {
                try {
                    str.set(str.get() + "a");
                    synchronized (this) {
                        wait(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testThreadState() throws InterruptedException {

        Manageable thread = new Manageable();
        assertEquals(thread.getState(), Thread.State.NEW);
        thread.start();
        assertEquals(thread.getState(), Thread.State.RUNNABLE);

        //TODO: Employ TestThreadStop.Manageable.running = false inside of loop and stop thread when "aaa" is built
        for (int i = 0; i < 100; i++) {
            if (Manageable.str.get().equals("aaa")) {
                Manageable.running.set(false);
            }
            TimeUnit.MILLISECONDS.sleep(3);
        }

        System.out.println("Received : " + Manageable.str);
        assertEquals("aaa", Manageable.str.get());
    }
}
