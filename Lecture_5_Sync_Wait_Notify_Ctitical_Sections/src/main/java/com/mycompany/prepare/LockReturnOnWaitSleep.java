package com.mycompany.prepare;


import com.mycompany.prepare.utils.Utils;

// Mutex, critical section in the static method, wait vs sleep
public class LockReturnOnWaitSleep {

    // how to make it thread safe - thread independent
    static class Counter {
        // isolated Object
        private int counter = 0;

        /**
         * Lock is released for wait() operation and another thread can enter critical section (synchronized)
         * @throws InterruptedException
         */
        public synchronized void inc() throws InterruptedException { // try to acquire lock on the 'counter' object
            System.out.println(Thread.currentThread().getName() + " executed. Inc. Lock Acquired. Thread waiting...Release lock...");
            wait(500); // wait releases lock on the sync object when it is being awaiting and attempt to acquire again when it is elapsed
            // if lock is till acquired by another thread then continue awaiting it
            counter++;
            System.out.println(Thread.currentThread().getName() + " executed. Inc. Go to Sleep for 5 sec. Counter = " + counter);
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " executed. Inc. After Sleep 5 sec. Counter = " + counter);
            wait(1000);
        }

        /**
         * Lock is released for wait() operation and another thread can enter critical section (synchronized)
         * @throws InterruptedException
         */
        public synchronized void dec() throws InterruptedException { // try to acquire lock on the 'counter' object
            System.out.println(Thread.currentThread().getName() + " executed. Dec. Lock Acquired. Thread waiting...Release lock...");
            wait(1000);
            System.out.println(Thread.currentThread().getName() + " Dec. Now decrement 1");
            counter--;
            System.out.println(Thread.currentThread().getName() + " executed. Dec. Counter = " + counter);
            wait(1000); // when Thread
        }

        /**
         * Lock is not released on the object during sleep (principle difference with wait())
         * @throws InterruptedException
         */
        public synchronized void incSleep() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " executed start");
            Thread.sleep(1000);
            counter++;
            System.out.println(Thread.currentThread().getName() + " executed end");
        }

        public synchronized void decSleep() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " executed start");
            Thread.sleep(1000);
            counter--;
            System.out.println(Thread.currentThread().getName() + " executed end");
        }
    }


    public static void main(String... args) {
        System.out.println("### Wait example! ###");

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

        Utils.sleep(7000);

        System.out.println("### Sleep example! ###");

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
