package prepare.util;

public class Util {

    public static void sleep(final int mil){
        try {
            Thread.sleep(mil);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
