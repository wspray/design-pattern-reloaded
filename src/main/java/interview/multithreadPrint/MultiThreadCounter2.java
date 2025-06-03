package interview.multithreadPrint;

import java.util.concurrent.atomic.AtomicInteger;

public interface MultiThreadCounter2 {

    AtomicInteger count = new AtomicInteger();

    int increment();

    static int syncIncrement() {
        return count.incrementAndGet();
    }


    static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            syncIncrement();
        }
        System.out.println(count.get());
    }
}
