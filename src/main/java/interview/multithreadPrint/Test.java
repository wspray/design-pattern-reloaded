package interview.multithreadPrint;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    static Lock lock = new ReentrantLock();
    static Condition conditionA = lock.newCondition();
    static Condition conditionB = lock.newCondition();
    static Thread threadA;
    static Thread threadB;
    static AtomicBoolean isAReturn = new AtomicBoolean(true);

    public static void main(String[] args) {
        threadA = new Thread(() -> {
            for (int i = 1; i <= 10; i = i + 2) {
                lock.lock();
                try {
                    if (!isAReturn.get()) conditionA.await();
                    System.out.println(i + ": " + "A");
                    isAReturn.set(false);
                    conditionB.signal();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });
        threadB = new Thread(() -> {
            for (int i = 2; i <= 10; i = i + 2) {
                lock.lock();
                try {
                    if (isAReturn.get()) conditionB.await();
                    System.out.println(i + ": " + "B");
                    isAReturn.set(true);
                    conditionA.signal();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });
        threadA.start();
        threadB.start();
    }
}
