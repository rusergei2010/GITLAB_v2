package prepare.lecture;

import java.util.stream.IntStream;

public class MainThread {

    private static class Singleton {

        private volatile static Singleton instance;

        public static Singleton getInstance() {
            if (instance == null) {
                synchronized (Singleton.class) {
                    if (instance == null) {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }

        private Singleton() {
        }

    }


    private static class Counter {

        private Object list = null;

        // writer
        public synchronized void add(Object o) throws InterruptedException {

            while(list != null) {
                this.wait();
            }
            list = o;
            this.notify();
        }

        // reader
        public synchronized Object pop() throws InterruptedException {
            while(list == null) {
                this.wait();
            }

            final Object o = list;
            list = null;
            notify();
            return o;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Thread write = new Thread(() -> {
            IntStream.range(0, 10).forEach(i -> {
                try {
                    counter.add(Integer.valueOf(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        write.start();


        Thread read = new Thread(() -> {
            IntStream.range(0, 10).forEach(i -> {
                try {
                    System.out.println(counter.pop());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        read.start();

        System.out.println("Exiting");
    }

    private static void print(final Thread thread) {
        System.out.println("State: " + thread.getState());
    }
}
