package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Good example of real value/usage of volatile
 * See console log to observe reaction
 */
public class TestVolatileTest extends Thread {
    // TODO: volatile? What is its function?
    private volatile static int SIGNAL = 0;
    public static final int WAIT = 5;

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            int local_value = SIGNAL;
            while (local_value < WAIT) {
                if (local_value != SIGNAL) {
                    System.out.println("Got Change for SIGNAL : " + SIGNAL);
                    local_value = SIGNAL;
                }
            }
        }
    }

    static class ChangeMaker extends Thread {
        @Override
        public void run() {

            int local_value = SIGNAL;
            while (SIGNAL < WAIT) {
                System.out.println(String.format("Incrementing SIGNAL to : %d", local_value + 1));
                SIGNAL = ++local_value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * TODO: use volatile and explain why it fixes the problem?
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        Thread listener = new ChangeListener();
        Thread maker = new ChangeMaker();

        listener.start();
        maker.start();

        listener.join(1000 * (WAIT + 1)); // wait till listener thread is finished or 5 secs

        assertEquals(5, SIGNAL);
        assertEquals(State.TERMINATED, listener.getState());
    }
}