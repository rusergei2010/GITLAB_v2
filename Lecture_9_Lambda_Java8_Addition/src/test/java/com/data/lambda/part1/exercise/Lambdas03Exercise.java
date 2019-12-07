package com.data.lambda.part1.exercise;

import org.junit.Test;

import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;

public class Lambdas03Exercise {

    private interface GenericProduct<T> {
        T prod(T a, int i);

        default T twice(T t) {
            return prod(t, 2);
        }
    }

    @Test
    public void generic0() {
        final GenericProduct<Integer> prod = (x,y) -> x*y; // TODO: Use anonymous class

        assertEquals(prod.prod(3, 2), Integer.valueOf(6));
    }

    @Test
    public void generic1() {
        final GenericProduct<Integer> prod = Math::multiplyExact; // TODO: Use statement lambda

        assertEquals(prod.prod(3, 2), Integer.valueOf(6));
    }

    @Test
    public void generic2() {
        final GenericProduct<Integer> prod = (x,y) ->{return x*y;} ; // TODO: Use expression lambda

        assertEquals(prod.prod(3, 2), Integer.valueOf(6));
    }

    private static String stringProd(String s, int i) {
        final StringBuilder sb = new StringBuilder();
        for (int j = 0; j < i; j++) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Test
    public void strSum() {
        final GenericProduct<String> prod = (ch, num)->{
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < num; i++) {
                sb.append(ch);
            }
                return sb.toString();
        };
        assertEquals(prod.prod("a", 2), "aa");
    }
}
