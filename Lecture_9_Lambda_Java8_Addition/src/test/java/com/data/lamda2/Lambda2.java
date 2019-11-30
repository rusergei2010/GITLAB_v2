package com.data.lamda2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        // males = new ArrayList<>();

        // ###############################
        // ########## TASK 1 #############
        // ###############################
        // Filter all MALES

        List<Person> males = list.stream().filter(p -> p.getGender().equals(Person.Gender.MALE)).collect(Collectors.toList());
        assertArrayEquals(males.toArray(), Arrays.asList(person1, person2).toArray());

        // ###############################
        // ########## TASK 2 #############
        // ###############################
        // Filter all MALES greater than 10

        males = males.stream().filter(p -> p.getAge() > 10).collect(Collectors.toList());

//        for (Person p : list) { // replace with com.data.lambda and stream().forEach()
//            // TODO: make Java 8 filter and (p) -> {} statement inside
//            if (p.gender == Person.Gender.MALE && p.age > 10) { // replace with '::' access
//                males.add(p);
//            }
//        }

        assertArrayEquals(males.toArray(), Arrays.asList(person2).toArray());


        // ###############################
        // ########## TASK 3 #############
        // ###############################
        // Filter all MALES elder than 10 and include FEMALE of any age. Then calculate total age of them.
        List<Person> peopleWithSalary = new ArrayList<>();


        // TODO: make one : Integer result = people.stream()....filter()....map()....sum();
//        for (Person p : list) { // replace with com.data.lambda and stream().forEach()
//            // TODO: make Java 8 filter and (p) -> {} statement inside
//            if ((p.gender == Person.Gender.MALE && p.age > 10) || (p.gender == Person.Gender.FEMALE)) { // replace with '::' access
//                peopleWithSalary.add(p);
//            }
//        }
        // use map() to convert to Integer and .sum() to collect the total age

//        int age = 0;
//        for (Person p : peopleWithSalary) {
//            age = age + p.age;
//        }
        Integer age = list.stream()
                .filter(
                        p -> p.getGender().equals(Person.Gender.FEMALE) ||
                        p.getGender().equals(Person.Gender.MALE) && p.getAge() > 10)
                .mapToInt(p -> p.getAge())
                .sum();

        assertEquals(age.intValue(), 32);


        // ###############################
        // ########## TASK 4 #############
        // ###############################
        // TODO: Apply StringBuilder and access to static method  in a stream expression

        StringBuilder sb = new StringBuilder();
//        for (Person p : list) {
//          // TODO: make the statement
//          //  (p) -> {
//          //      sb.append(getName); // with ::
//          // }
//            sb.append(getName(p) + " ");
//        }
        list.stream().forEach(p -> sb.append(getName(p) + " "));

        assertEquals("Andrey Evgenii Vala ", sb.toString());
    }

    private String getName(Person p) {
        return p.getName();
    }
}
