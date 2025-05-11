package interview;

import java.lang.ScopedValue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.StructuredTaskScope;

import static interview.Logger.LogLevel.INFO;

public class Logger {

    enum LogLevel {
        INFO, WARN, ERROR
    }

    public static ScopedValue<String> NAME =  ScopedValue.newInstance();



    private LogLevel cur_level = INFO;

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private final Thread workThread;

    private static final Logger INSTANCE = new Logger();

    public static Logger getInstance() {
        return INSTANCE;
    }

    public Logger() {
        workThread = Thread.ofVirtual().start(() -> {
            while (true) {
                try {
                    String log = queue.take();
                    // save a log file
                    System.out.println(log);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
    }

    public void log(LogLevel level, String log) {
        if (level.ordinal() < cur_level.ordinal()) return;
        queue.add(log);
    }

    public void shutdown() throws InterruptedException {
        workThread.interrupt();
        workThread.join();
    }

    public static void main(String[] args) throws InterruptedException {
        Logger logger = Logger.getInstance();
        logger.log(INFO,"EXCEPTION");
        Thread.sleep(1000L);
        logger.shutdown();
    }
}
