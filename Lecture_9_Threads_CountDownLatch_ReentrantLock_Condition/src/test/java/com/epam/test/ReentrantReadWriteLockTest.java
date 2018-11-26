package com.epam.test;

import com.epam.test.util.Util;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO: Fix in one line
public class ReentrantReadWriteLockTest {


    private static class Cache {
        Optional<String> name = Optional.empty();
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        public Optional<String> getName() throws InterruptedException {
            boolean locked = false;
            try {
                if (readLock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                    locked = true;
                    System.out.println("Read is locked");
                    Util.threadSleep(1000);
                    return name;
                }
            } finally {
                if (locked) {
                    readLock.unlock();
                    System.out.println("Read is unlocked");
                }
            }
            return Optional.empty();
        }

        public void setName(Optional<String> name) {
            name.orElseThrow(IllegalArgumentException::new);
            boolean locked = false;
            try {
                if (writeLock.tryLock(3000, TimeUnit.MILLISECONDS)) {
                    locked = true;
                    this.name = name;
                    System.out.println("Write is locked");
                    Util.threadSleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (locked) {
                    writeLock.unlock();
                    System.out.println("Write is unlocked");
                }
            }
        }

        public void setNameImmediately(Optional<String> name) {
            name.orElseThrow(IllegalArgumentException::new);
            boolean locked = false;
            try {
                //
                writeLock.lock();
                    locked = true;
                    System.out.println("Write is locked");
                    Util.threadSleep(1000);
                    this.name = name;

            } finally {
                if (locked) {
                    writeLock.unlock();
                    System.out.println("Write is unlocked");
                }
            }
        }
    }

    @Test
    public void testReentrantReadWriteLock() throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(10); // many threads
        Cache cache = new Cache();

        cache.getName().ifPresent(IllegalArgumentException::new);
        Util.threadSleep(100);

        service.submit(() -> {
            cache.setName(Optional.of("Agent 007"));
        });

        Util.threadSleep(100);

        // parallel access
        assertEquals("Agent 007", cache.getName().get());
        assertEquals("Agent 007", cache.getName().get());

        Util.threadSleep(100);
        service.submit(() -> {
            cache.setNameImmediately(Optional.of("Agent 008"));
        });

        Thread.sleep(9000);

        assertEquals("Agent 008", cache.getName().get());

        System.out.println("Exit Main");
        putDown(service, 1);
    }


    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}
