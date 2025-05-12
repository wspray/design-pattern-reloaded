package interview;

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

    }

    private static void scopeTest() {
        System.out.println(scopedValue.get());
    }
}
