package com.epam.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.BeforeClass;
import org.junit.Test;

import com.epam.optional.domain.Car;
import com.epam.optional.domain.Gear;
import com.epam.optional.domain.Vehicle;
import com.epam.optional.printer.ConsoleOutput;
import com.epam.optional.printer.Printer;

import static com.epam.optional.domain.Gear.AUTO;
import static com.epam.optional.domain.Gear.MANUAL;
import static com.epam.optional.domain.Gear.UNKNOWN;
import static org.junit.Assert.assertEquals;

public class OptionalTest {

    final static Printer printer = new ConsoleOutput();
    static Vehicle[] cars;

    @BeforeClass
    public static void testCar(){
        // Array Car Code
        cars = createCars(10);
    }

    private static Vehicle[] createCars(final int count) {
        final Vehicle[] vehicle = new Car[10];
        IntStream.range(0, 10).forEach(i -> {
            vehicle[i] = Car.builder(i % 2 == 0 ? Gear.AUTO: MANUAL)
                    .setBrand("Audi")
                    .setDistance(10 + i * 10)
                    .build();
        });

        return vehicle;
    }

    /**
     * Example 1: Abstract Factory implementation with Builder, Bridge and Visitor patterns
     */
    @Test
    public void testPrint() {
        printer.print(Stream.of(cars).collect(Collectors.toList()));
    }

    /**
     * TODO: 1
     *  - Create a model with a House -> Building in a same way as for a Car -> Vehicle
     *  - Print to the Console but change the format to be XML instead of Plain Text (StringBuilder)
     */
    @Test
    public void testHouseXMLPrint() {

    }

    /**
     * Example 2:
     * Explorer the Exception throwing
     */
    @Test(expected = RuntimeException.class)
    public void testOptionalMethods() {
        final Car car = Car.builder(MANUAL)
//                .setBrand("Audi") // omit brand name
                .setDistance(10)
                .build();
        assertEquals(MANUAL, car.gear().get()); // Optional.get()
        assertEquals("BMW", car.brand().orElseGet(() -> "BMW")); // Optional.orElseGet()
        car.brand().orElseThrow(() -> new RuntimeException("Illegal Parameter")); // Optional.orElseThrow()
    }

    /**
     * Example 3:
     * Study the basic Optional operator methods: map(), orElse(), filter()
     */
    @Test
    public void testOptionalException() {
        final List<? super Gear> gears = new ArrayList(); // Low of Covariant compliance for Generic Collection
        for (final Vehicle car : cars) {
            final String gear = car.gear()
                    .filter(g -> MANUAL == g) // Optional.filter(Predicate)
                    .map(Gear::name) // Optional.map()
                    .orElse(Gear.UNKNOWN.name()); // Optional.orElse()

            // Discover (resolve) enum by name
            gears.add(Gear.valueOf(gear));
        }
        assertEquals(gears.stream().filter(UNKNOWN::equals).count(), 5);
        assertEquals(gears.stream().filter(MANUAL::equals).count(), 5);
        assertEquals(gears.stream().filter(AUTO::equals).count(), 0);
    }

    /**
     * Example 3:
     * Study the basic Optional operator methods: flatMap()
     */
    @Test
    public void testOptionalFlatMap() {
        for (final Vehicle car : cars) {
            Optional<Optional<String>> o = Optional.of(car).map(Vehicle::brand); //
            Optional<String> s = Optional.of (car).flatMap(Vehicle::brand);
            assertEquals(o.get(), s);
        }
    }

}