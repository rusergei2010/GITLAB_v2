package com.data.lambda.part1.practice;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Lambda03 {

    class Person {
        String name;
        Integer age;

        public Person(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    class MyComparator implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    @Test
    public void testLambda01() {

        Person person1 = new Person("Sergey");
        Person person2 = new Person("Andrey");
        Person person3 = new Person("Anton");

        person1.setAge(21);
        person2.setAge(18);
        person3.setAge(10);

        Person[] persons = new Person[]{person1, person2, person3};
        Person[] personsAged = new Person[persons.length];

        MyComparator myComparator = new MyComparator();

        int i = 0;
        for (Person p : persons) {
            if (p.getAge() >= 18) {
                personsAged[i++] = p;
            } else {
                personsAged[i++] = new Person("");
            }
        }


        List<Person> list = new ArrayList();


        // TODO: Uncomment this first
//        Arrays.sort(personsAged, myComparator); // reference

        // TODO: Uncomment another expression then (replace with this one)
        Arrays.sort(personsAged,
                (person1_, person2_) -> {
//                    person1_.setName("Lambda Expression");
                    list.add(person1_);
                    return person1_.name.compareTo(person2_.name); // statement
                });

        assertEquals("Sergey", personsAged[2].name);
        assertEquals(2, list.size());
    }

    public static int compare(Person o1, Person o2) {
        return o1.name.compareTo(o2.name);
    }

}
