package com;

import org.junit.Test;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

// TODO: fix the test useing only volatile
public class InterruptTest {
    static volatile boolean flag = true;

    private boolean exec() {
        while (flag) {
            int counter = 0;
            counter++;
        }
        return true;
    }

    @Test
    public void testSync() throws InterruptedException {
        AtomicReference<Boolean> ref = new AtomicReference<>();
        ref.set(false);

        new Thread(() -> ref.set(exec())).start();

        new Thread(() -> {
            int counter = 0;
            while (true) {
                counter++;
                if (flag)
                    flag = false;
            }
        }).start();

        sleep(2000);

        assertEquals(true, ref.get());
    }

}
