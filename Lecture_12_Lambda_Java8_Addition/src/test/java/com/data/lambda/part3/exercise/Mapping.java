package com.data.lambda.part3.exercise;

import com.data.Employee;
import com.data.JobHistoryEntry;
import com.data.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

public class Mapping {

    private static class MapHelper<T> {
        private final List<T> list;

        public MapHelper(List<T> list) {
            this.list = list;
        }

        public List<T> getList() {
            return list;
        }

        // [T] -> (T -> R) -> [R]
        // [T1, T2, T3] -> (T -> R) -> [R1, R2, R3]
        public <R> MapHelper<R> map(Function<T, R> f) {
            // TODO
            final List<R> res = new ArrayList<>();
            list.forEach(t -> res.add(f.apply(t)));
            return new MapHelper<>(res);
        }

        // [T] -> (T -> [R]) -> [R]

        // map: [T, T, T], T -> [R] => [[], [R1, R2], [R3, R4, R5]]
        // flatMap: [T, T, T], T -> [R] => [R1, R2, R3, R4, R5]
        public <R> MapHelper<R> flatMap(Function<T, List<R>> f) {
            final List<R> result = new ArrayList<R>();
            list.forEach((T t) ->
                    f.apply(t).forEach(result::add)
            );

            return new MapHelper<R>(result);
        }
    }

    @Test
    public void mapping() {
        final List<Employee> employees =
                Arrays.asList(
                        new Employee(
                                new Person("a", "Galt", 30),
                                Arrays.asList(
                                        new JobHistoryEntry(2, "dev", "epam"),
                                        new JobHistoryEntry(1, "dev", "google")
                                )),
                        new Employee(
                                new Person("b", "Doe", 40),
                                Arrays.asList(
                                        new JobHistoryEntry(3, "qa", "yandex"),
                                        new JobHistoryEntry(1, "qa", "epam"),
                                        new JobHistoryEntry(1, "dev", "abc")
                                )),
                        new Employee(
                                new Person("c", "White", 50),
                                Collections.singletonList(
                                        new JobHistoryEntry(5, "qa", "epam")
                                ))
                );

        final List<Employee> mappedEmployees =
                new MapHelper<>(employees)

                        .map(e -> e.withPerson(e.getPerson().withFirstName("John"))) // change name to John .map(e -> e.withPerson(e.getPerson().withFirstName("John")))
                        .map(e -> e.withJobHistory(addOneYear(e.getJobHistory()))) // add 1 year to experience duration .map(e -> e.withJobHistory(addOneYear(e.getJobHistory())))
                        .map(e -> e.withJobHistory(qaToUpper(e.getJobHistory()))) // replace qa with QA
                        .getList();

        final List<Employee> expectedResult =
                Arrays.asList(
                        new Employee(
                                new Person("John", "Galt", 30),
                                Arrays.asList(
                                        new JobHistoryEntry(3, "dev", "epam"),
                                        new JobHistoryEntry(2, "dev", "google")
                                )),
                        new Employee(
                                new Person("John", "Doe", 40),
                                Arrays.asList(
                                        new JobHistoryEntry(4, "QA", "yandex"),
                                        new JobHistoryEntry(2, "QA", "epam"),
                                        new JobHistoryEntry(2, "dev", "abc")
                                )),
                        new Employee(
                                new Person("John", "White", 50),
                                Collections.singletonList(
                                        new JobHistoryEntry(6, "QA", "epam")
                                ))
                );

        assertEquals(mappedEmployees, expectedResult);
    }


    private List<JobHistoryEntry> qaToUpper(List<JobHistoryEntry> jobHistory) {
        jobHistory.replaceAll(jobHistoryEntry ->
                jobHistoryEntry.getPosition().toUpperCase().equals("QA") ?
                        jobHistoryEntry.withPosition(jobHistoryEntry.getPosition().toUpperCase()) : jobHistoryEntry);
        return jobHistory;
    }

    private List<JobHistoryEntry> addOneYear(List<JobHistoryEntry> jobHistory) {
        jobHistory.replaceAll(jobHistoryEntry -> jobHistoryEntry.withDuration(1 + jobHistoryEntry.getDuration()));
        return jobHistory;
    }

    private static class LazyMapHelper<T, R> {

        Function<T, R> function;
        List<T> list;

        public LazyMapHelper(List<T> list, Function<T, R> function) {
            this.list = list;
            this.function = function;
        }

        public static <T> LazyMapHelper<T, T> from(List<T> list) {
            return new LazyMapHelper<>(list, Function.identity());
        }

        public List<R> force() {
            // TODO
            List<R> res = new ArrayList<>();
            list.forEach(t -> res.add(function.apply(t)));
            return res;
        }

        public <R2> LazyMapHelper<T, R2> map(Function<R, R2> f) {
            // TODO
            list.forEach(t -> function.apply(t));
            Function<T, R2> functionRes = t -> f.apply(function.apply(t));
            return new LazyMapHelper<>(list, functionRes);

        }

    }

    private static class LazyFlatMapHelper<T, R> {

        public LazyFlatMapHelper(List<T> list, Function<T, List<R>> function) {
        }

        public static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            throw new UnsupportedOperationException();
        }

        public List<R> force() {
            // TODO
            throw new UnsupportedOperationException();
        }

        // TODO filter
        // (T -> boolean) -> (T -> [T])
        // filter: [T1, T2] -> (T -> boolean) -> [T2]
        // flatMap": [T1, T2] -> (T -> [T]) -> [T2]

        public <R2> LazyFlatMapHelper<T, R2> map(Function<R, R2> f) {
            final Function<R, List<R2>> listFunction = rR2TorListR2(f);
            return flatMap(listFunction);
        }

        // (R -> R2) -> (R -> [R2])
        private <R2> Function<R, List<R2>> rR2TorListR2(Function<R, R2> f) {
            throw new UnsupportedOperationException();
        }

        // TODO *
        public <R2> LazyFlatMapHelper<T, R2> flatMap(Function<R, List<R2>> f) {
            throw new UnsupportedOperationException();
        }
    }


    @Test
    public void lazy_mapping() {
        final List<Employee> employees =
                Arrays.asList(
                        new Employee(
                                new Person("a", "Galt", 30),
                                Arrays.asList(
                                        new JobHistoryEntry(2, "dev", "epam"),
                                        new JobHistoryEntry(1, "dev", "google")
                                )),
                        new Employee(
                                new Person("b", "Doe", 40),
                                Arrays.asList(
                                        new JobHistoryEntry(3, "qa", "yandex"),
                                        new JobHistoryEntry(1, "qa", "epam"),
                                        new JobHistoryEntry(1, "dev", "abc")
                                )),
                        new Employee(
                                new Person("c", "White", 50),
                                Collections.singletonList(
                                        new JobHistoryEntry(5, "qa", "epam")
                                ))
                );

        final List<Employee> mappedEmployees =
                LazyMapHelper.from(employees)
                        .map(t -> t.withPerson(t.getPerson().withFirstName("John")))
                        .map(t -> t.withJobHistory(addOneYear(t.getJobHistory())))
                        .map(t -> t.withJobHistory(qaToUpper(t.getJobHistory())))
                        .force();

        final List<Employee> expectedResult =
                Arrays.asList(
                        new Employee(
                                new Person("John", "Galt", 30),
                                Arrays.asList(
                                        new JobHistoryEntry(3, "dev", "epam"),
                                        new JobHistoryEntry(2, "dev", "google")
                                )),
                        new Employee(
                                new Person("John", "Doe", 40),
                                Arrays.asList(
                                        new JobHistoryEntry(4, "QA", "yandex"),
                                        new JobHistoryEntry(2, "QA", "epam"),
                                        new JobHistoryEntry(2, "dev", "abc")
                                )),
                        new Employee(
                                new Person("John", "White", 50),
                                Collections.singletonList(
                                        new JobHistoryEntry(6, "QA", "epam")
                                ))
                );

        assertEquals(mappedEmployees, expectedResult);
    }
}
