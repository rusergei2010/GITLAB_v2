package com.data.lambda.part2.exercise;

import com.data.Person;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static org.junit.Assert.assertEquals;

public class ArrowNotationExercise {

    @Test
    public void getAge() {
        // Person -> Integer
        final Function<Person, Integer> getAge = Person::getAge; // TODO

        assertEquals(Integer.valueOf(33), getAge.apply(new Person("", "", 33)));
    }

    @Test
    public void compareAges() {
        // TODO use BiPredicate
        final BiPredicate<Person, Person> compareAges = (first, second) -> first.getAge() == second.getAge();

        assertEquals(true, compareAges.test(new Person("a", "b", 22), new Person("c", "d", 22)));
    }


    @Test
    public void getAgeOfPersonWithTheLongestFullName() {
        // TODO
        final Function<Person, String> getFullName = person -> person.getFirstName() + " " + person.getLastName();


        // TODO
        // ageOfPersonWithTheLongestFullName: (Person -> String) -> (Person, Person) -> int
        // TODO use ageOfPersonWithTheLongestFullName(getFullName)
        final BiFunction<Person, Person, Integer> ageOfPersonWithTheLongestFullName = (p1, p2) ->
                getFullName.apply(p1).length() > getFullName.apply(p2).length() ? p1.getAge() : p2.getAge();

        assertEquals(
                Integer.valueOf(1),
                ageOfPersonWithTheLongestFullName.apply(
                        new Person("a", "b", 2),
                        new Person("aa", "b", 1)));
    }
}
