package com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


/**
 * Uncomment the setProperty()
 * Then ForkJoinPool will own 20 threads inside
 */
public class JavaForkJoinPoolZCustomParallelism {


    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        IntStream s = IntStream.range(0, 20);
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
        s.parallel().forEach(i -> {
            try {
                Thread.sleep(100);
            } catch (Exception ignore) {
            }
            System.out.print((System.currentTimeMillis() - start) + " ");
        });
    }

    private static void putDown(ExecutorService executorService) throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }


}
