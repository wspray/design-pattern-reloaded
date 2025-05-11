package interview.multithreadPrint;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    private static Thread threadA, threadB;

    public static void main(String[] args) {
        threadA = new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                System.out.println("A: " + i);
                LockSupport.unpark(threadB);
                LockSupport.park();
            }
        });

        threadB = new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                LockSupport.park();
                System.out.println("B: " + i);
                LockSupport.unpark(threadA);
            }
        });

        threadA.start();
        threadB.start();
    }
}