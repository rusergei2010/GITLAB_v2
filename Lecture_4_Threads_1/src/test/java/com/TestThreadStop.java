package com;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

public class TestThreadStop {

    static class Manageable extends Thread {

        // TODO: think of volatile, interrupt() or Atomic
        public static volatile boolean running = true;
        public static AtomicReference<String> str = new AtomicReference<>("");

        @Override
        public void run() {

            while (running) {
                str.weakCompareAndSet(str.get(), str.get() + "a");
                try {
                    synchronized (this) {
                        wait(1000);
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
            synchronized (Manageable.class) {
                if (Manageable.str.get().length() == 3) {
                    Manageable.running= false;
                    break;
                }
                else{
                    Thread.sleep(100);
                }
            }
        }

        thread.join();

        System.out.println("Received : " + Manageable.str.get());
        assertEquals("aaa", Manageable.str.get());
    }
}
