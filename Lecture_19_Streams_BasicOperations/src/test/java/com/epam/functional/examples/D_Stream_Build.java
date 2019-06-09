package com.epam.functional.examples;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.epam.functional.domain.Car;
import com.epam.functional.domain.Gear;
import com.epam.functional.domain.Vehicle;

import static com.epam.functional.domain.Gear.MANUAL;

@Ignore
public class D_Stream_Build {

    static Vehicle[] cars;

    @BeforeClass
    public static void testCar() {
        // Array Car Code
        cars = createCars(10);
    }

    private static Vehicle[] createCars(final int count) {
        final Vehicle[] vehicle = new Car[10];
        IntStream.range(0, 10).forEach(i -> {
            vehicle[i] = Car.builder(i % 2 == 0 ? Gear.AUTO : MANUAL)
                    .setBrand(i % 3 == 0 ? "Audi" : "BMW")
                    .setDistance(10 + i * 10)
                    .build();
        });

        return vehicle;
    }

    /**
     * Creation of Stream and execution collecting results (map, filter) - Basic Example
     */
    @Test
    public void testPrint() {
        final List<String> collection = Stream.of(cars).map(Vehicle::gear)
                .map(Optional::get)
                .filter(c -> c == MANUAL)
                .map(Enum::name)
                .collect(Collectors.toList());
        collection.forEach(System.out::println);
    }

    private static final class StreamSupplier implements Supplier<Optional<Integer>> {

        private static final AtomicInteger atomic = new AtomicInteger(0);

        @Override public Optional<Integer> get() {
            return Optional.of(atomic.getAndAdd(1));
        }
    }

    /**
     * Working with Stream Builders
     */
    @Test
    public void testStreamCreation() throws IOException {

        StreamSupplier supplier = new StreamSupplier();
        // Create stream from Supplier Functional Interface
        // infinite stream unless 'limit' is added
        List<Optional<Integer>> collect = Stream.generate(supplier).limit(15).collect(Collectors.toList());
        collect.forEach(x -> System.out.println(x.get()));

        // infinite Stream from iterate()
        Stream<Integer> stream2 = Stream.iterate(0, x -> x + 1);
        System.out.println("Iterator of elements: " + stream2.limit(30)
                .collect(StringBuilder::new, (sb, s) -> sb.append(s).append(" "), StringBuilder::append));

        // from array
        String[] array = { "1", "2" };
        Stream.of(array).forEach(System.out::println);

        final Path path = Files.createTempFile("Temp", "File");
        BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.WRITE);
        bufferedWriter.append("123");
        bufferedWriter.append("\n");
        bufferedWriter.append("123");
        bufferedWriter.append("\n");
        bufferedWriter.close();

        Stream<String> lines = Files.lines(Paths.get(path.toUri()));
        lines.forEach(System.out::println);

        // Stream from String chars
        IntStream stream = "Andrey".chars();
        stream.mapToObj(x -> String.valueOf((char) x).toUpperCase()).forEach(System.out::println);
        try {
            Stream<Integer> objects = stream.boxed();
        } catch (Throwable t) {
            System.err.println(t.getMessage());
            stream = "Andrey".chars();
            Stream<String> objects = stream.boxed().map(x -> String.valueOf((char) x.intValue()).toUpperCase());

            String s = objects.collect(StringBuilder::new, (c, x) -> c.append(x).append(x), StringBuilder::append)
                    .toString();
            System.out.println(s);
        }

        // Empty
        System.out.println("Empty: " + Stream.<String>empty().collect(Collectors.joining(", ")));


        // concatenate streams
        Stream.concat(Stream.<String>of("Concatenated: "), Stream.<Integer>concat(Stream.of(1,2), Stream.of(3,4)))
                .map(x -> x).forEach(System.out::println);
    }
}

