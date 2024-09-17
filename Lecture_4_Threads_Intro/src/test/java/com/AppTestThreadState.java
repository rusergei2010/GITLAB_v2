package com;

import java.lang.Thread.State;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Admin on 8/6/2018.
 */
public class AppTestThreadState {

  /**
   * Fill in the gaps and insert instructions to make code executable
   */
  @Test
  public void testThreadState() throws InterruptedException {
    // TODO: change instantiation
    Thread thread1 = createThread(new Runnable() {
      @Override
      public void run() {
        int i = 0;
        while (true){
          i++;
          if (i==1000){
            try {
              Thread.currentThread().sleep(10000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    });
    Thread thread2 = createThread(new Runnable() {
      @Override
      public void run() {
        int i = 0;
        while (true){
          i++;
          if (i==1000){
            try {
              Thread.currentThread().sleep(10000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    });

    assertEquals(thread1.getState(), Thread.State.NEW);
    assertEquals(thread2.getState(), Thread.State.NEW);

    // TODO: fill the gap
    // TODO: fill the gap

    thread1.start();
    thread2.start();

    assertEquals(thread1.getState(), Thread.State.RUNNABLE);
    assertEquals(thread2.getState(), Thread.State.RUNNABLE);

    // Add delay if necessary
    // TODO: fill the gap

    thread1.sleep(500);
    thread2.sleep(500);

    // threads should run task to be put on hold
    assertEquals(thread1.getState(), Thread.State.TIMED_WAITING);
    assertEquals(thread2.getState(), Thread.State.TIMED_WAITING);
    assertEquals(Thread.currentThread().getState(), Thread.State.RUNNABLE);
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
