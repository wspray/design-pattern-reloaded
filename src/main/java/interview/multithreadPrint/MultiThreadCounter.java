package interview.multithreadPrint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadCounter {

    public static final AtomicInteger count = new AtomicInteger();



    public static void main(String[] args) {

        List<CompletableFuture> futures = new ArrayList<>();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                    for (int j = 0; j < 10; j++) {
                        count.incrementAndGet();
                    }
                    return count.get();
                }, executor);
                futures.add(future);
            }
        }
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        System.out.println(count.get());
    }
}
