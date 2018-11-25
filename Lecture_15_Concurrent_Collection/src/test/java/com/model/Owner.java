package com.model;

// Immutability simplifies Concurrent apps
public class Owner {
    private final String name;
    private final String lastName;

    public Owner(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}
