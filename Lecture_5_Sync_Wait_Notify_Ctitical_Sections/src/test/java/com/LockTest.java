package com;

import org.junit.Test;

import java.util.stream.IntStream;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

public class LockTest {

    private static int counter = 0;
    private static final Object object = new Object();

    public static void change() {
        synchronized (object) {
            counter++;
        }
    }

    public static void changeX() {
        synchronized (object) {
            counter++;
        }
    }

    @Test
    public void testSync() throws InterruptedException {
        new Thread(() -> {
            IntStream.range(0, 1000).forEach((x) -> change());
        }).start();
        new Thread(() -> {
            IntStream.range(0, 1000).forEach((x) -> changeX());
        }).start();


        sleep(1000);

        // TODO: fix it
        assertEquals(2 * 1000, counter);
    }
}
