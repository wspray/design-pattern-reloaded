package interview.multithreadPrint;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
    private static volatile int num = 1;

    public static void main(String[] args) {
        CompletableFuture<Void> taskA = CompletableFuture.runAsync(() -> {
            while (num <= 10) {
                if (num % 2 == 1) {
                    System.out.println("A: " + num++);
                }
            }
        });

        CompletableFuture<Void> taskB = CompletableFuture.runAsync(() -> {
            while (num <= 10) {
                if (num % 2 == 0) {
                    System.out.println("B: " + num++);
                }
            }
        });

        CompletableFuture.allOf(taskA, taskB).join();
    }
}