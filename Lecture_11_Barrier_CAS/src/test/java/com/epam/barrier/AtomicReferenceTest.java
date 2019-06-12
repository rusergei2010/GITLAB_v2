package com.epam.barrier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AtomicReferenceTest {

    @Test
    public void testAtomicReference() {
        AtomicReference reference = new AtomicReference();

        // TODO: Fix it in one line (any)
        final String strBefore = "Before changes";
        final String afterChanges = "After Changes";

        //reference.compareAndSet(null, "Not a bug");
        reference.compareAndSet(null, "This is a bug");

        reference.compareAndSet("This is a bug", "Not a Bug");
        reference.compareAndSet("Not a bug", "This is a bug");

        assertEquals(reference.get(), "Not a Bug");
    }


    @Test
    public void testAtomicReferenceThread() throws InterruptedException {
        AtomicReference reference = new AtomicReference();
        CountDownLatch latchOne = new CountDownLatch(1);
        CountDownLatch latchTwo = new CountDownLatch(1);

        new Thread(() -> {
            reference.set("One");
            latchOne.countDown();
            // TODO: fix by using .countDown() for the first Latcher to hit the second Thread
        }).start();

        new Thread(() -> {
            try {
                latchOne.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String str = reference.get() + "Two";
            reference.set(str);
            // TODO: fix here - Use reference.set()

            latchTwo.countDown();
        }).start();

        latchTwo.await(1, TimeUnit.SECONDS); // await '0' or proceed
        assertEquals("OneTwo", reference.get());
    }
}
