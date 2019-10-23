package com.mycompany.prepare;


/**
 * JMS example
 * Deadlock demo (with mission control show up)
 * Volatile usages (AppVolatile)
 * Thread interruption (Interrupt)
 * Gain and release Lock for sleep and wait (LockReturnOnWaitSleep) - console output of entered threads in critical sections
 * MultipleSyncEntry: return lock on examples StackOverflow (can catch exception?) in DoubleSyncEntry
 * Singleton realization (SingletonVolatile) - repeat
 *
 * Dispute on Thread Safe objects: Immutable objects Mutability, defend from concurrent object access
 *
 * Advanced topics here:
     * Lock interface (External Lock) - advantageous
     * ReentrantLock1 - replacement for 'synchronized' - equivalent schema
     * ReentrantLock2 - liveness, heavy operation (workaround - tryLock() or tryLock(<time>)
     * WaitNotifyDemoWithQueue - demo the wait and notify work on the Locking object
     * CacheReentranReadWriteLockExample - ReentrantReadWriteLock (see ReadWriteLock interface)
     *
 * Super Advanced: ThreadLocal (ThreadLocalEx)
 *
 */
public class App {
}
