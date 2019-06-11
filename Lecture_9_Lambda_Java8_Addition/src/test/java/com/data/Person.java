package com.data;

import java.util.Objects;

public class Person {
    private final String firstName;
    private final String lastName;
    private final int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static String getLastName2(Person person) {
        return person.getLastName();
    }

    public int getAge() {
        return age;
    }

    public Person withFirstName(String firstName) {
        return new Person(firstName, lastName, age);
    }

    public Person withLastName(String lastName) {
        return new Person(firstName, lastName, age);
    }

    public Person withAge(int age) {
        return new Person(firstName, lastName, age);
    }

    public void print() {
        System.out.println(this.toString());
    }

    @Override public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Person person = (Person) o;
        return age == person.age &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName);
    }

    @Override public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }
}
