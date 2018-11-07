package com.epam.practice;

import com.epam.util.Util;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RaceConditionSReentrantReadWriteLock {


    private static class Cache {
        String name;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        public String getName() {
            readLock.lock();
            System.out.println("Read is locked");
            try {
                Util.threadSleep(3000);
                return name;
            } finally {
                System.out.println("Read is unlocked");
                readLock.unlock();
            }
        }

        public void setName(String name) {
            
            writeLock.lock();
            System.out.println("Write is locked");
            try {
                Util.threadSleep(2000);
                this.name = name;
            } finally {
                System.out.println("Write is unlocked");
                writeLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService service = Executors.newFixedThreadPool(3);
        Cache cache = new Cache();

        service.submit(() -> {
            cache.setName("Value ### 3000 ### ");
        });

        Util.threadSleep(100);

        // parallel access
        System.out.println("Read in parallel now !!!");
        service.submit(() -> {
            final String name = cache.getName();
            System.out.println("Got = " + name + " Time = " + new Date(System.currentTimeMillis()));
        });
        service.submit(() -> {
            final String name = cache.getName() + " Time = " + new Date((System.currentTimeMillis()));
            System.out.println("Got = " + name);
        });


        putDown(service, 6);
    }

    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}
