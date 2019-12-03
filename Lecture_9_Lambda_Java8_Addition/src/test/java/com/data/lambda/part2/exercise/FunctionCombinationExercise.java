package com.data.lambda.part2.exercise;

import com.data.Person;
import com.data.lambda.part1.practice.Lamdas04;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class FunctionCombinationExercise {

    @Test
    public void personHasNotEmptyLastNameAndFirstName0() {
        // Person -> boolean
        final Predicate<Person> validate = p -> !p.getFirstName().isEmpty() && !p.getLastName().isEmpty();

        assertEquals(true, validate.test(new Person("a", "b", 0)));
        assertEquals(false, validate.test(new Person("", "b", 0)));
        assertEquals(false, validate.test(new Person("a", "", 0)));
    }

    // TODO
    // negate1: (Person -> boolean) -> (Person -> boolean)
    private Predicate<Person> negate1(Predicate<Person> test) {
        return p -> !test.test(p);
    }

    // TODO
    // validateFirstNameAndLastName: (Person -> boolean, Person -> boolean) -> (Person -> boolean)
    private Predicate<Person> validateFirstNameAndLastName(Predicate<Person> t1, Predicate<Person> t2) {
        return p -> {
            if (t1.test(p) && t2.test(p)) {

                return true;
            } else return false;

            // TODO
        };
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstName1() {
        final Predicate<Person> hasEmptyFirstName = p -> p.getFirstName().isEmpty();
        final Predicate<Person> hasEmptyLastName = p -> p.getLastName().isEmpty();

        final Predicate<Person> validateFirstName = negate1(hasEmptyFirstName);
        final Predicate<Person> validateLastName = negate1(hasEmptyLastName);

        final Predicate<Person> validate = validateFirstNameAndLastName(validateFirstName, validateLastName);

        assertEquals(true, validate.test(new Person("a", "b", 0)));
        assertEquals(false, validate.test(new Person("", "b", 0)));
        assertEquals(false, validate.test(new Person("a", "", 0)));
    }

    // TODO
    // negate: (T -> boolean) -> (T -> boolean)
    private <T> Predicate<T> negate(Predicate<T> test) {
        return p -> {
            if (!test.test(p)) {
                return false;
            }
            return true;
        };
        // TODO
    }

    // TODO
    // and: (T -> boolean, T -> boolean) -> (T -> boolean)
    private <T> Predicate<T> and(Predicate<T> t1, Predicate<T> t2) {
        return p -> {
            if (!t1.test(p) && !t2.test(p)) {
                return true;
            }
            return false;
        };
        // TODO
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstName2() {
        final Predicate<Person> hasEmptyFirstName = p -> p.getFirstName().isEmpty();
        final Predicate<Person> hasEmptyLastName = p -> p.getLastName().isEmpty();

        final Predicate<Person> validateFirstName = negate(hasEmptyFirstName); // TODO use negate
        final Predicate<Person> validateLastName = negate(hasEmptyLastName);
        ; // TODO use negate

        final Predicate<Person> validate = and(validateFirstName, validateLastName); // TODO use and

        assertEquals(true, validate.test(new Person("a", "b", 0)));
        assertEquals(false, validate.test(new Person("", "b", 0)));
        assertEquals(false, validate.test(new Person("a", "", 0)));
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstName3() {
        final Predicate<Person> hasEmptyFirstName = p -> p.getFirstName().isEmpty();
        final Predicate<Person> hasEmptyLastName = p -> p.getLastName().isEmpty();

        Function<Predicate, Predicate<Person>> negate = Predicate::negate;
        Predicate<Person> apply = negate.apply(hasEmptyFirstName);
        Predicate<Person> apply2 = negate.apply(hasEmptyLastName);
        BiFunction<Predicate, Predicate<? super Person>, Predicate<Person>> and = Predicate::and;
        Predicate<Person> andApply = and.apply(apply, apply2);


        //Predicate<Person>::negate,hasEmptyFirstName
//        Stream.of("ad").forEach(System.out::println);
//        {person::getFirstName::negate(hasEmptyFirstName);};// TODO use Predicate::negate

        assertEquals(true, andApply.test(new Person("a", "b", 0)));
        assertEquals(false, andApply.test(new Person("", "b", 0)));
        assertEquals(false, andApply.test(new Person("a", "", 0)));
    }

}
