//package com.epam.practice;
//
//import com.epam.practice.counter.Counter;
//import org.openjdk.jmh.annotations.Benchmark;
//import org.openjdk.jmh.annotations.BenchmarkMode;
//import org.openjdk.jmh.annotations.Fork;
//import org.openjdk.jmh.annotations.Measurement;
//import org.openjdk.jmh.annotations.Mode;
//import org.openjdk.jmh.annotations.OutputTimeUnit;
//import org.openjdk.jmh.annotations.Scope;
//import org.openjdk.jmh.annotations.Setup;
//import org.openjdk.jmh.annotations.State;
//import org.openjdk.jmh.annotations.Warmup;
//import org.openjdk.jmh.runner.RunnerException;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//
//@BenchmarkMode(Mode.AverageTime)
//@Warmup(iterations = 0)
//@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
//@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@Fork(value = 1)
//@State(Scope.Benchmark)
//public class BenchmarkReentrant {
//
//    private static final int COUNTS = 10000000;
//    Counter counter;
//
//    public static void main(final String[] args) throws IOException, RunnerException {
//        org.openjdk.jmh.Main.main(args);
//    }
//
//    @Setup
//    public void setup() throws IOException, InterruptedException {
//         counter = new Counter();
//    }
//
//    @Benchmark
//    public void testSyncInc() throws InterruptedException {
//        counter.count = 0;
//        while (counter.count < COUNTS)
//            counter.inc();
//    }
//
//    @Benchmark
//    public void testNonSyncInc() throws InterruptedException {
//        counter.count = 0;
//        while (counter.count < COUNTS)
//            counter.syncInc();
//    }
//
//    @Benchmark
//    public void testReentrantLock() throws InterruptedException {
//        counter.count = 0;
//        while (counter.count < COUNTS)
//            counter.incrementWithReentrantLock();
//    }
//
//    @Benchmark
//    public void testCASCount() throws InterruptedException {
//        counter.casCount.set(0);
//        while (counter.CASInc() < COUNTS);
//    }
//
//    @Benchmark
//    public void testVolatileCount() throws InterruptedException {
//        counter.volCount = 0;
//        while (counter.volCount < COUNTS)
//            counter.volatileInc();
//    }
//}
