package com.epam.test;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class CountDownLatchTwoThreads {
    @Test
    public void testAtomicReferenceThread() throws InterruptedException {
        AtomicInteger atomic = new AtomicInteger();
        CountDownLatch latchOne = new CountDownLatch(1);
        CountDownLatch latchTwo = new CountDownLatch(1);

        new Thread(() -> {
            atomic.getAndIncrement();
            latchOne.countDown();
        }).start();

        new Thread(() -> {
            try {
                latchOne.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomic.set(atomic.get() + 1);
            latchTwo.countDown();
        }).start();

        latchTwo.await(1, TimeUnit.SECONDS); // await '0' or proceed
        assertEquals(2, atomic.get());
    }
}
