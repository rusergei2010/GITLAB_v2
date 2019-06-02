package com.epam.functional.examples;

public class C_FunctionalInterface {
    @FunctionalInterface
    public interface MyLambda {
        public void run();
        //        public void calulate(); // forbidden

        static void calc(){
        }

        default void test(){
            System.out.println("TEST");
        }
    }
}










