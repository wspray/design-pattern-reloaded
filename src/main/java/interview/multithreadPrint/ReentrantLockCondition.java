package interview.multithreadPrint;

import java.util.concurrent.locks.*;

public class ReentrantLockCondition {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static boolean isATurn = true;

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                lock.lock();
                try {
                    while (!isATurn) conditionA.await();
                    System.out.println("A: " + i);
                    isATurn = false;
                    conditionB.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                lock.lock();
                try {
                    while (isATurn) conditionB.await();
                    System.out.println("B: " + i);
                    isATurn = true;
                    conditionA.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}