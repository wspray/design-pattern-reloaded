package interview.multithreadPrint;

public class SyncWaitNotify {
    private static final Object lock = new Object();
    private static volatile boolean isATurn = true;

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                synchronized (lock) {
                    while (!isATurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("A: " + i);
                    isATurn = false;
                    lock.notify();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                synchronized (lock) {
                    while (isATurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("B: " + i);
                    isATurn = true;
                    lock.notify();
                }
            }
        }).start();
    }
}