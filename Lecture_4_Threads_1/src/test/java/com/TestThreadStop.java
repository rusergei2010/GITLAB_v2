package com;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestThreadStop {

    static class Manageable extends Thread {

        // TODO: think of volatile, interrup() or Atomic
        public volatile static boolean running = true;
        public volatile static String str = "";

        @Override
        public void run() {

            while (running) {
                try {
                    str = str + "a";
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
        while (true) {
            if (Manageable.str.equals("aaa")) {
                Manageable.running=false;
             break;
            }
            Thread.sleep(50);
        }
        System.out.println("Received : " + Manageable.str);
        assertEquals("aaa", Manageable.str);
    }
}
