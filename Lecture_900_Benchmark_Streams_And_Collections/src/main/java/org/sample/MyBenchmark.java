///*
// * Copyright (c) 2014, Oracle America, Inc.
// * All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *  * Redistributions of source code must retain the above copyright notice,
// *    this list of conditions and the following disclaimer.
// *
// *  * Redistributions in binary form must reproduce the above copyright
// *    notice, this list of conditions and the following disclaimer in the
// *    documentation and/or other materials provided with the distribution.
// *
// *  * Neither the name of Oracle nor the names of its contributors may be used
// *    to endorse or promote products derived from this software without
// *    specific prior written permission.
// *
// * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
// * THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package org.sample;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ForkJoinPool;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
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
//import org.openjdk.jmh.annotations.Warmup;
//
//@BenchmarkMode(Mode.AverageTime)
//@Warmup(iterations = 0)
//@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
//@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@Fork(value = 0)
//@State(Scope.Benchmark)
//public class MyBenchmark {
//
//    private static final int COUNTS = 100;
//    private static final int BIG_COUNTS = 1000_000;
//    List<String> smallArray;
//    List<String> bigArray;
//    String MATCH = "0";
//
//    @Warmup
//    public void warmup() {
//        forEachReplacedWithParallel_Ordered_1000_000();
//    }
//
//    @Setup
//    public void setup() {
//        smallArray = IntStream.range(0, COUNTS).mapToObj(String::valueOf).map((s) -> "A" + s + "B")
//                .collect(Collectors.toList());
//        bigArray = IntStream.range(0, BIG_COUNTS).mapToObj(String::valueOf).map((s) -> "A" + s + "B")
//                .collect(Collectors.toList());
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> forEach_1000_000() {
//        List<String> result = new ArrayList<>(bigArray.size());
//        for (int i = 0; i < bigArray.size(); i++) {
//            String x = bigArray.get(i);
//            x = x.toLowerCase();
//            result.add(x);
//        }
//        return result;
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> forEachReplacedWithStream_1000_000() {
//        return
//                bigArray.stream()
//                        .map(String::toLowerCase)
//                        .collect(Collectors.toList());
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public void forEach_10_iter_100000() {
////        List<String> s = new ArrayList<>(smallArray.size());
//        List<String> result = new ArrayList<>(smallArray.size());
//        for (int j = 0; j < 100000; j++) {
//            for (int i = 0; i < smallArray.size(); i++) {
//                String x = smallArray.get(i);
//                x = x.toLowerCase();
//                result.add(x);
//            }
//            result.clear();
//        }
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public void forEachReplacedWithStream_10_iter_100000() {
//        List<String> s = new ArrayList<>(smallArray.size());
//        for (int j = 0; j < 100000; j++) {
//            List<String> collect = smallArray.stream()
//                    .map(String::toLowerCase)
//                    .collect(Collectors.toList());
//            s = collect;
//            s.clear();
//        }
//    }
//
//    //
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> forEachReplacedWithParallel_Ordered_1000_000() {
//        return
//                bigArray.parallelStream()
//                        .map(x -> x.toLowerCase())
//                        .collect(Collectors.toList());
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> forEachReplacedWithParallel_Unordered_1000_000() {
//        return
//                bigArray.parallelStream()
//                        .unordered() // very important for better performance in parallel streams
//                        .map(x -> x.toLowerCase())
//                        .collect(Collectors.toList());
//    }
//
//    // With small physical delay
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> p_singleThreadDelayOnOperation10ms() {
//        return smallArray.stream()
//                .map(String::toLowerCase)
//                .peek((x) -> sleep_10())
//                .collect(Collectors.toList());
//    }
//
//    // With signif physical delay
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> p_singleThreadDelayOnOperation100ms() {
//        return smallArray.stream()
//                .map(String::toLowerCase)
//                .peek((x) -> sleep_100())
//                .collect(Collectors.toList());
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> parallelThreadDelayOnOperation_Ordered_10ms() {
//        return smallArray.parallelStream()
//                .map(String::toLowerCase)
//                .peek((x) -> sleep_10())
//                .collect(Collectors.toList());
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> parallelThreadDelayOnOperation_Unordered_10ms() {
//        return smallArray.parallelStream()
//                .unordered() // very important for better performance in parallel streams !!!
//                .map(String::toLowerCase)
//                .peek((x) -> sleep_10())
//                .collect(Collectors.toList());
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> parallelThreadDelayOnOperation_Unordered_100ms() {
//        return smallArray.parallelStream()
//                .unordered() // very important for better performance in parallel streams !!!
//                .map(String::toLowerCase)
//                .peek((x) -> sleep_100())
//                .collect(Collectors.toList());
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> parallelThreadDelayOnOperation_Unordered_1000ms() {
//        return smallArray.parallelStream()
//                .unordered() // very important for better performance in parallel streams !!!
//                .map(String::toLowerCase)
//                .peek((x) -> sleep_1000())
//                .collect(Collectors.toList());
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> parallelThreadDelayOnOperation_Unordered_CustomForkJoinPool_100Threads_10ms() {
//        final int parallelism = 100; // play around
//        final ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);
//
//        try {
//            return forkJoinPool.submit(() -> smallArray.parallelStream()
//                    .unordered() // very important for better performance in parallel streams !!!
//                    .map(String::toLowerCase)
//                    .peek((x) -> sleep_10()) // very important to set up the maximum number of threads for operations requiring physical com.data exchange (network, filesystem, etc.)
//                    .collect(Collectors.toList())).get();
//
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        } finally {
//            forkJoinPool.shutdown(); //always remember to shutdown the pool
//        }
//        return null;
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> parallelThreadDelayOnOperation_Unordered_CustomForkJoinPool_100Threads_100ms() {
//        final int parallelism = 100; // play around
//        final ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);
//
//        try {
//            return forkJoinPool.submit(() -> smallArray.parallelStream()
//                    .unordered() // very important for better performance in parallel streams !!!
//                    .map(String::toLowerCase)
//                    .peek((x) -> sleep_100()) // very important to set up the maximum number of threads for operations requiring physical com.data exchange (network, filesystem, etc.)
//                    .collect(Collectors.toList())).get();
//
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        } finally {
//            forkJoinPool.shutdown(); //always remember to shutdown the pool
//        }
//        return null;
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public List<String> parallelThreadDelayOnOperation_Unordered_CustomForkJoinPool_100Threads_1000ms() {
//        final int parallelism = 100; // play around
//        final ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);
//
//        try {
//            return forkJoinPool.submit(() -> smallArray.parallelStream()
//                    .unordered() // very important for better performance in parallel streams !!!
//                    .map(String::toLowerCase)
//                    .peek((x) -> sleep_1000()) // very important to set up the maximum number of threads for operations requiring physical com.data exchange (network, filesystem, etc.)
//                    .collect(Collectors.toList())).get();
//
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        } finally {
//            forkJoinPool.shutdown(); //always remember to shutdown the pool
//        }
//        return null;
//    }
//
//    private static void sleep_10() {
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void sleep_100() {
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void sleep_1000() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
