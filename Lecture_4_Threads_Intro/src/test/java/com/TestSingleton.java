package com;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

/**
 * Quest: fill in missing gaps (TODO or commented lines)
 */
public class TestSingleton {

    static TestSingleton instance;

    public TestSingleton(){}

    public static TestSingleton getInstance(){
        if (instance == null) {
            // TODO: complete
            instance = new TestSingleton();
        }
        return instance;
    }

    /**
     * Fill in the gaps and insert instructions to make code executable
     *
     * @throws InterruptedException
     */
    @Test
    public void testThread() throws InterruptedException {
        final AtomicReference<TestSingleton> instant = new AtomicReference<>();

        Thread thread1 = createThread(() -> {
            // TODO: replace with working code
            instant.compareAndSet(instance, TestSingleton.getInstance()); // TODO
        });

        thread1.start();
        thread1.join(2000); // TODO

        assertEquals(TestSingleton.getInstance(), instant.get());
    }

    private Thread createThread() {
        final Thread thread = new Thread();
        return thread;
    }

    private Thread createThread(Runnable runnable) {
        final Thread thread = new Thread(runnable);
        return thread;
    }

}