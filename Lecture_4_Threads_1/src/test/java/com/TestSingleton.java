package com;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;


public class TestSingleton {

    static volatile TestSingleton instance;

    public TestSingleton() {
    }

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
            // TODO: complete
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
        final AtomicReference<TestSingleton> instance = new AtomicReference<>();


        Thread thread1 = createThread(() -> {
            // TODO: replace with working code
            instance.compareAndSet(null, TestSingleton.getInstance());
        });

        thread1.start();

        System.out.println(thread1.getState());

        thread1.join(1000);



        assertEquals(TestSingleton.getInstance(), instance.get());
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