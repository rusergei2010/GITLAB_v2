package com.epam.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: fix it
 */
public class Barrier {

  @Test
  public void testBarrier() throws InterruptedException {

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

    // TODO: clone the 'calculate' operation to reuse it three (3) time. Do not forget
    // TODO: cyclicBarrier.reset(); after every operation being completed

    Assert.assertEquals(9, counter.counter.get());
  }

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

    @Override
    public void run() {
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

  private static void calculate(final Counter counter, final CyclicBarrier cyclicBarrier)
      throws InterruptedException {
    IntStream.range(0, 3).forEach(i -> {
      ForkJoinPool.commonPool().submit(() -> {
        new Task(cyclicBarrier, counter).run();
      });
    });

    Thread.currentThread().sleep(2000);
  }

}
