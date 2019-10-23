package com.mycompany.prepare;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/** Reading threads are paralleled and sequenced with Writing
 * Cache is a perfect sample to demo the parallel read operations when 1 write operation occurs per thousands of read operations
 */
public class CacheReentranReadWriteLockExample {

    private static final int TIMEOUT = 1000;

    private static class CacheReentranReadWriteLock<K, V> {

        AtomicInteger rh = new AtomicInteger(0);
        private Map<K, V> map = new HashMap<>();
        private ReadWriteLock mutex = new ReentrantReadWriteLock();
        Lock readLock;
        Lock writeLock;
        {
            readLock = mutex.readLock();
            writeLock = mutex.writeLock();
        }

        public void put(K key, V value) {

            writeLock.lock(); // synchronized(this){
            printThread("locked");
            try {
                printThread("PUT value = " + value);
                sleep(); // this.wait -> Condition
                map.put(key, value);
            } finally {
                printThread("unlocked");
                writeLock.unlock();
            }
            //}
        }


        public V get(K key) {
            readLock.lock();
            rh.incrementAndGet();
            printThread("locked ");
            try {
                sleep();
                printThread("GET key = " + key);
                return map.get(key);
            } finally {
                rh.decrementAndGet();
                printThread("unlocked ");
                readLock.unlock();
            }
        }
        public int readLocks(){
            return rh.get();
        }

        public int size() {
            readLock.lock();
            try {
                sleep();
                return map.size();
            } finally {
                readLock.unlock();
            }
        }
    }


    private static void sleep() {
        try {
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public CacheReentranReadWriteLockExample() throws InterruptedException {
        CacheReentranReadWriteLock cache = new CacheReentranReadWriteLock();
        ForkJoinPool.commonPool().execute(() -> {
            Thread.currentThread().setName("WRITER");
            new Random().ints(0,2).boxed().map(String::valueOf).forEach(i -> {
                cache.put(i, i);
                System.out.println("CONTENTION AVOIDANCE (PARALLEL READERS) : " + cache.readLocks());
            });
        });

        ForkJoinPool.commonPool().execute(() -> {
            Thread.currentThread().setName("READER");
            new Random().ints(0,2).boxed().map(String::valueOf).forEach(i -> {
                cache.get(i);
            });
        });

        ForkJoinPool.commonPool().execute(() -> {
            Thread.currentThread().setName("READER");
            new Random().ints(0,2).boxed().map(String::valueOf).forEach(i -> {
                cache.get(i);
            });
        });

        ForkJoinPool.commonPool().execute(() -> {
            Thread.currentThread().setName("READER");
            new Random().ints(0,2).boxed().map(String::valueOf).forEach(i -> {
                cache.get(i);
            });
        });

        Thread.sleep(9000);
    }

    public static void main(String[] args) throws InterruptedException {
        new CacheReentranReadWriteLockExample();
    }

    private static void printThread(String msg) {
        System.out.println(Date.from(Instant.now()) + " " + Thread.currentThread().getName() + " " + msg);
    }

    private static void printThread() {
        printThread("");
    }
}
