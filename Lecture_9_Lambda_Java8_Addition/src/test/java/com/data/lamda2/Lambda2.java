package com.data.lamda2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
        // TODO: replace with com.data.lambda all model access points

        Person person1 = new Person("Andrey", 10, Person.Gender.MALE);
        Person person2 = new Person("Evgenii", 20, Person.Gender.MALE);
        Person person3 = new Person("Vala", 12, Person.Gender.FEMALE);


        List<Person> list = Arrays.asList(person1, person2, person3);
        List<Person> males = new ArrayList<>();

        // ###############################
        // ########## TASK 1 #############
        // ###############################
        // Filter all MALES

        list.stream()
                .filter((p) -> p.gender == Person.Gender.MALE)
                .forEach((p) -> males.add(p));
        assertArrayEquals(males.toArray(), Arrays.asList(person1, person2).toArray());

        // ###############################
        // ########## TASK 2 #############
        // ###############################
        // Filter all MALES greater than 10

        males.clear();

        list.stream()
                .filter((p) -> p.gender == Person.Gender.MALE && p.age > 10)
                .forEach((p) -> males.add(p));

        assertArrayEquals(males.toArray(), Arrays.asList(person2).toArray());


        // ###############################
        // ########## TASK 3 #############
        // ###############################
        // Filter all MALES elder than 10 and include FEMALE of any age. Then calculate total age of them.
        List<Person> peopleWithSalary = new ArrayList<>();


        list.stream()
                .filter((p) -> (p.gender == Person.Gender.MALE && p.age > 10) || (p.gender == Person.Gender.FEMALE))
                .forEach((p) -> peopleWithSalary.add(p));
        // use map() to convert to Integer and .sum() to collect the total age

        int age = 0;
        for (Person p : peopleWithSalary) {
            age = age + p.age;
        }

        assertEquals(age, 32);


        // ###############################
        // ########## TASK 4 #############
        // ###############################
        // TODO: Apply StringBuilder and access to static method  in a stream expression

        StringBuilder sb = new StringBuilder();
        list.stream()
                .forEach((p) -> sb.append(getName(p) + " "));

        assertEquals("Andrey Evgenii Vala ", sb.toString());
    }

    private String getName(Person p) {
        return p.getName();
    }
}
