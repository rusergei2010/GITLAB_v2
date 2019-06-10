package com.epam.split;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.stream.StreamSupport;

import org.junit.Test;

/**
 * Explorer characteristics and tryAdvance, forEachRemaining
 *
 * Spliterator supports parallel calculations unlike Iterators
 */
public class SpliteratorAPITest {

    @Test
    public void testTrySplit() throws InterruptedException {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        Spliterator original = list.spliterator();

        System.out.println("Estimated size = " + original.estimateSize()); // used by forEachRemaining to travere
        System.out.println("Exact Size = " + original.getExactSizeIfKnown()); // if CHARACTERISTICS = SIZED, then it should return exact size

        Spliterator splitedPart = original.trySplit();

        StreamSupport.stream(splitedPart, false).forEach(System.out::println);
        System.out.println("---");
        StreamSupport.stream(original, false).forEach(System.out::println);
    }

    @Test
    public void testRemainingInSpliterator(){
        List<String> names = new ArrayList<>();
        names.add("One");
        names.add("Quatra");
        names.add("Russia");

        // Getting Spliterator
        Spliterator<String> namesSpliterator = names.spliterator();

        // Traversing elements
        namesSpliterator.forEachRemaining(System.out::println);
    }

    @Test
    public void testAdvanceInSpliterator(){
        List<String> names = new ArrayList<>();
        names.add("One");
        names.add("Quatra");
        names.add("Russia");

        // Getting Spliterator
        Spliterator<String> namesSpliterator = names.spliterator();

        // Traversing One element
        // Inside of Every Spliterator there is a current index to traverse through it
        namesSpliterator.tryAdvance(System.out::println); // go through the all elements in Spliterator one by another
    }

    @Test
    public void testCharacteristics() {
        Collection<String> list = new LinkedList<>();
        Collection<String> set = new HashSet();
        Collection<String> linkedHashSet = new LinkedHashSet<>(1);
        Collection<String> treeSet = new TreeSet<>();

        System.out.println("LINKED LIST CHARACTERISTICS:");
        match(list.spliterator());
        System.out.println("HASH SET CHARACTERISTICS:");
        match(set.spliterator());
        System.out.println("LINKED HASH SET CHARACTERISTICS:");
        match(linkedHashSet.spliterator());
        System.out.println("TREE SET CHARACTERISTICS:");
        match(treeSet.spliterator());
    }


    @Test
    public void testLossCharacteristics() {
        Collection<String> list = new LinkedList<>();

        System.out.println("LINKED LIST CHARACTERISTICS:");
        match(list.spliterator());
        System.out.println("LINKED LIST FILTERED CHARACTERISTICS:");
        match(list.stream().filter(x -> true).spliterator());
    }

    @Test
    public void testEncounterCharacteristics() {
        Collection<String> list = new HashSet<>();

        System.out.println("ARRAY LIST CHARACTERISTICS:");
        match(list.spliterator());
        System.out.println("SORTED ARRAY LIST CHARACTERISTICS:");
        match(list.stream().sorted(Comparator.naturalOrder()).spliterator());
    }

    private static void match(Spliterator spliterator) {
        if (spliterator.hasCharacteristics(Spliterator.ORDERED)) {
            System.out.println("  ORDERED"); // like array with index (opposite to set)
        }

        if (spliterator.hasCharacteristics(Spliterator.DISTINCT)) {
            System.out.println("  DISTINCT");
        }

        if (spliterator.hasCharacteristics(Spliterator.SORTED)) {
            System.out.println("  SORTED"); // if elements has already been sorted
        }

        if (spliterator.hasCharacteristics(Spliterator.SIZED)) {
            System.out.println("  SIZED"); // the size is predefined
        }

        if (spliterator.hasCharacteristics(Spliterator.CONCURRENT)) {
            System.out.println("  CONCURRENT"); // can be used in concurrent calls
        }

        if (spliterator.hasCharacteristics(Spliterator.IMMUTABLE)) {
            System.out.println("  IMMUTABLE"); // source is unchanged
        }

        if (spliterator.hasCharacteristics(Spliterator.NONNULL)) {
            System.out.println("  NONNULL");
        }

        if (spliterator.hasCharacteristics(Spliterator.SUBSIZED)) {
            System.out.println("  SUBSIZED");
        }
    }
}
