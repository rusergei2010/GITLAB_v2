package com.epam.optional.printer;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.optional.domain.Vehicle;

public class ConsoleOutput extends Printer<Vehicle> {

    public ConsoleOutput() {
        super(new Output() {

            @Override public void print(final byte[] code) {
                System.out.println(new String(code, StandardCharsets.UTF_8));
            }
        });
    }

    @Override public void print(final List<? extends Vehicle> printable) {
        printable.forEach(v -> {
            v.printToOutput(output);
        });
    }
}
