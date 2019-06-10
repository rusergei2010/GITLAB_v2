package com;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestThreadStop {

    static class Manageable extends Thread {

        // TODO: think of volatile, interrupt() or Atomic
        public static AtomicBoolean running = new AtomicBoolean(true);
        public static String str = "";

        @Override
        public void run() {
            while (running.get()) {
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
        assertEquals(Thread.State.NEW, thread.getState());
        thread.start();
//        assertEquals(Thread.State.RUNNABLE, thread.getState());

        //TODO: Employ TestThreadStop.Manageable.running = false inside of loop and stop thread when "aaa" is built
        for (int i = 0; i < 100; i ++) {
            synchronized (thread){
                if(Manageable.str.length() >= 3){
                    thread.running.set(false);
                    break;
                }
                else{
                    Thread.sleep(10);
                }
            }
        }

        System.out.println("Received : " + Manageable.str);
        assertEquals("aaa", Manageable.str);
    }
}
