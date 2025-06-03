package handWrite.thread_pool_demo;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public interface RejectHandle {

    void reject(Runnable rejectCommand, MyThreadPool threadPool);
}
