package jhm;

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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
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
public class BenchmarkCollections {

	private static final int COUNT = 1000000;

	private List<String> origin = IntStream.range(0, COUNT).mapToObj(String::valueOf).collect(Collectors.toList());

	private List<String> array = new ArrayList<>();
	private Collection<String> syncArray = Collections.synchronizedList(new ArrayList<>());
	private Collection<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

	private Set<String> set = new HashSet<>();
	private Collection<String> syncSet = Collections.synchronizedSet(new HashSet<>());
	private Collection<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

	@Warmup
	public void warmup(){
		System.out.println("Warm up");
	}

	@Setup
	public void setup() {
		System.out.println("Setup");
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void arrayWrite() throws ExecutionException, InterruptedException {
		ForkJoinPool.commonPool().submit(() -> {
			origin.forEach((s) -> {
				array.add(s);
			});
		}).get();
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void syncArrayWrite() throws ExecutionException, InterruptedException {
		ForkJoinPool.commonPool().submit(() -> {
			origin.forEach((s) -> {
				syncArray.add(s);
			});
		}).get();
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void copyOnWriteArrayListWrite() throws ExecutionException, InterruptedException {
		ForkJoinPool.commonPool().submit(() -> {
			origin.forEach((s) -> {
				copyOnWriteArrayList.add(s);
			});
		}).get();
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void arrayRead() throws ExecutionException, InterruptedException {
		ForkJoinPool.commonPool().submit(() -> {
			origin.forEach((s) -> {
				array.contains(s);
			});
		}).get();
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void syncArrayRead() throws ExecutionException, InterruptedException {
		ForkJoinPool.commonPool().submit(() -> {
			origin.forEach((s) -> {
				syncArray.contains(s);
			});
		}).get();
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void copyOnWriteArrayRead() throws ExecutionException, InterruptedException {
		ForkJoinPool.commonPool().submit(() -> {
			origin.forEach((s) -> {
				copyOnWriteArrayList.contains(s);
			});
		}).get();
	}

}