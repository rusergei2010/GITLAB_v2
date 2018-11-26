/*
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
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

*/
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
 *//*


@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 0)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 0)
@State(Scope.Benchmark)
public class BenchmarkReadCollections {

    //    private static final int COUNT = 1000000;
    private static final int COUNT_READ = 10000;
    private static final int LOOP = 50; 

    //    private List<String> origin = IntStream.range(0, COUNT).mapToObj(String::valueOf).collect(Collectors.toList());
    private List<String> originRead = IntStream.range(0, COUNT_READ).mapToObj(String::valueOf).collect(Collectors.toList());

    private List<String> array = new ArrayList<>();
    private List<String> syncArray = Collections.synchronizedList(new ArrayList<>());

    private Map<String, String> map = new HashMap<>();
    private Map<String, String> syncMap = Collections.synchronizedMap(new HashMap<>());

    private Map<String, String> concurMap = new ConcurrentHashMap<>();

    @Warmup
    public void warmup() {
        System.out.println("Warm up");
    }

    @Setup
    public void setup() throws ExecutionException, InterruptedException {
        System.out.println("Setup");
        array.clear();
        syncArray.clear();
        map.clear();
        syncMap.clear();
        concurMap.clear();

        originRead.forEach((s) -> {
            array.add(s);
            syncArray.add(s);
            map.put(s, s);
            syncMap.put(s, s);
            concurMap.put(s, s);
        });
    }

    @Benchmark
    @Threads(1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void arrayRead() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; i++)
            originRead.forEach((s) -> {
                array.contains(s);
            });
    }

    @Benchmark
    @Threads(1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void syncArrayRead() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; i++)
            originRead.forEach((s) -> {
                syncArray.contains(s);
            });
    }

    @Benchmark
    @Threads(1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void arrayReadIndex() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; i++)
            originRead.forEach((s) -> {
                array.get(Integer.valueOf(s));
            });
    }

    @Benchmark
    @Threads(1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void syncArrayReadIndex() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; i++)
            originRead.forEach((s) -> {
                syncArray.get(Integer.valueOf(s));
            });
    }

    @Benchmark
    @Threads(1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void mapContains() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; i++)
            originRead.forEach((s) -> {
                map.containsKey(Integer.valueOf(s));
            });
    }

    @Benchmark
    @Threads(1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void syncMapContains() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; i++)
            originRead.forEach((s) -> {
                syncMap.containsKey(Integer.valueOf(s));
            });
    }

    @Benchmark
    @Threads(1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void concurMapContains() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; i++)
            originRead.forEach((s) -> {
                concurMap.containsKey(Integer.valueOf(s));
            });
    }


    @Benchmark
    @Threads(4)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void syncArrayReadIndex_4_Threads() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; i++)
            originRead.forEach((s) -> {
                syncArray.get(Integer.valueOf(s));
            });
    }

    @Benchmark
    @Threads(4)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void syncMapContains_4_Threads() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; i++)
            originRead.forEach((s) -> {
                syncMap.containsKey(Integer.valueOf(s));
            });
    }

    @Benchmark
    @Threads(4)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void concurMapContains_4_Threads() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; i++)
            originRead.forEach((s) -> {
                concurMap.containsKey(Integer.valueOf(s));
            });
    }

}*/
