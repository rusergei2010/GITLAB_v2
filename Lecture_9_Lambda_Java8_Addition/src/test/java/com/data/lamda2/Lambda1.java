package com.data.lamda2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Lambda1 {


    /**
     * TODO: Convert List of strings to the list of Upper Case strings
     * "Berlin", "Machine" -> "BERLIN", "MACHINE"
     */
    @Test
    public void testUpperCase() {
        List<String> list = new ArrayList<String>();
        list.add("Berlin");
        list.add("Work");
        list.add("speed");
        List<String> result =  list.stream()
                .map(String::toUpperCase) // TODO: replace with other com.data.lambda '::' expression
                .collect(Collectors.toList());


        assertArrayEquals(Arrays.asList("BERLIN", "WORK", "SPEED").toArray(), result.toArray());
    }


    /**
     * Count words in all sentences and sum it up
     */
    @Test
    public void testCountAllWordsInAllSentences() {
        List<String> list = new ArrayList<String>();
        list.add("Berlin One");
        list.add("Work Two Thres");
        list.add("speed man man");

        long sum1 = list.stream().mapToInt(str-> new StringTokenizer(str).countTokens()).sum();
            // TODO: replace '1' with com.data.lambda expression

        Long sum = sum1;
        assertEquals(sum, Long.valueOf("8"));

    }
}
