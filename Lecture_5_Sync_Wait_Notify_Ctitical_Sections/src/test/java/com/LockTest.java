package com;

import static org.junit.Assert.assertEquals;

import com.mycompany.prepare.utils.Utils;
import java.util.stream.IntStream;
import org.junit.Test;

public class LockTest {

    private static int counter = 0;

    public static void change() {
        synchronized (LockTest.class) {
            counter++;
        }
    }

    private static final Object object = new Object();

    public static void changeX() {
        synchronized (LockTest.class) {
            counter++;
        }
    }

    @Test
    public void testSync() {
        new Thread(() -> IntStream.range(0, 1000).forEach((x) -> change())).start();
        new Thread(() -> IntStream.range(0, 1000).forEach((x) -> changeX())).start();


        Utils.sleep(1000);

        // TODO: fix it
        assertEquals(2 * 1000, counter);
    }
}
