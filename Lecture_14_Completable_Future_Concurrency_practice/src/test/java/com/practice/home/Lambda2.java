package com.practice.home;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.practice.home.Lambda2.Person.Gender;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * TODO: Convert to Lambda
 */
public class Lambda2 {

    public static class Person {

        String name;
        int age;
        Gender gender;

        public Person(String name, int age, Gender gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        enum Gender {
            MALE,
            FEMALE,
            UNKNOWN
        }
    }

    /**
     * Fill in the gaps and insert instructions to make code executable
     */
    @Test
    public void testLambda() throws InterruptedException {
        // TODO: replace with com.data.lambda all person access points

        Person person1 = new Person("Andrey", 10, Person.Gender.MALE);
        Person person2 = new Person("Evgenii", 20, Person.Gender.MALE);
        Person person3 = new Person("Vala", 12, Person.Gender.FEMALE);

        List<Person> list = Arrays.asList(person1, person2, person3);
        List<Person> males = new ArrayList<>();

        // ###############################
        // ########## TASK 1 #############
        // ###############################
        // Filter all MALES

        list.stream().filter(person -> person.getGender().equals(Gender.MALE)).forEach(males::add);
        assertArrayEquals(males.toArray(), Arrays.asList(person1, person2).toArray());

        // ###############################
        // ########## TASK 2 #############
        // ###############################
        // Filter all MALES greater than 10

        males.clear();

        list.stream().filter(person -> person.getGender().equals(Gender.MALE))
                .filter(person -> person.getAge() > 10).forEach(males::add);
        assertArrayEquals(males.toArray(), Arrays.asList(person2).toArray());

        // ###############################
        // ########## TASK 3 #############
        // ###############################
        // Filter all MALES elder than 10 and include FEMALE of any age. Then calculate total age of them.
        List<Person> peopleWithSalary = new ArrayList<>();

        list.stream()
                .filter(person -> (person.getGender().equals(Gender.MALE) && person.getAge() > 10)
                        || (person.getGender().equals(Gender.FEMALE)))
                .forEach(peopleWithSalary::add);
        // use map() to convert to Integer and .sum() to collect the total age

        int age = peopleWithSalary.stream().mapToInt(Person::getAge).sum();

        assertEquals(age, 32);

        // ###############################
        // ########## TASK 4 #############
        // ###############################
        // TODO: Apply StringBuilder and access to static method  in a stream expression

        StringBuilder sb = new StringBuilder();
        list.forEach(p -> sb.append(p.getName()).append(" "));

        assertEquals("Andrey Evgenii Vala ", sb.toString());
    }

    private String getName(Person p) {
        return p.getName();
    }
}
