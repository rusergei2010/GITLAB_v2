package com.epam.executors.addition.com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ScheduledZUse {

    private static class Counter {
        volatile int counter = 0;

        public void inc() {
            counter++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            if (counter % 100 == 0)
//                System.out.println(counter); // bad manner
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Counter counter = new Counter();

        executorService.submit(() -> {
            IntStream.range(0, 1000).forEach((i) -> counter.inc());
        });

        executorService.submit(() -> {
            IntStream.range(0, 1000).forEach((i) -> counter.inc());
        });

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(() -> {
            System.out.println("Counter : " + counter.counter); // use the visitor pattern
        }, 2, 1, TimeUnit.SECONDS);

        putDown(executorService);
        putDown(service);
    }

    private static void putDown(ExecutorService executorService) throws InterruptedException {
//        executorService.shutdown();
//        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }


}
