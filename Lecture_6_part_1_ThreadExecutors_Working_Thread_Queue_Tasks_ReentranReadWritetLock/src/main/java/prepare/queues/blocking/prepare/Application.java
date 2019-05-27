package prepare.queues.blocking.prepare;

import java.time.Instant;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class Application {

    private static final long TIMEOUT = 500;

    public static class MyCache<K, V> {

        //        ReentrantReadWriteLock
//        Lock reentrantLock = new ReentrantLock();
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock;
        ReentrantReadWriteLock.WriteLock writeLock;
        {
            readLock = reentrantReadWriteLock.readLock();
            writeLock  = reentrantReadWriteLock.writeLock();
        }
        private Map<K, V> map = new HashMap<>();

        public void put(K key, V value) {
            writeLock.lock();
            print(Date.from(Instant.now()) + " " +  Thread.currentThread().getName() + " PUT lock");
            try {
//                sleep();
                map.put(key, value);
            } finally {
                print(Date.from(Instant.now()) + " " +  Thread.currentThread().getName() + " PUT unlock");
                writeLock.unlock();
            }
        }

        public V get(K key) {
            readLock.lock(); // contention
            print(Date.from(Instant.now()) + " " +  Thread.currentThread().getName() + " GET lock");
            try {
                sleep();
                return map.get(key);
            } finally {
                print(Date.from(Instant.now()) + " " +  Thread.currentThread().getName() + " GET unlock");
                readLock.unlock();
            }
        }

    }

    public Application() throws InterruptedException {

        MyCache<String, String> cache = new MyCache<>();
        ForkJoinPool.commonPool().execute(() -> {
            IntStream.range(0, 1000).boxed().map(String::valueOf).forEach(i -> {
//                System.out.println(Thread.currentThread().getName() + " PUT : " + i);
                cache.put(i, i);
            });
        });

        ForkJoinPool.commonPool().execute(() -> {
            IntStream.range(0, 1000).boxed().map(String::valueOf).forEach(i -> {
                cache.get(i);
//                System.out.println(Thread.currentThread().getName() + " GET : " + cache.get(i));
            });
        });

        ForkJoinPool.commonPool().execute(() -> {
            IntStream.range(0, 1000).boxed().map(String::valueOf).forEach(i -> {
                cache.get(i);
//                System.out.println(Thread.currentThread().getName() + " GET : " + cache.get(i));
            });
        });

        ForkJoinPool.commonPool().execute(() -> {
            IntStream.range(0, 1000).boxed().map(String::valueOf).forEach(i -> {
                cache.get(i);
//                System.out.println(Thread.currentThread().getName() + " GET : " + cache.get(i));
            });
        });

        Thread.sleep(10000);
    }

    public static void main(String[] args) throws InterruptedException {
        new Application();
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
