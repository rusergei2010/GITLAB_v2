package com.epam.functional.practice;

import static com.epam.functional.model.Gear.MANUAL;

import com.epam.functional.model.Car;
import com.epam.functional.model.Gear;
import com.epam.functional.model.Vehicle;
import java.util.stream.IntStream;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * ##########                ###########################                #######################
 * # Source # -> Elements -> # Intermediate Operations # -> Elements -> # Terminal Operations #
 * ##########                ###########################                #######################
 *
 * Collection.stream         filter                                     forEach
 * Map.EntrySet.stream       map                                        collect
 * Arrays.stream(array)      flatMap                                    count
 * Stream.of("a", "b")       limit                                      reduce
 *                           findAny, findFirst
 *
 * Note:
 *  Stream will start execution when the terminal operation is called
 *  It is impossible to reuse the same Stream twice
 *
 */
@Ignore
public class StreamTest {

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
    }
}

