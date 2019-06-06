package com.epam.executors.addition.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Barrier {


    static class Counter {
        public AtomicInteger counter = new AtomicInteger(0);
        public void inc() {
            counter.getAndIncrement();
        }
    }
    static class Task implements Runnable {

        private final CyclicBarrier cyclicBarrier;
        private final Counter counter;

        public Task(final CyclicBarrier cyclicBarrier, final Counter counter) {
            this.cyclicBarrier = cyclicBarrier;
            this.counter = counter;
        }

        @Override public void run() {
            try {
                System.out.println("Task has started");
                System.out.println("Task is being executed.");
                Thread.sleep(1000);

                counter.inc();
                cyclicBarrier.await();
                System.out.println("Task is completed");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {


        Counter counter = new Counter();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("Barrier has been executed");
        });

        calculate(counter, cyclicBarrier);
        cyclicBarrier.reset();
        calculate(counter, cyclicBarrier);
        cyclicBarrier.reset();
        calculate(counter, cyclicBarrier);
        cyclicBarrier.reset();

        System.out.println("Exit...");

        System.out.println("Threads in common pool = " + ForkJoinPool.commonPool().getPoolSize());


    }

    private static void calculate(final Counter counter, final CyclicBarrier cyclicBarrier)
            throws InterruptedException {
        IntStream.range(0, 3).forEach(i -> {
            ForkJoinPool.commonPool().submit(() -> {
                new Task(cyclicBarrier, counter).run();
            });
        });

        System.out.println("Tasks are submitted");
        Thread.currentThread().sleep(2000);
        System.out.println("Call await on CyclicBarrier");
        System.out.println("Counter = " + counter.counter.get());
    }

}
