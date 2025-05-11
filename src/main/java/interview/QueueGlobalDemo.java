package interview;

import java.util.concurrent.*;

public class QueueGlobalDemo {
    public static void main(String[] args) {
        // 1. 创建全局阻塞队列（线程安全）
        BlockingQueue<Runnable> globalQueue = new LinkedBlockingQueue<>();

        // 2. 提交1000个任务到队列
        for (int i = 0; i < 1000; i++) {
            int taskId = i;
            globalQueue.add(() -> processTask(taskId));
        }

        // 3. 创建10个工作者线程
        ExecutorService workers = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            workers.execute(() -> {
                while (!globalQueue.isEmpty()) {
                    try {
                        Runnable task = globalQueue.take();
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