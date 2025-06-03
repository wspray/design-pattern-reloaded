package interview.multithreadPrint;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SyncWaitNotify {
    private static final Logger logger = Logger.getLogger("SyncWaitNotify");

    static {
        // 设置日志级别
        logger.setLevel(Level.INFO);
        // 配置控制台处理器
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);
        // 可以设置输出格式（默认即可）
        logger.addHandler(handler);
        // 禁止使用默认的父处理器（防止重复输出）
        logger.setUseParentHandlers(false);
    }


    private static final Object lock = new Object();
    private static volatile boolean isATurn = true;

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                synchronized (lock) {
                    while (!isATurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("A: " + i);
                    isATurn = false;
                    lock.notify();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                synchronized (lock) {
                    while (isATurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("B: " + i);
                    isATurn = true;
                    lock.notify();
                }
            }
        }).start();
    }
}