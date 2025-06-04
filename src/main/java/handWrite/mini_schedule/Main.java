package handWrite.mini_schedule;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class Main {
    public static void main(String[] args) throws InterruptedException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss SSS");
        ScheduleService scheduleService = new ScheduleService();
        scheduleService.schedule(()->{
            System.out.println(LocalDateTime.now().format(dateTimeFormatter) +" 这是 100毫秒一次的");
        },100);
        
        Thread.sleep(1000);
        System.out.println("添加一个每200毫秒打印一个2的定时任务");
        scheduleService.schedule(()->{
            System.out.println(LocalDateTime.now().format(dateTimeFormatter) +" 这是 200毫秒一次的");
        },200);
    }
}
