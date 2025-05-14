package interview;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Logger2 {

    enum Level {
        DEBUG, INFO, WARN, ERROR
    }

    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private static final Level rootLevel = Level.ERROR;

    private final Thread logWorker;

    private static final Logger2 INSTANCE = new Logger2();

    public static Logger2 getInstance() {
        return INSTANCE;
    }

    public Logger2() {
        logWorker = Thread.ofVirtual().start(() -> {
            while (true) {
                try {
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
    }

    public void print(Level level, String log) throws InterruptedException {
        if (level.ordinal() < rootLevel.ordinal()) return;
        queue.offer(log);
    }

    public static void main(String[] args) throws InterruptedException {
        Logger2 logger2 = getInstance();
        logger2.print(Level.ERROR, "HELLO");
    }
}
