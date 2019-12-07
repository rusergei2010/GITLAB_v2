package com.data.lambda.part2.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.data.Person;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.LongSupplier;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;
import org.junit.Test;

public class OtherClasses {

    @Test
    // Supplier: () -> T
    // Arity 0
    public void suppliers() {
        final Supplier<String> strSupplier = () -> new String("a");

        assertEquals("a", strSupplier.get());
        assert strSupplier.get() != strSupplier.get();

        final BooleanSupplier booleanSupplier = () -> true;
        assertEquals(true, booleanSupplier.getAsBoolean());

        final IntSupplier intSupplier = () -> ThreadLocalRandom.current().nextInt();
        assertNotEquals(intSupplier.getAsInt(), intSupplier.getAsInt());

        final LongSupplier longSupplier = () -> Integer.MAX_VALUE + 100L;
        assert longSupplier.getAsLong() > Integer.MAX_VALUE;

        final DoubleSupplier doubleSupplier = () -> 0.1;
        assertEquals(0.1, doubleSupplier.getAsDouble(), 0.0001);
    }

    @Test
    // Consumer: T -> void
    // Arity 1
    public void consumers() {
        final Consumer<String> stringConsumer = System.out::println;

        stringConsumer.accept("Some string");

        // IntConsumer
        // LongConsumer
        // DoubleConsumer
    }

    @Test
    // UnaryOperator: T -> T
    // Arity 1
    public void unaryOperator() {
        final UnaryOperator<String> reverse = s -> new StringBuilder(s).reverse().toString();

        assertEquals("abc", reverse.apply("cba"));

        final IntUnaryOperator negate = i -> -i;

        assertEquals(-1, negate.applyAsInt(1));

        // LongUnaryOperator
        // DoubleUnaryOperator
    }

    @Test
    // Function: T -> R
    // Arity 1
    public void functions() {
        final Function<Person, String> getFirstName = Person::getFirstName;
        assertEquals("a", getFirstName.apply(new Person("a", "b", 33)));

        final ToIntFunction<Person> getAge = Person::getAge;
        assertEquals(33, getAge.applyAsInt(new Person("a", "b", 33)));

        final IntFunction<Person> withAge = i -> new Person("a", "b", i);
        assertEquals(new Person("a", "b", 666), withAge.apply(666));

        final DoubleToLongFunction doubleToLong = d -> (long)d;
        assertEquals(666, doubleToLong.applyAsLong(666.66));
    }

    @Test
    // Predicate: T -> boolean
    // Arity 1
    public void predicates() {
        final Predicate<String> isEmpty = String::isEmpty;
        assertEquals(true, isEmpty.test(""));

        final IntPredicate positive = i -> i > 0;
        assertEquals(false, positive.test(-1));

        // LongPredicate
        // DoublePredicate
    }

    @Test
    // BinaryOperator: (T, T) -> T
    // Arity 2
    public void binaryOperators() {
        final BinaryOperator<String> concat = String::concat;
        assertEquals("ab", concat.apply("a", "b"));

        final IntBinaryOperator sum = (i1, i2) -> i1 + i2;
        assertEquals(3, sum.applyAsInt(1, 2));

        // LongBinaryOperator
        // DoubleBinaryOperator
    }

    @Test
    // BiFunction: (A, B) -> R
    // Arity 2
    public void biFunction() {
        final BiFunction<Person, String, Person> changeFirstName = Person::withFirstName;
        assertEquals(new Person("c", "b", 0), changeFirstName.apply(new Person("a", "b", 0), "c"));

        final ToIntBiFunction<Person, String> toIntBiFunction = (p, s) -> p.getAge() + s.length();
        assertEquals(10, toIntBiFunction.applyAsInt(new Person("", "", 8), "ab"));

        // ToLongBiFunction
        // ToDoubleBiFunction

        // no IntLongToDouble, etc
    }

    @Test
    // BiPredicate: (A, B) -> boolean
    // Arity 2
    public void biPredicate() {
        final BiPredicate<String, Person> checkFirstName = (s, p) -> s.equals(p.getFirstName());
    }

    @Test
    // BiConsumer: (A, B) -> void
    // Arity 2
    public void biConsumers() {
        final BiConsumer<Person, String> biConsumer = null;

        final ObjIntConsumer<String> checkLength = null;
    }

    private interface PersonFactory {
        Person create(String name, String lastName, int age);
    }

    // ((String, String, int) -> Person, String) -> (String, Int) -> Person
    private BiFunction<String, Integer, Person> partiallyApply(
            PersonFactory pf,
            String lastName) {
        return (name, age) -> pf.create(name, lastName, age);
    }

    // ((String, String, int) -> Person) -> String -> String -> Int -> Person
    private Function<String, Function<String, IntFunction<Person>>> curry(
            PersonFactory pf) {
        return name -> lastName -> age -> pf.create(name, lastName, age);
    }

    public void currying() {
        // (String, String, int) -> Person
        final PersonFactory factory = Person::new;


        final BiFunction<String, Integer, Person> doe =
                (name, age) -> factory.create(name, "Doe", age);

        final Person mother = doe.apply("Samanta", 33);
        final Person father = doe.apply("Bob", 33);
        final Person son = doe.apply("John", 33);


        // String -> String -> int -> Person
        final Function<String, Function<String, IntFunction<Person>>> curried =
                name -> (lastName -> age -> factory.create(name, lastName, age));

        final Function<String, IntFunction<Person>> john =
                curried.apply("John");

        final IntFunction<Person> johnDoeWithoutAge =
                john.apply("Doe");

        assertEquals(new Person("John", "Doe", 22), johnDoeWithoutAge.apply(22));
        assertEquals(new Person("John", "Doe", 33), johnDoeWithoutAge.apply(33));
    }
}
