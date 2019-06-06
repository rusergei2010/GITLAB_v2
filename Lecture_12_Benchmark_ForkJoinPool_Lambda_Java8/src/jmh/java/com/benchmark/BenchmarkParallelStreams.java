package com.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * ============================== HOW TO RUN THIS TEST: ====================================
 *
 * Note that C1 is faster, C2 is slower, but the C1 is slow again! This is because
 * the profiles for C1 and C2 had merged together. Notice how flawless the measurement
 * is for forked runs.
 *
 * You can run this test:
 *
 * a) Via the command line:
 *    $ mvn clean install
 *    $ java -jar target/benchmarks.jar JMHSample_12 -wi 5 -i 5
 *    (we requested 5 warmup/measurement iterations)
 *
 * b) Via the Java API:
 *    (see the JMH homepage for possible caveats when running from IDE:
 *      http://openjdk.java.net/projects/code-tools/jmh/)
 */

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 0)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 0)
@State(Scope.Benchmark)
public class BenchmarkParallelStreams {

    private static final int COUNTS = 100;
    private static final int BIG_COUNTS = 1000_000;
    List<String> smallArray;
    List<String> bigArray;
    String MATCH = "0";

    @Warmup
    public void warmup() {
        forEachReplacedWithParallel_Ordered_1000_000();
    }

    @Setup
    public void setup() {
        smallArray = IntStream.range(0, COUNTS).mapToObj(String::valueOf).map((s) -> "A" + s + "B").collect(Collectors.toList());
        bigArray = IntStream.range(0, BIG_COUNTS).mapToObj(String::valueOf).map((s) -> "A" + s + "B").collect(Collectors.toList());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> forEach_1000_000() {
        List<String> result = new ArrayList<>(bigArray.size());
        for (int i = 0; i < bigArray.size(); i++) {
            String x = bigArray.get(i);
            x = x.toLowerCase();
            result.add(x);
        }
        return result;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> forEachReplacedWithStream_1000_000() {
        return
                bigArray.stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> forEachReplacedWithParallel_Ordered_1000_000() {
        return
                bigArray.parallelStream()
                        .map(x -> x.toLowerCase())
                        .collect(Collectors.toList());
    }
    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> forEachReplacedWithParallel_Unordered_1000_000() {
        return
                bigArray.parallelStream()
                        .unordered() // very important for better performance in parallel streams
                        .map(x -> x.toLowerCase())
                        .collect(Collectors.toList());
    }

    // With small physical delay
    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> p_singleThreadDelayOnOperation10ms() {
        return smallArray.stream()
                        .map(String::toLowerCase)
                        .peek((x) -> sleep_10())
                        .collect(Collectors.toList());
    }

    // With signif physical delay
    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> p_singleThreadDelayOnOperation100ms() {
        return smallArray.stream()
                        .map(String::toLowerCase)
                        .peek((x) -> sleep_100())
                        .collect(Collectors.toList());
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> parallelThreadDelayOnOperation_Ordered_10ms() {
        return smallArray.parallelStream()
                .map(String::toLowerCase)
                .peek((x) -> sleep_10())
                .collect(Collectors.toList());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> parallelThreadDelayOnOperation_Unordered_10ms() {
        return smallArray.parallelStream()
                .unordered() // very important for better performance in parallel streams !!!
                .map(String::toLowerCase)
                .peek((x) -> sleep_10())
                .collect(Collectors.toList());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> parallelThreadDelayOnOperation_Unordered_100ms() {
        return smallArray.parallelStream()
                .unordered() // very important for better performance in parallel streams !!!
                .map(String::toLowerCase)
                .peek((x) -> sleep_100())
                .collect(Collectors.toList());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> parallelThreadDelayOnOperation_Unordered_1000ms() {
        return smallArray.parallelStream()
                .unordered() // very important for better performance in parallel streams !!!
                .map(String::toLowerCase)
                .peek((x) -> sleep_1000())
                .collect(Collectors.toList());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> parallelThreadDelayOnOperation_Unordered_CustomForkJoinPool_100Threads_10ms() {
        final int parallelism = 100; // play around
        final ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);

        try {
            return forkJoinPool.submit(() -> smallArray.parallelStream()
                    .unordered() // very important for better performance in parallel streams !!!
                    .map(String::toLowerCase)
                    .peek((x) -> sleep_10()) // very important to set up the maximum number of threads for operations requiring physical com.data exchange (network, filesystem, etc.)
                    .collect(Collectors.toList())).get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown(); //always remember to shutdown the pool
        }
        return null;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> parallelThreadDelayOnOperation_Unordered_CustomForkJoinPool_100Threads_100ms() {
        final int parallelism = 100; // play around
        final ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);

        try {
            return forkJoinPool.submit(() -> smallArray.parallelStream()
                    .unordered() // very important for better performance in parallel streams !!!
                    .map(String::toLowerCase)
                    .peek((x) -> sleep_100()) // very important to set up the maximum number of threads for operations requiring physical com.data exchange (network, filesystem, etc.)
                    .collect(Collectors.toList())).get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown(); //always remember to shutdown the pool
        }
        return null;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> parallelThreadDelayOnOperation_Unordered_CustomForkJoinPool_100Threads_1000ms() {
        final int parallelism = 100; // play around
        final ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);

        try {
            return forkJoinPool.submit(() -> smallArray.parallelStream()
                    .unordered() // very important for better performance in parallel streams !!!
                    .map(String::toLowerCase)
                    .peek((x) -> sleep_1000()) // very important to set up the maximum number of threads for operations requiring physical com.data exchange (network, filesystem, etc.)
                    .collect(Collectors.toList())).get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown(); //always remember to shutdown the pool
        }
        return null;
    }

    private static void sleep_10() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void sleep_100() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void sleep_1000() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
