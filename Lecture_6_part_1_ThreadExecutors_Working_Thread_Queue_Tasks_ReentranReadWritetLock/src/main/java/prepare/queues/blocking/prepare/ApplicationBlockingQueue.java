package prepare.queues.blocking.prepare;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class ApplicationBlockingQueue {

    private static final long TIMEOUT = 100;

    public ApplicationBlockingQueue() throws InterruptedException {

        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(() -> {
            IntStream.range(0, 3000).forEach(i -> {
                System.out.println("WRITE: " + i);
                try {
                    queue.put(jms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        executorService.execute(() -> {
            IntStream.range(0, 1000).forEach(i -> {
                try {
                    System.out.println("READ: " + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        executorService.execute(() -> {
            IntStream.range(0, 1000).forEach(i -> {
                try {
                    System.out.println("READ: " + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        executorService.execute(() -> {
            IntStream.range(0, 1000).forEach(i -> {
                try {
                    System.out.println("READ: " + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        Thread.sleep(10000);
    }

    public static void main(String[] args) throws InterruptedException {
        new ApplicationBlockingQueue();
    }

    private static void sleep() {
        try {
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void print(String str) {
        System.out.println(str);
    }
}
