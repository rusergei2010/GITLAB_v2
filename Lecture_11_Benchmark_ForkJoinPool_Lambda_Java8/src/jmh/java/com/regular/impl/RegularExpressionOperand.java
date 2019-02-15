package com.regular.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RegularExpressionOperand {

    public static OperatorValue value(final String value) {
        return new VALUE(value);
    }

    public static OperatorValue and(final OperatorValue first, final OperatorValue second, final OperatorValue... operands) {
        List<OperatorValue> list = new ArrayList<>();
        list.add(first);
        list.add(second);
        list.addAll(Arrays.asList(operands));
        return new AND(list);
    }

    public static OperatorValue or(final OperatorValue first, final OperatorValue second, final OperatorValue... operands) {
        List<OperatorValue> list = new ArrayList<>();
        list.add(first);
        list.add(second);
        list.addAll(Arrays.asList(operands));
        return new OR(list);
    }

    public static class VALUE implements OperatorValue {

        private String value;

        private VALUE(final String value) {
            this.value = value;
        }

        @Override
        public String build() {
            return value;
        }
    }

    public static class AND extends Operator {

        private AND(final List<OperatorValue> operands) {
            this.operands = Collections.unmodifiableList(operands);
        }

        @Override
        public String build() {
            if (operands.isEmpty()) return "";
            return operands.stream().map(AND::wrap).collect(Collectors.joining("", "(", ".*)"));
        }

        private static String wrap(OperatorValue v) {
            return "(?=.*" + v.build() + ")";
        }

    }

    public static class OR extends Operator {

        private OR(final List<OperatorValue> operands) {
            this.operands = Collections.unmodifiableList(operands);
        }

        @Override
        public String build() {
            if (operands.isEmpty()) return "";
            return operands.stream().map(OR::wrap).collect(Collectors.joining("|", "(", ")"));
        }

        private static String wrap(OperatorValue v) {
            return "(.*" + v.build() + ".*)";
        }
    }
}