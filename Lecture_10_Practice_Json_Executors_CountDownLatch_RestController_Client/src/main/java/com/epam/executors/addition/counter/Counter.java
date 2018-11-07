package com.epam.executors.addition.counter;

import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    public int count = 0;
    ReentrantLock lock = new ReentrantLock();

    public synchronized void inc() {
        count++;
    }

    public void dec() {
        count--;
    }

    public void incrementWithReentrantLock() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
}