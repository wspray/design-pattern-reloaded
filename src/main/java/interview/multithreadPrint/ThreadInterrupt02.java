package interview.multithreadPrint;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadInterrupt02 {
    //使用线程池进行测试
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        
        executorService.execute(()->{
            System.out.println("线程1打算获取锁");
            synchronized (lock){
                try {
                    System.out.println("线程1打算睡眠");
                    //sleep不会释放锁
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程1已经执行完成");
            }
        });
        executorService.shutdown();
 
        //主线程睡眠1秒，保证线程1已经睡眠了
        TimeUnit.SECONDS.sleep(1);
 
        Thread thread = new Thread(() -> {
            System.out.println("线程2打算获取锁");
            //线程2会在此被阻塞，因为线程1已经拿到了锁，并抱着锁睡觉了
            //在此中断线程是不会有任何响应的
            synchronized (lock) {
                try {
                    //判断当前线程的中断为
                    System.out.println("线程2的中断标志位 " + Thread.currentThread().isInterrupted());
                    //虽然在synchronized (lock) {中不会响应，但是线程的中断标志位还是true，所以执行到此，会响应迟到的中断
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("InterruptedException 线程2的中断标志位 " + Thread.currentThread().isInterrupted());
                }
            }
            System.out.println("线程2已经执行完成");
        });
 
        thread.start();
        //此时中断线程2，测试线程2在等待获取锁的时候会不会相应中断
        thread.interrupt();
    }
}
 
/*
执行结果：
线程1打算获取锁
线程1打算睡眠
线程2打算获取锁
线程1已经执行完成
线程2的中断标志位 true
java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at java.lang.Thread.sleep(Thread.java:340)
	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
	at it.cast.basic.thread.interrupt.Test03.lambda$main$1(Test03.java:43)
	at java.lang.Thread.run(Thread.java:748)
InterruptedException 线程2的中断标志位 false
线程2已经执行完成
*/