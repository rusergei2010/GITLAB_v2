package com;

import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestThreadStop {

    static class Manageable extends Thread {

        // TODO: think of volatile, interrup() or Atomic
        public static volatile boolean running = true;
        public static AtomicReference<String> str = new AtomicReference<>("");

        @Override
        public void run() {

            while (running) {
                try {
                    while (!str.weakCompareAndSet(str.get(), str + "a"));
                    //str = str + "a";
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
                Manageable.running = false;
                break;
            } else {
                Thread.sleep(100);
            }
        }

        System.out.println("Received : " + Manageable.str);
        assertEquals("aaa", Manageable.str.get());
    }
}
