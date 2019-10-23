package prepare.lecture;

public class Util {
    public static void printThread(final Thread thread) {
        System.out
                .println("name = " + thread + " group = " + thread.getThreadGroup() + " state = " + thread.getState() + " isInterrupted = " + thread.isInterrupted());
    }

}
