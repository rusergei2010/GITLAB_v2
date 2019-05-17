package com.mycompany.prepare;


import com.mycompany.prepare.utils.Utils;

// Mutex, ctitical section in the static method, wait vs sleep
public class LockReturnOnWaitSleep {

    // how to make it thread safe - thread independent
    static class Counter {
        // isolated Object
        private int counter = 0;

        public synchronized void inc() throws InterruptedException { // try to acquire lock on the 'counter' object
            System.out.println(Thread.currentThread().getName() + " executed. Lock Acquired. Thread waiting...Release lock...");
            wait(500); // wait releases lock on the sync object when it is being awaiting and attempt to acquire again when it is elapsed
            // if lock is till acquired by another thread then continue awaiting it
            counter++;
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " executed. Counter = " + counter);
            wait(1000);
        }

        public synchronized void dec() throws InterruptedException { // try to acquire lock on the 'counter' object
            System.out.println(Thread.currentThread().getName() + " executed. Lock Acquired. Thread waiting...Release lock...");
            wait(1000);
            System.out.println(Thread.currentThread().getName() + " Now it is continued");
            counter--;
            System.out.println(Thread.currentThread().getName() + " executed. Counter = " + counter);
            wait(1000); // when Thread
        }


        public synchronized void incSleep() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " executed");
            Thread.sleep(1000);
            counter++;
            System.out.println(Thread.currentThread().getName() + " executed");
        }

        public synchronized void decSleep() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " executed");
            Thread.sleep(1000);
            counter--;
            System.out.println(Thread.currentThread().getName() + " executed");
        }
    }


    public static void main(String... args) {

        Counter counter = new Counter();

        new Thread(() -> {
            try {
                counter.inc();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                counter.dec();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Utils.sleep(5500);

        System.out.println("Sleep example!");

        new Thread(() -> {
            try {
                counter.incSleep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                counter.decSleep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
