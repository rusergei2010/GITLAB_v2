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
        final Function<Person, Integer> getAge = Person::getAge; // done

        assertEquals(Integer.valueOf(33), getAge.apply(new Person("", "", 33)));
    }

    @Test
    public void compareAges() {
        // TODO use BiPredicate
        // compareAges: (Person, Person) -> boolean
        BiPredicate<Person, Person> compareAges = (person1, person2) -> person1.getAge() == person2.getAge();

        assertEquals(true, compareAges.test(new Person("a", "b", 22), new Person("c", "d", 22)));
    }

    // TODO
    // getFullName: Person -> String
    Function<Person, String> getFullName = r -> r.getLastName() + " " + r.getFirstName();

    // TODO
    // ageOfPersonWithTheLongestFullName: (Person -> String) -> (Person, Person) -> int
   BiFunction<Person, Person, Integer> ageOfPersonWithTheLongestFullName = (a, b) -> {
        if (getFullName.apply(a).length() > getFullName.apply(b).length()){
            return a.getAge();
        } else {
            return b.getAge();
        }
    } ;

    @Test
    public void getAgeOfPersonWithTheLongestFullName() {
        // Person -> String
        final Function<Person, String> getFullName = this.getFullName; //

        // (Person, Person) -> Integer
        //
        final BiFunction<Person, Person, Integer> ageOfPersonWithTheLongestFullName = (person1, person2) -> {
            Integer P1FullNameLength = getFullName.apply(person1).length();
            Integer P2FullNameLength = getFullName.apply(person2).length();

            return P1FullNameLength >= P2FullNameLength ? person1.getAge() : person2.getAge();

        };

        assertEquals(
                Integer.valueOf(1),
                ageOfPersonWithTheLongestFullName.apply(
                        new Person("a", "b", 2),
                        new Person("aa", "b", 1)));
    }
}