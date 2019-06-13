package com;

public class Util {

    public static void sleep(final int mil) throws InterruptedException {
        try {
            Thread.sleep(mil);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
