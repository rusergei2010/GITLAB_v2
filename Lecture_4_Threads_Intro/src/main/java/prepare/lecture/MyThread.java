package prepare.lecture;

public class MyThread {

    public class MyThreadExec implements Runnable {

        public int counter = 0;

        public void run() {
            try {
                Thread.sleep(200);
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void print() {
            System.out.println("Counter = " + counter);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new MyThread();
    }

    public MyThread() throws InterruptedException {

        // 1 case define Thread
        Thread thread = new Thread();
        thread.start();

        // 2 case define Thread
        MyThreadExec myThreadExec = new MyThreadExec();
        Thread thread2 = new Thread(myThreadExec);
        thread2.start();

        printThread(Thread.currentThread());
        printThread(thread);

//        thread2.join(); // main thread will wait till thread2 is executed

        thread2.join(100);
        printThread(thread2);

        myThreadExec.print();
    }

    private void printThread(final Thread thread) {
        System.out
                .println("name = " + thread + " group = " + thread.getThreadGroup() + " state = " + thread.getState() + " isInterrupted = " + thread.isInterrupted());
    }
}
