package com.mycompany.prepare;

/**
 * StackOverFlow
 * Mutex - object that serves two functions: lock/unlock or acquire/release
 * When synchronized(object) is used then it is similar to implicite mutex (synchronized will acquire hidden lock from the object
 * when the thread enter the critical section and releases when it leave the section). So, synchronized(object) is like mutex.acquire()
 */
public class DoubleSyncEntry {

    static class MyCounter {

        private Object o1 = new Object(); //Mutex - object that serves two functions:lock/unlock or acquire//release

        int counter = 0;

        public void inc() {

            synchronized (o1){
                System.out.println(counter++);
                if (counter == 100000) {
                    return;
                }
                inc();
                // Can we catch/handle StackOverflow?
            }
        }
    }

    public static void main(String... args) {
        MyCounter counter = new MyCounter();

        new Thread(() -> {
                counter.inc();
        }).start();

        System.out.println("Exit");
    }
}
