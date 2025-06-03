package interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;

public class JDK21Test {

    private static ScopedValue<String> scopedValue = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {
        ScopedValue.runWhere(scopedValue, "test", () -> {
            System.out.println(scopedValue.get());
        });
        ScopedValue.where(scopedValue, "test2").run(() -> {
            System.out.println(scopedValue.get());
        });

        Thread thread = Thread.ofVirtual().start(() -> System.out.println("Test"));
        thread.join();

        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 5; i++) {
                String id = String.valueOf(i);
                executorService.submit(() -> {
                    ScopedValue.runWhere(scopedValue, id, () -> {
                        System.out.println("Thread:" + scopedValue.get());
                    });
                });
            }
        }

        StructuredTaskScope.ShutdownOnFailure shutdownOnFailure = new StructuredTaskScope.ShutdownOnFailure();
        var forkReturn = shutdownOnFailure.fork(() -> {
            Thread.sleep(1000);
            return "123";
        });
        shutdownOnFailure.join();
        String result = forkReturn.get();
        System.out.println(result);

        List<CompletableFuture> futures = new ArrayList<>();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                int taskId = i;
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    System.out.println(taskId);
                }, executor);
                futures.add(future);
            }
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();



        List<CompletableFuture> futures2 = new ArrayList<>();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                int taskId = i;
                CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                    return taskId;
                }, executor);
                futures2.add(future);
            }
        }
        CompletableFuture.allOf(futures2.toArray(new CompletableFuture[0])).join();

        int result1 = futures2.stream().mapToInt(t -> {
            try {
                return (int) t.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).reduce(0, Integer::sum);
        System.out.println(result1);

    }

    private static void scopeTest() {
        System.out.println(scopedValue.get());
    }
}
