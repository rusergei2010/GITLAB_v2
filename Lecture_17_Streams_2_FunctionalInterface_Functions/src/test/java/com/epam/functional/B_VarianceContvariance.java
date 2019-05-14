package com.epam.functional;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;
import com.epam.functional.domain.Vehicle;

public class B_VarianceContvariance {

    private static final class StreamSupplier implements Supplier<Optional<Integer>> {
        private static final AtomicInteger atomic = new AtomicInteger(0);
        @Override public Optional<Integer> get() {
            return Optional.of(atomic.getAndAdd(1));
        }
    }

    // PECS
    @FunctionalInterface
    public interface MyPredicate<T extends Vehicle> {

        public boolean test(T t);

        public default boolean testBMW(T t) {
            return t.brand().filter("BMW"::equals).isPresent();
        }
    }

    @Test
    public void testVarianceAndCovariance() {
        // Arrays are covariant (since Java 1.0)
        // They act like Collection<? extends T>

        Number[] numbers = new Number[2]; // OK
        numbers[0] = 1;
        numbers[1] = 2;

        numbers[0] = 0.1; // OK
        printNumbers(numbers);

        // Array of type T[] may contain elements of type T and its subtypes.
        numbers = new Integer[2]; // OK
        // numbers[1] = 0.2; // Error in Runtime

        // It produces a runtime exception, because Java knows at runtime
        // that the “actual object” numbers is actually an array of Integer


        // ############################
        // ######### Generics #########
        // ############################
        // With generic types, Java has no way of knowing at
        // runtime the type information of the type parameters, due to type erasure.
        // Erasure - purifying the generics and compiler remain plain Objects of byte code
        final List<Number> listNumbers = new ArrayList<>(2);
        listNumbers.add(1);
        listNumbers.add(0.2);

        final List<Integer> listInts = new ArrayList<>(2);
        listInts.add(1);
        listInts.add(2);

        // Therefore, since covariant types are read-only
        // and contravariant types are write-only (loosely speaking),
        // we can derive the following rule of thumb: “Producer extends, consumer super”.

        final Consumer<List<Number>> printer = new Consumer<List<Number>>() {

            @Override public void accept(final List<Number> list) {
                list.forEach(System.out::println);
            }
        };



        printer.accept(listNumbers);
//        printer.accept(listInts); // gives compile error since List<Number> != List<Integer> - it is a rule of CONTRvariance VS COvariance for arrays
//        producer.apply(new ArrayList<Integer>());


        // ########################################################
        // A Workaround would be introduction of wildcards !!! This divide read/write for covariance and contrvariance
        // This will impose conditions and restictions on the read/write operations in collections
        // ########################################################

        final Consumer<List<Number>> printerContrvariance = new Consumer<List<Number>>() {

            @Override public void accept(final List<Number> list) {
                list.forEach(System.out::println);
            }
        };

        List<Number> nums = new ArrayList<Number>();
        nums.add(1);
        nums.add(1.0);


        // In real applications with inheritance we deal with parent/child
        // But it is forbidden to do this (with wildcard setting upper and lower bounds):
        //      List<Number> nums_ = new ArrayList<Double>();
        // Though it is allowed to do:
        //      List<? extends Number> intNums = new ArrayList<Integer>();
        //      List<? extends Number> intNums = new ArrayList<Double>();
        // Or
        //      List<? super Integer> i = new ArrayList<Number>();
        //      List<? super Float> f = new ArrayList<Number>();

        // Covariance is broken for write operation in java
        // Wile Contrvariance is broken for reading

        List<? super Integer> numForProducer = new ArrayList<>();
        numForProducer.add(new Integer(0)); // super -> producer -> allowed
//        Integer i_ = numForProducer.get(0); // super -> consumer -> denied
    }


    @Test
    public void operationsCovariantContrvariantOperations() {
        List<Number> num = new ArrayList<Number>();
//        List<Integer> superInt = num; // denied - intuitive
        List<? super Integer> superInt = num; // for this read is denied
        num.add(1);
        Number a = num.get(0);
        superInt.add(1);
//        Number i = superInt.get(0); // The superInt can contain any super class of Integer and cannot be casted to Integer. Read operation is denied.

        List<Integer> ints = new ArrayList<>();
//        List<Number> extInts = ints; // forbidden for covariance (allowed for arrays)

        List<Integer> i = new ArrayList<Integer>();
        List<? extends Number> n = new ArrayList<Number>();
        n = i;

        List<? extends Number> extInts = ints; // for this write is denied

        num.add(1);
        num.get(0);
//        extInts.add(1); // extInts can contain any sub-type of Number. So it can be Double. Write Operation is denied.
        extInts.get(0);
    }

    private void printNumbers(final Number[] numbers) {
        for (final Number n : numbers) {
            System.out.println(n);
        }
    }

    @Test
    public void testConsumerExtends(){

        // map int to String
        Collection<Integer> ints = new ArrayList<>();
        List<? extends String> str = function.apply(ints); // except Integer, Double, Float

        String s = str.get(0); // Consumer - extends - OK

    }

    // P extends
    // C super
    final Function<Collection<? extends Number>, List<? extends String>> function = new Function<Collection<? extends Number>, List<? extends String>>() {

        public List<? extends String> apply(final Collection<? extends Number> nums) {
            List<String> s = new ArrayList<>();
            for (final Number num : nums) {
                s.add(String.valueOf(num));
            }
            return s;
        }
    };
}










