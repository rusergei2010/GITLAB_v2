package com;

import com.mycompany.prepare.utils.Utils;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class LockTest {

    private static int counter = 0;
    private static Lock lock = new ReentrantLock();

    public static void change() {
        lock.lock();
        synchronized (LockTest.class) {
            counter++;
        }
        lock.unlock();
    }

    private static final Object object = new Object();

    public static void changeX() {
        lock.lock();
        synchronized (object) {
            counter++;
        }
        lock.unlock();
    }

    @Test
    public void testSync() {
        new Thread(() -> {
            IntStream.range(0, 1000).forEach((x) -> change());
        }).start();
        new Thread(() -> {
            IntStream.range(0, 1000).forEach((x) -> changeX());
        }).start();


        Utils.sleep(1000);

        // TODO: fix it
        assertEquals(2 * 1000, counter);
    }
}
