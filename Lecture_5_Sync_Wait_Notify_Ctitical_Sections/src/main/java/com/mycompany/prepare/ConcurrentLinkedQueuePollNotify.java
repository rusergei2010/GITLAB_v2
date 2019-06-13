package com.mycompany.prepare;


import com.mycompany.prepare.utils.Utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Visual VM Thread Dead Lock, Thread Dump
 * notify - awake one waiting thread on the LOCK object (object of synchronization or monitor lock)
 *
 */
public class ConcurrentLinkedQueuePollNotify {

    static class Counter {
        // monitor lock
        static Object object = new Object(); // LOCK OBJECT

        Queue queue = new ConcurrentLinkedQueue();

        public void inc() {
            synchronized (object) {
                try {
                    System.out.println("Start waiting...");
                    if (queue.isEmpty()) {
                        object.wait();
                        Object object = queue.poll();
                    }
                    System.out.println("Exit waiting...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void notifyUs() {
            synchronized (object) {
                object.notify();
            }
        }
    }

    public static void main(String... args) {
        Counter counter = new Counter();
        new Thread(counter::inc).start();
        new Thread(counter::inc).start();
        new Thread(counter::inc).start();

        System.out.println("notify");
        Utils.sleep(1000);
        counter.notifyUs();

        System.out.println("Exit");

    }
}
