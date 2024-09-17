package com.data.lambda.part2.exercise;

import com.data.Person;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class ArrowNotationExercise {

    @Test
    public void getAge() {
        // Person -> Integer
        final Function<Person, Integer> getAge = (p)->p.getAge(); // TODO

        assertEquals(Integer.valueOf(33), getAge.apply(new Person("", "", 33)));
    }

    @Test
    public void compareAges() {
        // TODO use BiPredicate
        // compareAges: (Person, Person) -> boolean
        BiPredicate<Person,Person> compareAges = (p1,p2)->p1.getAge() == p2.getAge();
        //throw new UnsupportedOperationException("Not implemented");
        assertEquals(true, compareAges.test(new Person("a", "b", 22), new Person("c", "d", 22)));
    }



    @Test
    public void getAgeOfPersonWithTheLongestFullName() {
        // TODO
        // getFullName: Person -> String
        final Function<Person, String> getFullName = null;

        // TODO
        // ageOfPersonWithTheLongestFullName: (Person -> String) -> (Person, Person) -> int
        // TODO use ageOfPersonWithTheLongestFullName(getFullName)
        final BiFunction<Person, Person, Integer> ageOfPersonWithTheLongestFullName = (p1,p2)->{
            String str1 = p1.getFirstName()+p1.getLastName();
            String str2 = p2.getFirstName()+p2.getLastName();
            if (str1.length() > str2.length())
                return p1.getAge();
            return p2.getAge();
        };

        assertEquals(
                Integer.valueOf(1),
                ageOfPersonWithTheLongestFullName.apply(
                        new Person("a", "b", 2),
                        new Person("aa", "b", 1)));
    }
}
