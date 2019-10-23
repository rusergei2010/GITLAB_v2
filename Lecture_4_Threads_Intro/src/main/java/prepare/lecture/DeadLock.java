package prepare.lecture;

import java.util.stream.IntStream;

public class DeadLock {

    private static volatile DeadLock instance;


    public static volatile int counter = 0;
    public static Object MUTEX = new Object();
    public static Object MUTEX2 = new Object();

    public static DeadLock getInstance() {
//        counter++;
        if (instance == null) {
            synchronized (/*DeadLock.class*/ MUTEX) {
                if (instance == null) {
                    instance = new DeadLock();
                }
            }
        }
        return instance;
    }

    private DeadLock() {
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread() {

            @Override public void run() {
                DeadLock instance = DeadLock.getInstance();
                IntStream.range(0, 1000).forEach((i) -> {
                    instance.inc();
                });
                try {
                    synchronized (MUTEX2) {
                        MUTEX2.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        Thread thread2 = new Thread() {

            @Override public void run() {
                DeadLock instance = DeadLock.getInstance();
                IntStream.range(0, 1000).forEach((i) -> {
                    instance.inc();
                });
                try {
                    synchronized (MUTEX2) {
                        MUTEX2.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread1.start();
        thread2.start();


        Thread.sleep(1000000);
        System.out.println("Counter = " + counter);
    }

    private synchronized void inc() {
        counter++;
        dec();
    }

    private synchronized void dec(){
        counter--;
    }
}
