package com.epam.executors.addition.barrier;


import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Example: http://tutorials.jenkov.com/java-util-concurrent/semaphore.html
 * CountDownLatch and CyclicBarrier are similar rto each other.
 * But CyclicBuffer can be reset and re-work again
 */
public class BarrierTest {

    private static class Counter {
        private AtomicInteger count = new AtomicInteger(0);

        public int parallelAlgorithm() {
            return count.incrementAndGet();
        };
    }

    private static class Computation {

        private Counter counter;
        private CyclicBarrier cyclicBarrier;

        public Computation(Counter counter) {
            this.counter = counter;
        }

        public void compute() {
            try {
                System.out.println(Thread.currentThread().getName() + " Computes sum " + counter.parallelAlgorithm() + " Awaiting: " + cyclicBarrier.getNumberWaiting() + " Parties: " + cyclicBarrier.getParties());
                Thread.sleep(1000 + new Random().nextInt(1000));
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + " exit compute");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        public void setBarrier(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, BrokenBarrierException {

        ExecutorService service = Executors.newFixedThreadPool(4);

        Computation computation = new Computation(new Counter());
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () ->{
            System.out.println("The result of computation is: " + computation.counter.count.get());
        });
        computation.setBarrier(cyclicBarrier);

        IntStream.range(0, 4).forEach((x) -> {
            try {

                computeSum(service, computation, cyclicBarrier);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Exit Main...");
        Thread.sleep(1000);
        putDown(service, 4);
    }

    private static void computeSum(ExecutorService service, Computation computation, CyclicBarrier cyclicBarrier) throws InterruptedException, BrokenBarrierException {
        IntStream.range(0, 4).forEach((port) -> {
                    service.submit(() -> {
                        computation.compute();
                    });
                }
        );

        cyclicBarrier.await();
        System.out.println(Thread.currentThread().getName() + " Awaiting: " + cyclicBarrier.getNumberWaiting());
        System.out.println("Result: " + computation.counter.count.get() + " Reset!");
        cyclicBarrier.reset();
    }

    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        service.shutdown(); // reject new threads
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}
