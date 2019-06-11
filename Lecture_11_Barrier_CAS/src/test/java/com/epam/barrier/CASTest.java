package com.epam.barrier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: fix it
 */
public class CASTest {

    static class Counter {
        AtomicLong counter = new AtomicLong(0);

        void inc(long addValue) {
            long initValue = counter.get();
            long newValue = addValue + initValue;
            while (!counter.compareAndSet(initValue, newValue)) {
                initValue = counter.get();
                // TODO: fix it to comply with CAS approach
//                newValue = <...> + <...>;
            }
        }

        long get() {
            return counter.get();
        }
    }

    @Test
    public void testCounter() throws InterruptedException {
        Counter counter = new Counter();
        CountDownLatch latch = new CountDownLatch(100000);

        IntStream.range(0, 100000).forEach((i) -> {
            ForkJoinPool.commonPool().execute(() -> this.increment(counter, latch));
        });

        latch.await(); // await '0'

        Assert.assertEquals(100000, counter.get());
    }

    private void increment(final Counter counter, final CountDownLatch latch) {
        latch.countDown();
        counter.inc(1);
    }

}
