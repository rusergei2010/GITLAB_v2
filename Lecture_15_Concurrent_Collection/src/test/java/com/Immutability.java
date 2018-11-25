package com;

import com.model.Car;
import com.model.Owner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

public class Immutability {


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
        List<Integer> mutable = new CopyOnWriteArrayList<>();

        IntStream.range(0, 10).forEach(mutable::add);


        Thread thread = new Thread(() -> {
            IntStream.range(0, 10).forEach((i) -> {
                mutable.add(-1);
                System.out.println(Thread.currentThread().getName() + ": Added value to array: " + (-1) + "; Size = " + mutable.size());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("########################################\n" +
                    "Complete Adding new Values to mutable array. Size = " + mutable.size() + "\n" +
                    "########################################");

        });

        thread.start();

        Thread.sleep(100);

        // concurrent modification in the list
        final Iterator<Integer> iterator = mutable.iterator();
        System.out.println("########################################");
        System.out.println(Thread.currentThread().getName() + " - current thread; Size = " + mutable.size()); // size is not defined for 100%
        System.out.println("########################################");

        while (iterator.hasNext()) {

            System.out.println(Thread.currentThread().getName() + " - Print: " + iterator.next() + "; Size = " + mutable.size());
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
        mutable.forEach(System.out::print);
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}