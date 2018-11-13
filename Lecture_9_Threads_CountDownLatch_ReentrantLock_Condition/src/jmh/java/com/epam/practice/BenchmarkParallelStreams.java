package com.epam.practice;

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
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 0)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1)
@State(Scope.Benchmark)
public class BenchmarkParallelStreams {

    private static final int COUNTS = 10000000; // 1 million
    Collection<Integer> ints;

    public static void main(final String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup
    public void setup() {
        ints = IntStream.range(0, COUNTS).boxed().collect(Collectors.toList());
    }

    @Benchmark
    public void testWithoutParallelStream() {
        Collection<String> result = ints.stream().map(String::valueOf).collect(Collectors.toList());
    }

    @Benchmark
    public void testWithParallelStream() {
        Collection<String> result = ints.parallelStream().map(String::valueOf).collect(Collectors.toList());
    }
}
