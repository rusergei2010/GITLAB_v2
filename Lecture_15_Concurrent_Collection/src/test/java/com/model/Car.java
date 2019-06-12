package com.model;

public class Car {
    private final String name;
    private final Owner owner;


    private Car(String name, Owner owner) {
        this.name = name;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", owner=" + owner +
                '}';
    }

    public static class CarBuilder {
        private String name;
        private Owner owner;

        private CarBuilder() {
        }

        public static CarBuilder builder() {
            return new CarBuilder();
        }

        public CarBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CarBuilder withOwner(Owner owner) {
            this.owner = owner;
            return this;
        }

        public Car build() {
            if (name == null) throw new AssertionError();
            if (owner == null) throw new AssertionError();

            return new Car(name, owner);
        }
    }

    public String getName() {
        return name;
    }

    public Owner getOwner() {
        return owner;
    }
}