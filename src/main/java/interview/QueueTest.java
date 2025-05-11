package interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueTest {
    private static final List<BlockingQueue<Runnable>> list = new ArrayList<>(10);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            list.add(new LinkedBlockingQueue<>());
        }
        for (int i = 0; i < 100; i++) {
            int index = i % 10;
            int taskId = i;
            list.get(index).add(() -> System.out.println(index + ":task:" + taskId));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            int taskId = i;
            executorService.execute(() -> {
                BlockingQueue<Runnable> queue = list.get(taskId);
                while (!queue.isEmpty()) {
                    try {
                        queue.take().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
        executorService.shutdown();
    }
}
