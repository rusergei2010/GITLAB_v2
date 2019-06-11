package com.data.lambda.part2.exercise;

import com.data.Person;
import com.data.lambda.part2.example.ArrowNotation;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

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
        BiPredicate<Person, Person> compareAges = new BiPredicate<Person, Person>() {
            @Override
            public boolean test(Person person, Person person2) {
                return person.getAge() == person2.getAge();
            }
        };

        assertEquals(true, compareAges.test(new Person("a", "b", 22), new Person("c", "d", 22)));
    }



    @Test
    public void getAgeOfPersonWithTheLongestFullName() {
        // TODO
        // getFullName: Person -> String
        final Function<Person, String> getFullName = new Function<Person, String>() {
            @Override
            public String apply(Person person) {
                return person.getFirstName() + " " + person.getLastName();
            }
        };

        Person Anton = new Person("anton", "ivanov", 21);

        String name = getFullName.apply(Anton);

        // TODO
        // ageOfPersonWithTheLongestFullName: (Person -> String) -> (Person, Person) -> int
        // TODO use ageOfPersonWithTheLongestFullName(getFullName)
        final BiFunction<Person, Person, Integer> ageOfPersonWithTheLongestFullName = new BiFunction<Person, Person, Integer>() {
            @Override
            public Integer apply(Person person, Person person2) {
                String p1Name = getFullName.apply(person);
                String p2Name = getFullName.apply(person2);

                if(p1Name.length() > p2Name.length()) return person.getAge();
                else return person2.getAge();
            }
        };

        assertEquals(
                Integer.valueOf(1),
                ageOfPersonWithTheLongestFullName.apply(
                        new Person("a", "b", 2),
                        new Person("aa", "b", 1)));
    }
}
