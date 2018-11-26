package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestThreadStop {

    static class Manageable extends Thread {

        // TODO: think of volatile, interrup() or Atomic
        public static volatile boolean running = true;
        public static volatile String str = "";

        @Override
        public void run() {
            System.out.println(running);
            while (running) {
                try {

                    synchronized (this) {
                        Manageable.str = Manageable.str + "a";
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
        for (int i = 0; i < 100; i ++) {
            if(Manageable.str.equals("aaa")){
                Manageable.running = false;
            }
            Thread.sleep(10);
        }

        System.out.println("Received : " + Manageable.str);
        assertEquals("aaa", Manageable.str);
    }
}
