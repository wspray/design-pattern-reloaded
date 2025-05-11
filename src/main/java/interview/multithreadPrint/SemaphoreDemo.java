package interview.multithreadPrint;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    private static Semaphore semA = new Semaphore(1);
    private static Semaphore semB = new Semaphore(0);

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                try {
                    semA.acquire();
                    System.out.println("A: " + i);
                    semB.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                try {
                    semB.acquire();
                    System.out.println("B: " + i);
                    semA.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}