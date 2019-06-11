package com.data;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {
    private final Person person;
    private final List<JobHistoryEntry> jobHistory;

    public Employee(Person person, List<JobHistoryEntry> jobHistory) {
        this.person = person;
        this.jobHistory = jobHistory;
    }

    public Employee withPerson(Person p) {
        return new Employee(p, jobHistory);
    }

    public Employee withJobHistory(List<JobHistoryEntry> h) {
        return new Employee(person, h);
    }

    public Person getPerson() {
        return person;
    }

    public List<JobHistoryEntry> getJobHistory() {
        return new ArrayList<>(jobHistory);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("model" + person)
                .append("jobHistory" + jobHistory)
                .toString();
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Employee employee = (Employee) o;
        return Objects.equals(person, employee.person) &&
                Objects.equals(jobHistory, employee.jobHistory);
    }

    @Override public int hashCode() {
        return Objects.hash(person, jobHistory);
    }
}
