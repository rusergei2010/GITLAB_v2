package com.epam.functional.examples;

import java.util.stream.Stream;

import org.junit.Test;

public class A_Lambda {

    @FunctionalInterface
    public interface MyLambda {
        public void run();
//        public void calulate(); // forbidden

        static void calc(){
        }

        default void test(){
            System.out.println("TEST");
        }
    }


    static class Person {
        private String name;
        private int age;
        private Gender gender;

        public Person(String name, int age, Gender gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public enum Gender {
            MALE,
            FEMALE
        }


        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public Gender getGender() {
            return gender;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    '}';
        }
    }

    interface MyPredicator<Person> {
        boolean test(Person p);
    }

    @Test
    public void main() {
        MyLambda lambda = () -> System.out.println("Print Me");
        // Lambdas can refer to each other if they they have the same parameters in the method (name can deffer)
        // Example:
        Runnable runnable = () -> System.out.println("Hello"); // resolved, since Runnable - functional interface


        lambda.run();


        // statement
        lambda = () -> {
            System.out.println("Print Me");
        };

        // reference to static method is also Lambda
        lambda = System.out::println;


        // Example
        MyPredicator<Person> predicator = (p) -> p.age > 17 && p.gender == Person.Gender.MALE;

        Stream.of(
                new Person("Kirill", 20, Person.Gender.MALE),
                new Person("Marry", 10, Person.Gender.FEMALE)
        )
                .filter((p) -> p.gender == Person.Gender.MALE)
                .map(A_Lambda::getGetAge)
                .mapToLong(Integer::longValue)
                .sum();

        System.out.println("Output: " + predicator.test(new Person("Anton", 19, Person.Gender.MALE)));
    }

    private static Integer getGetAge(Person person) {
        return person.age;
    }
}
