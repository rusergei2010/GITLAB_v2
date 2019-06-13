package com.epam.executors.addition.exchanger;

import java.util.concurrent.Exchanger;

/**
 * Exchanger is like bound two way SynchronizedQueue (bi-directional)
 * But it is faster because uses CAS algo
 * See http://lmax-exchange.github.io/disruptor/files/Disruptor-1.0.pdf
 * See http://lmax-exchange.github.io/disruptor/
 */
public class ExchangerTest {

    Exchanger<String> exchanger = new Exchanger();

    private class Producer implements Runnable {
        private String queue;

        @Override
        public void run() {
            try {
                //create tasks & fill the queue
                //exchange the full queue for a empty queue with Consumer
                queue = exchanger.exchange("Ready Queue");
                System.out.println(Thread.currentThread().getName() + " now has " + queue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class Consumer implements Runnable {
        private String queue;

        @Override
        public void run() {
            try {
                //do procesing & empty the queue
                queue = exchanger.exchange("Empty Queue");
                System.out.println(Thread.currentThread().getName() + " now has " + queue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void start() {
        new Thread(new Producer(), "Producer").start();
        new Thread(new Consumer(), "Consumer").start();
    }

    public static void main(String[] args) {

        new ExchangerTest().start();

    }
}
