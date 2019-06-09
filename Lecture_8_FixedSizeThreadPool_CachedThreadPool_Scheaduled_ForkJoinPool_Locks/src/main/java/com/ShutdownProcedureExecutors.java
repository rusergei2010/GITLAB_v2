package com;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


public class ShutdownProcedureExecutors {

    private static final AtomicInteger atomicInt = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

//        executorService.submit(App::increment);
//        executorService.submit(App::increment);
        List<Callable<Integer>> promise = new ArrayList<>();

        IntStream.range(0, 10).forEach(i -> promise.add(i, () -> {
            try {
                Util.sleep(1000);
                System.out.println(i);
            } catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return i;
        }));


        new Thread(() -> {
            try {
                Util.sleep(2000);
                ShutdownProcedureExecutors.shutdown(executorService);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).start();

        executorService.invokeAll(promise);

        // It will be rejected
        List<Future<Integer>> futures = executorService.invokeAll(promise);
        futures.stream().map((future) -> {
            try {
                return future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).forEach((x) -> System.out.println("Received :" + x));
        // wait till first is completed
//        Thread.sleep(2500);
//        executorService.invokeAll(promise);
    }

    private static void shutdown(ExecutorService executorService) throws InterruptedException {
        try {
            System.out.println("Shutdown");
            executorService.shutdown();
            if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                System.out.println("Shutdown Now");
            }

//            executorService.shutdownNow(); // Syste.exit(1) - use exit instead
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
