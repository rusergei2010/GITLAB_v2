package prepare.util;

public class Util {
    static Object mutex = new Object();
    public static void threadSleep(final int mil){

        try {
            synchronized (mutex) {
                mutex.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
