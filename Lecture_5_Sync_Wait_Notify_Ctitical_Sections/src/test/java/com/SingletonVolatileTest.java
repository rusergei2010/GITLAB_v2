package com;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class SingletonVolatileTest {

    @Test
    public void testSingleton() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            SingletonVolatile.getInstance().setCounter(0);

            new Thread(() -> {
                IntStream.range(0, 1000).forEach((x) -> {
                    SingletonVolatile.getInstance().inc();
                });
            }).start();

            new Thread(() -> {
                IntStream.range(0, 1000).forEach((x) -> {
                    SingletonVolatile.getInstance().inc();
                });
            }).start();

            Thread.sleep(500);

            assertEquals(2 * 1000, SingletonVolatile.getInstance().getCounter());
        }
    }

    private static class SingletonVolatile {
        private static volatile SingletonVolatile instance = null;
        private AtomicInteger counter = new AtomicInteger();

        private SingletonVolatile() {
        }

        public static SingletonVolatile getInstance() {
            synchronized (SingletonVolatile.class) {
                if (instance == null) {
                    try {
                        Thread.sleep(100); // keep sleep()
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instance = new SingletonVolatile();
                }
                return instance;
            }
        }

        public void inc() {
            counter.incrementAndGet();
        }

        public int getCounter() {
            return counter.get();
        }

        public void setCounter(int i) {
            counter.set(i);
        }
    }
}