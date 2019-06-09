package com.epam.optional.printer;

import java.util.List;
import java.util.Objects;


public abstract class Printer <T extends Printable> {
    protected final Output output;

    public Printer(final Output output) {
        Objects.requireNonNull(output);
        this.output = output;
    }

    public abstract void print(final List<? extends T> printable);
}
