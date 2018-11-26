//package com;
//
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Created by Admin on 8/6/2018.
// */
//public class ATestThreadState {

//    /**
//     * Fill in the gaps and insert instructions to make code executable
//     * @throws InterruptedException
//     */
//    @Test
//    public void testThreadState() throws InterruptedException {
//        // TODO: change instantiation
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1000; i++) {
//                    try {
//                        Thread.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100; i++) {
//                    try {
//                        Thread.sleep(20);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        assertEquals(thread1.getState(), Thread.State.NEW);
//        assertEquals(thread2.getState(), Thread.State.NEW);
//
//
//        thread1.start();
//        thread2.start();
//        // TODO: fill the gap
//        // TODO: fill the gap
//
//        assertEquals(thread1.getState(), Thread.State.RUNNABLE);
//        assertEquals(thread2.getState(), Thread.State.RUNNABLE);
//
//        // Add delay if necessary
//        // TODO: fill the gap
//
//        thread1.join(1000);
//        thread2.join(1000);
//
//        // threads should run task to be put on hold
//        assertEquals(thread1.getState(), Thread.State.TIMED_WAITING);
//        assertEquals(thread2.getState(), Thread.State.TIMED_WAITING);
//        assertEquals(Thread.currentThread().getState(), Thread.State.RUNNABLE);
//    }
//
//    private Thread createThread() {
//        final Thread thread = new Thread();
//        return thread;
//    }
//
//    private Thread createThread(Runnable runnable) {
//        final Thread thread = new Thread(runnable);
//        return thread;
//    }

}
