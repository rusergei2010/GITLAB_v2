package com.epam.functional.home;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import com.epam.functional.model.*;

import static org.junit.Assert.assertEquals;

/**
 * Fix it
 */
public class StreamTest {

    static List<Student> students;

    @BeforeClass
    public static void beforeClass() {

        students = Arrays.asList(
                new Student("Alex", Student.Speciality.Physics, 1),
                new Student("Rika", Student.Speciality.Biology, 4),
                new Student("Julia", Student.Speciality.Biology, 2),
                new Student("Steve", Student.Speciality.History, 4),
                new Student("Mike", Student.Speciality.Finance, 1),
                new Student("Hinata", Student.Speciality.Biology, 2),
                new Student("Richard", Student.Speciality.History, 1),
                new Student("Kate", Student.Speciality.Psychology, 2),
                new Student("Sergey", Student.Speciality.ComputerScience, 4),
                new Student("Maximilian", Student.Speciality.ComputerScience, 3),
                new Student("Tim", Student.Speciality.ComputerScience, 5),
                new Student("Ann", Student.Speciality.Psychology, 1)
        );
    }

    @Test
    public void groupingBy() {
        Map<Integer, List<Student>> collectByYear = new HashMap<>();

        collectByYear = students.stream().collect(Collectors.groupingBy(Student::getYear));

        assertEquals(5, collectByYear.size());
        assertEquals(4, collectByYear.get(1).size());
    }

    @Test
    public void groupingByAndCount() {
        Map<Student.Speciality, Long> countStudents = new HashMap<>();

        countStudents = students.stream().collect(Collectors.groupingBy(Student::getSpeciality, Collectors.counting()));

        assertEquals(3, countStudents.get(Student.Speciality.Biology).intValue());
        assertEquals(1, countStudents.get(Student.Speciality.Physics).intValue());
    }

    @Test
    public void partitionBy() {
        Map<Boolean, List<Student>> collectHistorians = new HashMap<>();

        collectHistorians = students.stream().collect(Collectors.groupingBy(Student::isHistory));

        assertEquals(2, collectHistorians.get(true).size());
    }

    @Test
    public void groupBySpecialityNameAndThenYear() {
        /**
         * TODO: Group by the naturally sorted Speciality name first and the Year after
         * Sorting is necessary at first.
          */

        Map<Student.Speciality, Map<Integer /*year*/, List<Student>>> groupedBySpecAndYear = new HashMap<>();

        groupedBySpecAndYear = students.stream()

                .sorted(Comparator
                        .comparing(Student::getSpeciality, Comparator.comparing(Enum::name))
                        .thenComparing(Student::getYear)
                )

                .collect(Collectors.groupingBy(
                        Student::getSpeciality,
                        LinkedHashMap::new,
                        Collectors.groupingBy(Student::getYear)));

        assertEquals(new Student("Alex", Student.Speciality.Physics, 1)
                ,groupedBySpecAndYear.get(Student.Speciality.Physics).get(1).get(0));
        assertEquals(new Student("Sergey", Student.Speciality.ComputerScience, 4)
                ,groupedBySpecAndYear.get(Student.Speciality.ComputerScience).get(4).get(0));
    }
}

