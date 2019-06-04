package prepare.lecture;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyExample {

    private static class Counter {

        //
        //        public void inc() throws InterruptedException {
        //            synchronized(this) {
        //                Thread.sleep(100);
        //                // wait()
        //                // notify()
        //                System.out.println(Thread.currentThread() + " Executed");
        //            }
        //        }
        //
        Lock lock = new ReentrantLock();
        Condition condition;
        {
            condition = lock.newCondition();
        }

        public void inc() {
            System.out.println(Thread.currentThread() + " Try Acquire Lock");
            lock.lock(); // synchronized(this) {
            System.out.println(Thread.currentThread() + " Acquired Lock");

            try {
                condition.await(); // wait()
                System.out.println(Thread.currentThread() + " Executed");
            } catch (Exception e) {
            } finally {
                System.out.println(Thread.currentThread() + " UnLock");
                lock.unlock(); // release lock
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        new Thread(() -> {
            counter.inc();
        }).start();

        Thread.sleep(100);

        counter.lock.lock(); // enter synchronized
        try {
        } finally {
            counter.condition.signal(); // notify()
            counter.lock.unlock(); // exit synchronized
        }


        System.out.println("Exit...");
    }


    public static void print(final Thread thread, final String s) {
        System.out.println("Thread: " + thread.getName() + "; " + s);
    }

}
