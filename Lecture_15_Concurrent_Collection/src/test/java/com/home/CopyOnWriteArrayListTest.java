package com.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class CopyOnWriteArrayListTest {

    /**
     * @throws InterruptedException
     */
    @Test(expected = ConcurrentModificationException.class)
    public void mutableCollections() throws InterruptedException {
        ArrayList<Integer> mutable = new ArrayList<>();

        IntStream.range(0, 10).forEach(mutable::add);
        new Thread(() -> {
            IntStream.range(0, 10).forEach((i) -> {
                mutable.add(-1);
                sleep(100);
            });

        }).start();

        // TODO: remove this delay if necessary
        //sleep(3000);

        // concurrent modification in the list
        final Iterator<Integer> iterator = mutable.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next()); // Why does iterator.next cause the exception?
            sleep(100);
        }
    }

    // Immutable collection
    @Test
    public void immutableCollections() throws InterruptedException {
        ArrayList<Integer> mutableList = new ArrayList<>();
        IntStream.range(0, 10).forEach(mutableList::add);
        List<Integer> immutable = Collections.unmodifiableList(mutableList);

        AtomicReference<Throwable> exception = new AtomicReference<>(); // Use of reference from lambda and thread

        Thread thread = new Thread(() -> {
            IntStream.range(0, 10).forEach((i) -> {
                try {
                    immutable.add(-1);
                } catch (Throwable t) {
                    // TODO: uncomment this if necessary
                     exception.set(t);
                    throw new RuntimeException(t);
                }
                sleep(100);
            });

        });
        thread.start();

        // concurrent modification in the list
        // prevent from returning list from Immutable Class in multi-threaded apps
        final Iterator<Integer> iterator = immutable.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            sleep(100);
        }

        thread.join();
        org.junit.Assert.assertNotNull(exception.get());
    }

    // Solution 2 - convert to CopyOnWriteArrayList
    // TODO: Fix the timeout. Adjust 'TIMEOUT' to let more element to be added to the CopyOnWriteArrayList before thread-safe Iteration is started
    @Test
    public void syncCollections() throws InterruptedException {
        final long TIMEOUT = 300;
        List<Integer> cowArrayList = new CopyOnWriteArrayList<>();

        Thread thread = new Thread(() -> {
            IntStream.range(0, 10).forEach((i) -> {
                cowArrayList.add(-1);
                sleep(100);
            });
        });
        thread.start();

        Thread.sleep(TIMEOUT); // wait till two elements are added

        final Iterator<Integer> cowIterator = cowArrayList.iterator(); // COWIterator is used
        int coutner = 0;
        while (cowIterator.hasNext()) {
            System.out.println(Thread.currentThread().getName() +
                    "      COW Iterator element : " + cowIterator.next() + "; Size = " + cowArrayList.size());
            coutner++;
        }

        assertEquals(3, coutner);

        thread.join();
        Thread.sleep(100);
        // add the list to the end after the Iterator is released
        cowArrayList.forEach(System.out::print);
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
