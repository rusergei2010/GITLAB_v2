package com.regular;

import com.regular.impl.OperatorValue;

import java.util.regex.Pattern;

import static com.regular.impl.RegularExpressionOperand.and;
import static com.regular.impl.RegularExpressionOperand.or;
import static com.regular.impl.RegularExpressionOperand.value;

public class TestApp {


    public static void main(String[] args) {
        // test 1
        testPattern("(((?=.*CAT)(?=.*DOG).*)|(.*COW.*))",
                or(
                        and(
                                value("CAT"),
                                value("DOG")
                        ),
                        value("COW"),
                        and()
                )
        );

        // test 2
        testPattern("(.*CAT)|(.*DOG)|(.*COW.*)",
                or(
                        or(
                                value("CAT"),
                                value("DOG")
                        ),
                        value("COW")
                )
        );

        // test 3
        testPattern("",

                and(
                        or(
                                value("CAT"),
                                value("DOG")
                        ),
                        value("COW")
                )
        );
    }

    private static void testPattern(String actual, OperatorValue operand) {
        Pattern patternExpected = Pattern.compile(actual);
        Pattern patternActual = Pattern.compile(operand.build());
        System.out.println("Expected: " + patternExpected.pattern());
        System.out.println("Actual  : " + patternActual.pattern());

        System.out.println(patternActual.matcher("askdhjsaCAT12784yhkanfCOW").matches());
        System.out.println(patternActual.matcher("asfklasjfDOGk12j4kljaklsf").matches());
        System.out.println(patternActual.matcher("askfaklsfjCATaksfaksfhjkDOGasklfhasl").matches());
        System.out.println(patternActual.matcher("asfjkljasCOWajshfjkashfCATaskdjlkasd").matches());
    }
}

/*
(.*CAT.*|.*DOG.*)(?=.*COW).*
 */