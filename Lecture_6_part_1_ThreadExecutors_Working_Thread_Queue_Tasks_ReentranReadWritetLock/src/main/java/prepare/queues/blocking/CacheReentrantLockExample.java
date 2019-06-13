package com.mycompany.prepare;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Reading threads are sequenced as well as Writing
 * Use ReentrantReadWriteLock instead reentrant lock for w/r
 */
public class CacheReentrantLockExample {

    private static final int TIMEOUT = 1000;

    private static class CacheReentrantLock<K, V> {

        private Map<K, V> map = new HashMap<>();
        private Lock mutex = new ReentrantLock();

        public void put(K key, V value){
            mutex.lock();
            printThread("locked");
            try {
                printThread("PUT value = " + value);
                sleep();
                map.put(key, value);
            } finally {
                printThread("unlocked");
                mutex.unlock();
            }
        }


        public V get(K key) {
            mutex.lock();
            printThread("locked");
            try {
                sleep();
                printThread("GET key = " + key);
                return map.get(key);
            } finally {
                printThread("unlocked");
                mutex.unlock();
            }
        }

        public int size() {
            mutex.lock();
            try {
                sleep();
                return map.size();
            } finally {
                mutex.unlock();
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


    public CacheReentrantLockExample() throws InterruptedException {
        CacheReentrantLock cache = new CacheReentrantLock();
        ForkJoinPool.commonPool().execute(() -> {
            new Random().ints(0,2).boxed().map(String::valueOf).forEach(i -> {
                cache.put(i, i);
            });
        });

        ForkJoinPool.commonPool().execute(() -> {
            new Random().ints(0,2).boxed().map(String::valueOf).forEach(i -> {
                cache.get(i);
            });
        });

        ForkJoinPool.commonPool().execute(() -> {
            new Random().ints(0,2).boxed().map(String::valueOf).forEach(i -> {
                cache.get(i);
            });
        });

        Thread.sleep(9000);
    }

    public static void main(String[] args) throws InterruptedException {
        new CacheReentrantLockExample();
    }

    private static void printThread(String msg) {
        System.out.println(Date.from(Instant.now()) + Thread.currentThread().getName() + " " + msg);
    }

    private static void printThread() {
        printThread("");
    }
}
