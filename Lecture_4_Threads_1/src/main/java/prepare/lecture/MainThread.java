package prepare.lecture;

public class MainThread {

    private static class MyThread extends Thread {

        @Override public void run() {
            System.out.println("Thread Id:" + Thread.currentThread().getId());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread th = Thread.currentThread();
//        System.out.println(th.getName());
//        System.out.println(th.getThreadGroup());

        Thread thread = new Thread("Sergey Thread");
        print(thread);
        thread.start();
        print(thread);
        new Thread(() -> {
            try {
                synchronized (thread) {
                    thread.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        print(thread);
//        Thread.currentThread().sleep(2000);
        print(thread);

        MyThread myThread = new MyThread();

        System.out.println("Exiting start");
        myThread.sleep(10000);
        myThread.wait(10000);
        System.out.println("Exiting");




        System.out.println("Andrey");
    }



    private static void print(final Thread thread) {
        System.out.println("State: " + thread.getState());
    }
}
