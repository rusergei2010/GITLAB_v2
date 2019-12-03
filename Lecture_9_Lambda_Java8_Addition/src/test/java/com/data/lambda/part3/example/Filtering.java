package com.data.lambda.part3.example;

import com.data.Employee;
import com.data.JobHistoryEntry;
import com.data.Person;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class Filtering {
    // old style
    @Test
    public void filtering0() {
        final List<Employee> employees =
                Arrays.asList(
                        new Employee(
                                new Person("Bob", "Galt", 30),
                                Arrays.asList(
                                        new JobHistoryEntry(2, "dev", "epam"), // 1 dev
                                        new JobHistoryEntry(1, "dev", "google")
                                )),
                        new Employee(
                                new Person("John", "Galt", 30),
                                Arrays.asList(
                                        new JobHistoryEntry(2, "dev", "epam"), // 2 dev
                                        new JobHistoryEntry(1, "dev", "google")
                                )),
                        new Employee(
                                new Person("John", "Doe", 40),
                                Arrays.asList(
                                        new JobHistoryEntry(3, "QA", "yandex"),
                                        new JobHistoryEntry(1, "QA", "epam"),
                                        new JobHistoryEntry(1, "dev", "abc")
                                )),
                        new Employee(
                                new Person("John", "White", 50),
                                Arrays.asList(
                                        new JobHistoryEntry(5, "QA", "epam")
                                ))
                );

        // Johns with dev experience worked in epam more then 1 year
        final List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            long count = employee.getJobHistory().stream()
                    .filter(h -> h.getPosition().equals("dev"))
                    .filter(h -> h.getDuration() > 1)
                    .filter(h -> h.getEmployer().equals("epam"))
                    .filter(h -> employee.getPerson().getFirstName().equals("John"))
                    .count();
            if (count != 0) {
                result.add(employee);
            }
            // TODO: add the filter to store DEVELOPERS from EPAM with more than 1 year of experience in this collection
            // TODO: DEV name should be 'John'
            // Store all matching output in 'result' collection
        }
        TestCase.assertEquals(1, result.size());
    }


    public static class FilterUtil<T> {
        private final List<T> list;

        public FilterUtil(List<T> list) {
            this.list = list;
        }

        public List<T> getList() {
            return list;
        }

        // [T] -> (T -> boolean) -> [T]
        private FilterUtil<T> filter(Predicate<T> condition) {
            final List<T> res = new ArrayList<T>();
            for (T t : list) {
                if (condition.test(t)) {
                    res.add(t);
                }
            }

            return new FilterUtil<T>(res);
        }
    }

    private static boolean hasDevExperience(Employee e) {
        return new FilterUtil<>(e.getJobHistory())
                .filter(j -> j.getPosition().equals("dev")) // TODO: fix here
                .getList()
                .size() > 0;
    }

    private static boolean workedInEpamMoreThenOneYear(Employee e) {
        return new FilterUtil<>(e.getJobHistory())
                .filter(j -> j.getEmployer().equals("epam"))
                .filter(j -> j.getDuration() > 1)
                .getList()
                .size() > 0;
    }

    @Test
    public void filtering() {
        final List<Employee> employees =
                Arrays.asList(
                        new Employee(
                                new Person("Bob", "Galt", 30),
                                Arrays.asList(
                                        new JobHistoryEntry(2, "dev", "epam"),
                                        new JobHistoryEntry(1, "dev", "google")
                                )),
                        new Employee(
                                new Person("John", "Galt", 30),
                                Arrays.asList(
                                        new JobHistoryEntry(2, "dev", "epam"),
                                        new JobHistoryEntry(1, "dev", "google")
                                )),
                        new Employee(
                                new Person("John", "Doe", 40),
                                Arrays.asList(
                                        new JobHistoryEntry(3, "QA", "yandex"),
                                        new JobHistoryEntry(1, "QA", "epam"),
                                        new JobHistoryEntry(1, "dev", "abc")
                                )),
                        new Employee(
                                new Person("John", "White", 50),
                                Arrays.asList(
                                        new JobHistoryEntry(5, "QA", "epam")
                                ))
                );

        final FilterUtil<Employee> johns = new FilterUtil<>(employees)
                .filter(e -> e.getPerson().getFirstName().equals("John"));
        final List<Employee> filteredList = johns
                .filter(Filtering::hasDevExperience) //
                .filter(Filtering::workedInEpamMoreThenOneYear)
                .getList();

        assertEquals(filteredList.size(), 1);
        assertEquals(filteredList.get(0).getPerson(), new Person("John", "Galt", 30));
    }

    public static class LazyFilterUtil<T> {
        private final List<T> list;
        private final Predicate<T> condition;

        public LazyFilterUtil(List<T> list, Predicate<T> condition) {
            this.list = list;
            this.condition = condition;
        }

        public LazyFilterUtil(List<T> list) {
            this(list, null);
        }

        public List<T> force() {
            if (condition == null) {
                return list;
            }

            return new FilterUtil<>(list).filter(condition).getList();
        }

        private LazyFilterUtil<T> filter(Predicate<T> condition) {
            final Predicate<T> combinedCondition = combine(this.condition, condition);
            return new LazyFilterUtil<T>(list, combinedCondition);
        }

        private Predicate<T> combine(Predicate<T> c1, Predicate<T> c2) {
            if (c1 == null) {
                return c2;
            }

            return c1.and(c2);
        }
    }

    private static boolean workedInEpamMoreThenOneYearLazy(Employee e) {
        return new LazyFilterUtil<>(e.getJobHistory())
                .filter(j -> j.getEmployer().equals("epam"))
                .filter(j -> j.getDuration() > 1)// TODO: fix it in this line (1,2 or more?)
                .force()
                .size() > 0;
    }


    @Test
    public void lazy_filtering() {
        final List<Employee> employees =
                Arrays.asList(
                        new Employee(
                                new Person("John", "Galt", 30),
                                Arrays.asList(
                                        new JobHistoryEntry(2, "dev", "epam"),
                                        new JobHistoryEntry(1, "dev", "google")
                                )),
                        new Employee(
                                new Person("John", "Doe", 40),
                                Arrays.asList(
                                        new JobHistoryEntry(3, "QA", "yandex"),
                                        new JobHistoryEntry(1, "QA", "epam"),
                                        new JobHistoryEntry(1, "dev", "abc")
                                )),
                        new Employee(
                                new Person("John", "White", 50),
                                Collections.singletonList(
                                        new JobHistoryEntry(5, "QA", "epam")
                                ))
                );

        final List<Employee> filteredList = new LazyFilterUtil<>(employees)
                .filter(e -> e.getPerson().getFirstName().equals("John"))
                .filter(Filtering::hasDevExperience)
                .filter(Filtering::workedInEpamMoreThenOneYearLazy)
                .force();

        assertEquals(filteredList.size(), 1);
        assertEquals(filteredList.get(0).getPerson(), new Person("John", "Galt", 30));
    }

}
