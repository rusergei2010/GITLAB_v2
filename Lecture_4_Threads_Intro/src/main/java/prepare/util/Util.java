package prepare.util;

public class Util {
    private static Object mutex = new Object();
    public static void threadSleep(final int mil){

        try {
            synchronized (mutex) {
                mutex.wait(mil);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
