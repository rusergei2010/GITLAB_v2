package com.epam.optional.printer;


public interface Printable<T extends Output> {
    public void printToOutput(T output);
}
