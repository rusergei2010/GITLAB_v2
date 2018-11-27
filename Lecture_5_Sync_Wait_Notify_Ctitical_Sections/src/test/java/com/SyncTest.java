package com;

import org.junit.Test;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

// Mutex, critical section in the static method, acquire lock in the same thread (Mutex knows who locked it)
// Intrinsic lock is associated with the Class instance (static context)
// Extrinsic lock is associated with a particular dynamic object (not the Class instance)
public class SyncTest {

    private static int counter = 0;

    Lock lock = new ReentrantLock();

    public void change() {

        lock.lock();
        try {
            try {
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            counter++;
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void testSync() throws InterruptedException {
        new Thread(() -> {
            change();
        }).start();
        new Thread(() -> {
            change();
        }).start();
        sleep(1000);
        // TODO: fix it with use of 'if(tryLock())' for heavy calculations (~sleep(1000))
        if (!lock.tryLock()) {
            sleep(500);
        }
        assertEquals(1, counter);
    }
}
