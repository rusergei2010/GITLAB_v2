package com.epam.functional.domain;

import java.util.Objects;
import java.util.Optional;

public class Person {

    private String name;
    private Integer age;

    private Vehicle[] cars;

    private Person(String name, Integer age){
        Objects.requireNonNull(name);
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public Optional<Integer> getAge() {
        return Optional.ofNullable(age);
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public Vehicle[] getCars() {
        return cars;
    }

    public void setCars(final Car[] cars) {
        this.cars = cars;
    }
}