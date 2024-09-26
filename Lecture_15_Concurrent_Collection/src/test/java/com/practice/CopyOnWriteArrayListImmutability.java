package com.practice;

import com.model.Car;
import com.model.Owner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CopyOnWriteArrayListImmutability {


    /**
     * Use the Builder pattern to code the immutable objects
     */
    @Test
    public void testImmutability() {
        Car.CarBuilder builder = Car.CarBuilder.builder();
        builder
                .withName("Vovlva")
                .withOwner(new Owner("Vova", "Vladimirov"));

        Car car = builder.build();

        System.out.println(car);
    }

    /**
     * The same concept as for the iteration over the Map (ExecutionException will be thrown there)
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


        // concurrent modification in the list
        // prevent from returning list from Immutable Class in multi-threaded apps
        final Iterator<Integer> iterator = mutable.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            sleep(100);
        }
    }

    // Solution 1 - convert to Immutable Collection
    @Test
    public void immutableCollections() throws InterruptedException {
        ArrayList<Integer> mutableList = new ArrayList<>();
        IntStream.range(0, 10).forEach(mutableList::add);
        List<Integer> immutable = Collections.unmodifiableList(mutableList);

        new Thread(() -> {
            IntStream.range(0, 10).forEach((i) -> {
                immutable.add(-1);
                sleep(100);
            });

        }).start();


        // concurrent modification in the list
        // prevent from returning list from Immutable Class in multi-threaded apps
        final Iterator<Integer> iterator = immutable.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            sleep(100);
        }
    }

    // Solution 2 - convert to CopyOnWriteArrayList
    @Test
    public void syncCollections() throws InterruptedException {
        List<Integer> cowArrayList = new CopyOnWriteArrayList<>();

        IntStream.range(0, 10).forEach(cowArrayList::add);

        Thread thread = new Thread(() -> {
            IntStream.range(0, 10).forEach((i) -> {
                cowArrayList.add(-1);
                System.out.println(Thread.currentThread().getName() + ": Modify: Add value to array: " + (-1) + "; Size = " + cowArrayList.size());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("########################################\n" +
                    "Complete Adding new Values to mutable array. Size = " + cowArrayList.size() + "\n" +
                    "########################################");

        });

        thread.start();

        Thread.sleep(100); // added at least 10 + 2 elements (-1) by that moment

        // ########################################################
        // Concurrent modification in the list
        // Make a snapshot of the Iterator at this concrete moment while it is being modified in the Thread
        // ########################################################
        System.out.println("########################################");
        System.out.println(Thread.currentThread().getName() + " - current thread; Size = " + cowArrayList.size()); // size is not defined for 100%
        System.out.println("########################################");

        final Iterator<Integer> cowIterator = cowArrayList.iterator(); // COWIterator is used
        while (cowIterator.hasNext()) {
            System.out.println(Thread.currentThread().getName() +
                    "      COW Iterator element : " + cowIterator.next() + "; Size = " + cowArrayList.size());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread.join();
        Thread.sleep(100);
        System.out.println("After modification");
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
