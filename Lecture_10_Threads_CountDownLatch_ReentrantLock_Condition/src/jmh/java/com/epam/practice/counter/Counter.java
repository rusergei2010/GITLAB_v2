package com.epam.practice.counter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    public int count = 0;
    public AtomicInteger casCount = new AtomicInteger(0);
    ReentrantLock lock = new ReentrantLock();
    public static volatile int volCount = 0; // ++ and -- operations are not atomic for volatile. Only READ.

    public void inc() {
        count++;
    }

    public synchronized void syncInc() {
        count++;
    }

    public void incrementWithReentrantLock() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int CASInc() {
        return casCount.incrementAndGet();
    }

    public void volatileInc() {
        volCount++;
    }
}