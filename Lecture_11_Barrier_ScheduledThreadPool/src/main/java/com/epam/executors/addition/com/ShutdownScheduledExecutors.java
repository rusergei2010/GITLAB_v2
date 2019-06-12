package com.epam.executors.addition.com;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ShutdownScheduledExecutors {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

        ScheduledFuture future = service.scheduleAtFixedRate(() -> {
            System.out.print("Ping ... ");
        }, 1, 1, TimeUnit.SECONDS);


        executorService.submit(() -> {
            // executed with 1 sec lag between tasks
//            service.scheduleWithFixedDelay(runnable("Executed delayed for 200 ms at 1 sec rate between tasks", 200), 1, 1, TimeUnit.SECONDS);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Cancel periodic Task Executor");
            future.cancel(false);
            putDown(service);
            putDown(executorService);
        });
        System.out.println("Received: " + future.get());
    }

    private static void putDown(ExecutorService executorService) {
        executorService.shutdown();
    }
}
