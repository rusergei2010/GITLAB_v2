package prepare;


import java.io.*;

/**
 * Add VM params to th launch params:
 * -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false  -Djava.rmi.server.hostname=localhost
 *
 * Check with mission control tab: "Threads" Blocking (Deadlock)
 */
public class Deadlock {

    public static class Counter {

        private int counter;
        private Object object1 = new Object();
        private Object object2 = new Object();


        public Counter(int counter) {
            this.counter = counter;
        }

        public void inc() throws InterruptedException {
            synchronized (object1) {
                System.out.println(Thread.currentThread().getName());
                object1.wait(100);
                synchronized (object2) {
                    object2.wait(100);
                }
            }
            System.out.println("Exited inc()");
        }

        public void dec() throws InterruptedException {
            synchronized (object2) {
                System.out.println(Thread.currentThread().getName());
                object2.wait(100);
                synchronized (object1) {
                    object1.wait(100);
                }
            }
            System.out.println("Exited dec()");
        }

        public Integer getCounter() {
            return counter;
        }
    }

    private static class MyThread extends Thread {

        private Counter counter;

        public MyThread(Counter counter) {
            this.counter = counter;
        }

        static int even = 0;

        @Override
        public void run() {

            try {
                if (even++ % 2 == 0)
                    counter.inc();
                else
                    counter.dec();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(counter.getCounter());
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        final Counter counter = new Counter(0);
        MyThread thread1 = new MyThread(counter);
        MyThread thread2 = new MyThread(counter);

        thread1.start();
        thread2.start();
    }
}
