package com.epam.functional.domain;

import java.util.Optional;

/**
 * <
 * Do not compose get() and set() methods in parent class.
 * - Keep the code clean and add logical related methods only: drive(), beep(), stop(), etc
 * - Characteristics of the Vehicle like color, mass, seats, etc need to be allocated in another parent class or better injected
 *   This is a concept of separation of concerns (Every class should be responsible for its purpose or baseline)
 *   Allocation of characteristics (color, mass) in another class complies with the pattern "Bridge"
 *   Group the parameters together and isolate groups from each other
 */
public abstract class Vehicle {

    public abstract Integer drive(Integer distance);

    public abstract Optional<String> brand();

    public abstract Optional<Gear> gear();
}
