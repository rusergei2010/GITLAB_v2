//package com.epam.functions.apps.predicates.benchmark;
//
//
//import org.openjdk.jmh.annotations.Benchmark;
//import org.openjdk.jmh.annotations.BenchmarkMode;
//import org.openjdk.jmh.annotations.Fork;
//import org.openjdk.jmh.annotations.Measurement;
//import org.openjdk.jmh.annotations.Mode;
//import org.openjdk.jmh.annotations.OutputTimeUnit;
//import org.openjdk.jmh.annotations.Scope;
//import org.openjdk.jmh.annotations.Setup;
//import org.openjdk.jmh.annotations.State;
//import org.openjdk.jmh.annotations.TearDown;
//import org.openjdk.jmh.annotations.Warmup;
//import org.openjdk.jmh.runner.RunnerException;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.IntStream;
//
//
//@BenchmarkMode(Mode.SampleTime)
//@Warmup(iterations = 0)
//@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
//@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@Fork(value = 1)
//@State(Scope.Thread)
//public class BenchmarkBlockingQueue {
//
//    private static final int MESSAGES = 20000;
//    ExecutorService service_2_threads = Executors.newFixedThreadPool(2);
//
//    Producer prod_2_threads;
//    Consumer consumer_2_threads;
//
//    CountDownLatch latchStart = new CountDownLatch(1);
//    CountDownLatch latchEnd = new CountDownLatch(2);
//
//    public static void main(final String[] args) throws IOException, RunnerException {
//        org.openjdk.jmh.Main.main(args);
//    }
//
//    @Setup
//    public void setup() throws IOException, InterruptedException {
//        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
//
//        prod_2_threads = new Producer(queue, latchStart, latchEnd);
//        consumer_2_threads = new Consumer(queue, latchStart, latchEnd);
//    }
//
//
//    @TearDown
//    public void tearDown() {
//        System.out.println("###################### Teat Down now");
//
//        shutdown(service_2_threads, 1, false);
//    }
//
//
//    @Benchmark
//    public void testBlockingQueue_2_threads() throws InterruptedException {
//        service_2_threads.submit(prod_2_threads);
//        service_2_threads.submit(consumer_2_threads);
//
//        latchStart.countDown();
//        // can use the timeouts if test is running infinitely
//        latchEnd.await();
//        System.out.println("Count Down latch is completed");
//    }
//
//
//    private static class Producer implements Runnable {
//
//        BlockingQueue<String> queue;
//        private CountDownLatch latchStart;
//        private CountDownLatch latchEnd;
//
//        public Producer(BlockingQueue<String> queue, CountDownLatch latchStart, CountDownLatch latchEnd) {
//            this.queue = queue;
//            this.latchStart = latchStart;
//            this.latchEnd = latchEnd;
//        }
//
//        @Override
//        public void run() {
//            // current thread is waiting for the latch count eq 0
//            try {
//                latchStart.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            IntStream.range(0, MESSAGES).forEach((x) -> {
//                final String str = "Send Message: " + x;
//                try {
//                    queue.put(str);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            // 1 task is completed
//            latchEnd.countDown();
//        }
//    }
//
//    private static class Consumer implements Runnable {
//
//        BlockingQueue<String> queue;
//        private CountDownLatch latchStart;
//        private CountDownLatch latchEnd;
//
//        public Consumer(BlockingQueue<String> queue, CountDownLatch latchStart, CountDownLatch latchEnd) {
//            this.queue = queue;
//            this.latchStart = latchStart;
//            this.latchEnd = latchEnd;
//        }
//
//        @Override
//        public void run() {
//            // current thread is waiting for the latch count eq 0
//            try {
//                latchStart.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//
//            IntStream.range(0, MESSAGES).forEach((x) -> {
//                String str = null;
//                try {
//                    str = queue.take();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    throw new RuntimeException(e);
//                }
//            });
//
//            // 1 task is completed
//            latchEnd.countDown();
//        }
//    }
//
//    public static void shutdown(ExecutorService executorService, int timeout, boolean exit) {
//        try {
//            executorService.shutdown();
//            if (!executorService.awaitTermination(timeout, TimeUnit.SECONDS)) {
//                executorService.shutdown();
//                System.out.println("Shutdown");
//            }
//        } catch (InterruptedException e) {
//            List<Runnable> waiting = executorService.shutdownNow();
//            waiting.forEach((task) -> {
//                System.out.println("Waiting: " + task);
//            });
//            System.out.println("Shutdown Now");
//            System.out.println("Terminated: " + executorService.isTerminated());
//        } finally {
//            if (exit) {
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.exit(0);
//            }
//        }
//    }
//}
