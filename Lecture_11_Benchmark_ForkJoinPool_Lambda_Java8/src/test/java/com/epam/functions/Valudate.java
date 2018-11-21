package com.epam.functions;


import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class Valudate {
    public Valudate() {
    }

    @Test
    public void testNew() {
        BiFunction<Integer, Integer, Two> constructor = Two::new;

        Function<Integer, Two> constructorOneParam = Two::new;
        Two two = constructorOneParam.apply(20); // TODO: fix in this line
        assertEquals((long) two.i, (long) 22);

        Two two_ = constructor.apply(10, 10); // TODO: fix in this line
        assertEquals((long) two_.i, (long) 11);
    }

    @Test
    public void testFlatMap() {
        List<String> first = Arrays.asList("1", "2"); // TODO: fix in this line
        List<String> second = Arrays.asList("3", "4"); // TODO: fix in this line

        List<List<String>> combined = Arrays.asList(first, second);

        List<String> full = combined.stream().flatMap(Collection::stream).collect(Collectors.toList());
        full.stream().forEach(x -> {
            Assert.assertTrue("0335".contains(x));
        });
    }
}

class Two {

    public Integer i;
    public Integer j;

    public Two(Integer i) {
        this.i = i;
    }

    public Two(Integer i, Integer j) {
        this.i = i;
        this.j = j;
    }
}