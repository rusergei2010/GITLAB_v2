package com.epam.executors.addition.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class BarrierApp {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(4, () -> {
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Barrier exec");
        });
        System.out.println("ForkJoinPool size = "  + ForkJoinPool.commonPool().getPoolSize());

        IntStream.range(0, 4).forEach(i -> {
            ForkJoinPool.commonPool().submit(() -> {
                try {
                    System.out.println("Barrier awaiting");
                    System.out.println("Barrier awaiting completed");
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        });


        System.out.println("Main thread awaiting");
        barrier.await();
        System.out.println("Exit App");

    }

}
