package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Admin on 8/6/2018.
 */
public class AppTestThreadState {

  /**
   * Fill in the gaps and insert instructions to make code executable
   *
   * @throws InterruptedException
   */
  @Test
  public void testThreadState() throws InterruptedException {
    // TODO: change instantiation

    Runnable job = () -> {
      try {
        int counter;
        for (int i = 0; i < 100_000_000; i++) {
          counter = i;
          i++;
            this.createThread();
            Thread.sleep(200);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };
    Thread thread1 = createThread(job);
    Thread thread2 = createThread(job);

    assertEquals(Thread.State.NEW, thread1.getState());
    assertEquals(Thread.State.NEW, thread2.getState());

    // TODO: fill the gap
    // TODO: fill the gap
    thread1.start();
    thread2.start();

    assertEquals(Thread.State.RUNNABLE, thread1.getState());
    assertEquals(Thread.State.RUNNABLE, thread2.getState());

    // Add delay if necessary
    // TODO: fill the gap
    thread1.sleep(2000);
    thread2.sleep(1000);
    // threads should run task to be put on hold
    assertEquals(Thread.State.TIMED_WAITING, thread1.getState());
    assertEquals(Thread.State.TIMED_WAITING, thread2.getState());
    assertEquals(Thread.State.RUNNABLE, Thread.currentThread().getState());
  }

  private Thread createThread() {
    final Thread thread = new Thread();
    return thread;
  }

  private Thread createThread(Runnable runnable) {
    final Thread thread = new Thread(runnable);
    return thread;
  }

}
