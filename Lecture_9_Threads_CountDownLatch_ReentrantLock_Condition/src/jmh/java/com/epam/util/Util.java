package com.epam.util;

import java.util.concurrent.ExecutorService;

public class Util {

    public static void threadSleep(final int mil){
        try {
            Thread.sleep(mil);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void shutdown(ExecutorService executorService, boolean b) {
        executorService.shutdown();
    }
}
