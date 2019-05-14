package com.epam.optional.apps.predicates.benchmark.queues.blocking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueEx {


    public static void main(String... args) {
        BlockingQueue<String> windows = new LinkedBlockingQueue<>(5);
//        Cook cook = new Cook(windows);
//        Waiter waiter = new Waiter(windows);
//
//        ExecutorService service = Executors.newCachedThreadPool();
//
//        service.execute(cook);
//        service.execute(waiter);
//        service.execute(waiter);
    }
}
