package com.epam.functional.model;

import java.util.Objects;

public class Student {

    private final String name;
    private final Speciality speciality;
    private final int year;

    public Student(final String name, final Speciality speciality, final int year) {

        this.name = name;
        this.speciality = speciality;
        this.year = year;
    }


    public String getName() {
        return name;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public int getYear() {
        return year;
    }

    public enum Speciality {
        Biology, ComputerScience, Economics, Finance,
        History, Philosophy, Physics, Psychology
    }

    public static boolean isHistory(Student student){
        return student.speciality == Speciality.History;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Student student = (Student) o;
        return year == student.year &&
                Objects.equals(name, student.name) &&
                speciality == student.speciality;
    }

    @Override public int hashCode() {
        return Objects.hash(name, speciality, year);
    }

    @Override public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", speciality=" + speciality +
                ", year=" + year +
                '}';
    }
}
