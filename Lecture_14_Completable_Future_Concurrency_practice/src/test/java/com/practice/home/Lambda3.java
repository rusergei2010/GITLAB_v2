package com.practice.home;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Admin on 8/6/2018.
 */
public class Lambda3 {

    /**
     * Fill in the gaps and insert instructions to make code executable
     */
    @Test
    public void testLambda() throws InterruptedException {
        // TODO: Get "RESULT" printed in console
        AtomicInteger counter = new AtomicInteger(0);

        Consumer<AtomicInteger> consumer = (AtomicInteger count) -> {
            System.out.println(count.incrementAndGet());
        };

        Consumer<AtomicInteger> result = wrap(wrap(wrap(wrap(wrap(consumer)))));

        // TODO: fix it
        //result.accept();

        assertEquals(1, counter.get());
    }


    public static void print() {
        System.out.println("RESULT");
    }

    Consumer<AtomicInteger> wrap(Consumer<AtomicInteger> consumer){
        return (AtomicInteger count) -> {
            // TODO: fix it
            // consumer.accept();
        };
    }
}
