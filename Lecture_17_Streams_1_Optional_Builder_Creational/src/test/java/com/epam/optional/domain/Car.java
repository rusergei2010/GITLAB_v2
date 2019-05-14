package com.epam.optional.domain;

import java.util.Objects;
import java.util.Optional;

import com.epam.optional.printer.Output;

// Immutable
// Take care of immutability of the class and delegate its update to the patterns
// The direct mutability of Domain objects is risky
public class Car extends Vehicle {

    private int distance;
    private String brand;
    private Gear gear;

    private Car(final Gear gear, final int distance, final String brand) {
        Objects.requireNonNull(gear);
        this.distance = distance;
        this.brand = brand;
        this.gear = gear;
    }

    private Car(final Gear gear) {
        this(gear, 0, null);
    }

    @Override public Integer drive(final Integer distance) {
        return this.distance + distance;
    }

    @Override public Optional<String> brand() {
        return Optional.ofNullable(brand);
    }

    @Override public Optional<Gear> gear() {
        return Optional.of(gear);
    }

    public static Builder builder(final Gear gear) {
        return new Builder(gear);
    }

    @Override public void printToOutput(final Output output) {
        final StringBuilder sb = new StringBuilder();

        brand().ifPresent(b -> {
            sb.append("Brand: ").append(b).append("\n");
        });
        gear().ifPresent(g -> {
            sb.append("Gear: ").append(g).append("\n");
        });

        sb.append("Distance: ").append(distance).append("\n");

        output.print(sb.toString().getBytes());
    }

    public static class Builder {

        private Car car;

        private Builder(final Gear gear) {
            car = new Car(gear);
        }

        public Builder setDistance(final Integer drive) {
            car.drive(drive);
            return this;
        }

        public Builder setGear(final Gear gear) {
            car.gear = gear;
            return this;
        }

        public Builder setBrand(final String brand) {
            car.brand = brand;
            return this;
        }

        private void check() {
            Objects.requireNonNull(car.gear, "Gear is mandatory");
        }

        public Car build() {
            check();
            return car;
        }
    }
}
