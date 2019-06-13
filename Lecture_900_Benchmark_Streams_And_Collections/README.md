Test performance of Streams vs Collections on Small(100 items) and big(1_000_000 items) arrays:


$ mvn clean install
$ java -jar target/benchmarks.jar

Result:
-------

Benchmark                                                                                  Mode  Cnt   Score   Error  Units
---------------------------------------------------------------------------------------------------------------------------
ARRAY.forEach() vs STREAM.forEach() vs Iter 100000 (with 100 elements)

MyBenchmark.forEach_1000_000                                                               avgt        0.124           s/op - fastest is a single thread and for() iterator
MyBenchmark.forEachReplacedWithStream_1000_000                                             avgt        0.154           s/op - the same as above but with stream being produced
---------------------------------------------------------------------------------------------------------------------------

bigArray.parallelStream().unordered() // very important for better performance in parallel streams
MyBenchmark.forEachReplacedWithParallel_Unordered_1000_000                                 avgt        0.148           s/op - parallel stream is executed but unordered !!! explicitely

bigArray.parallelStream() // mistakenly we do not set unordered() that causes big performance issues on large arrays
MyBenchmark.forEachReplacedWithParallel_Ordered_1000_000                                   avgt        0.364           s/op - parallel with ordered 

---------------------------------------------------------------------------------------------------------------------------
MyBenchmark.p_singleThreadDelayOnOperation100ms                                            avgt       10.304           s/op
MyBenchmark.p_singleThreadDelayOnOperation10ms                                             avgt        1.450           s/op
MyBenchmark.parallelThreadDelayOnOperation_Ordered_10ms                                    avgt        0.297           s/op
MyBenchmark.parallelThreadDelayOnOperation_Unordered_1000ms                                avgt       26.140           s/op
MyBenchmark.parallelThreadDelayOnOperation_Unordered_100ms                                 avgt        2.558           s/op
MyBenchmark.parallelThreadDelayOnOperation_Unordered_10ms                                  avgt        0.311           s/op
MyBenchmark.parallelThreadDelayOnOperation_Unordered_CustomForkJoinPool_100Threads_1000ms  avgt        7.072           s/op
MyBenchmark.parallelThreadDelayOnOperation_Unordered_CustomForkJoinPool_100Threads_100ms   avgt        0.787           s/op
MyBenchmark.parallelThreadDelayOnOperation_Unordered_CustomForkJoinPool_100Threads_10ms    avgt        0.206           s/op
