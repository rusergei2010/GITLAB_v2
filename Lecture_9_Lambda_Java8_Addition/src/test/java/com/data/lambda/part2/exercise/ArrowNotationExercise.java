package com.data.lambda.part2.exercise;

import static org.junit.Assert.assertEquals;

import com.data.Person;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import org.junit.Test;

public class ArrowNotationExercise {

  @Test
  public void getAge() {
    // Person -> Integer
    final Function<Person, Integer> getAge = person -> person.getAge(); // TODO

    assertEquals(Integer.valueOf(33), getAge.apply(new Person("", "", 33)));
  }

  @Test
  public void compareAges() {
    // TODO use BiPredicate
    // compareAges: (Person, Person) -> boolean
    final BiPredicate<Person, Person> compareAges = (person, person2) -> person.getAge() == person2
        .getAge();
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
    final BiFunction<Person, Person, Integer> ageOfPersonWithTheLongestFullName =
        (person, person2) -> (person.getLastName().length() + person.getFirstName().length())
            > (person2.getLastName().length() + person2.getFirstName().length())
            ? person.getAge() : person2.getAge();

    assertEquals(Integer.valueOf(1),
        ageOfPersonWithTheLongestFullName
            .apply(new Person("a", "b", 2),
                new Person("aa", "b", 1)));
  }
}
