package interview;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class QueueShardedDemo {
    public static void main(String[] args) {
        // 1. 创建10个独立队列
        List<BlockingQueue<Runnable>> queues = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            queues.add(new LinkedBlockingQueue<>());
        }

        // 2. 分配1000个任务到10个队列（轮询分片）
        for (int i = 0; i < 1000; i++) {
            int queueIndex = i % 10;
            int taskId = i;
            queues.get(queueIndex).add(() -> processTask(taskId));
        }

        // 3. 为每个队列启动一个专属工作者线程
        ExecutorService workers = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            final int queueIndex = i;
            workers.execute(() -> {
                BlockingQueue<Runnable> queue = queues.get(queueIndex);
                while (!queue.isEmpty()) {
                    try {
                        Runnable task = queue.take();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        // 4. 关闭线程池
        workers.shutdown();
    }

    private static void processTask(int taskId) {
        System.out.println(Thread.currentThread().getName() + " processing task " + taskId);
    }
}