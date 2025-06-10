package handWrite.lock_demo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyLock {

    AtomicInteger state = new AtomicInteger(0);

    Thread owner = null;

    AtomicReference<Node> head = new AtomicReference<>(new Node());

    AtomicReference<Node> tail = new AtomicReference<>(head.get());

    void lock() {
        if (state.get() == 0) {
            if (state.compareAndSet(0, 1)) {
                System.out.println(Thread.currentThread().getName() + "直接拿到锁");
                owner = Thread.currentThread();
                return;
            }
        } else {
            if (owner == Thread.currentThread()) {
                System.out.println(Thread.currentThread().getName() + " 拿到了重入锁,当前重入次数为" + state.incrementAndGet());
                return;
            }
        }

        // 没有拿到锁，安全的把自己放到尾节点
        Node current = new Node();
        current.thread = Thread.currentThread();
        while (true) {
            Node currentTail = tail.get(); // 每次拿到最新的尾节点
            if (tail.compareAndSet(currentTail, current)) {
                System.out.println(Thread.currentThread().getName() + "加入到了链表尾");
                current.pre = currentTail;
                currentTail.next = current;
                break;
            }
        }
        // 阻塞
        while (true) {
            // 避免unpark时的虚假唤醒。
            // 应该唤醒头节点的下一个节点，并设置当前节点为head节点
            if (current.pre == head.get() && state.compareAndSet(0, 1)) {
                owner = Thread.currentThread();
                head.set(current);
                current.pre.next = null;
                current.pre = null;
                System.out.println(Thread.currentThread().getName() + "被唤醒之后，拿到锁");
                return;
            }
            LockSupport.park();
        }
    }

    void unlock() { // 只有持有锁的线程才会unlock，所以同一时间只有一个线程在操作此方法
        if (Thread.currentThread() != this.owner) {
            throw new IllegalStateException("当前线程并没有锁，不能解锁"+Thread.currentThread().getName() + "owner" + owner.getName());
        }
        int i = state.get();
        if (i > 1) {
            state.set(i - 1);
            System.out.println(Thread.currentThread().getName() + "解锁了重入锁，重入锁剩余次数" + (i - 1));
            return;
        }
        if (i <= 0) {
            throw new IllegalStateException("重入锁解锁错误！");
        }
        Node headNode = head.get();
        Node next = headNode.next;
        owner = null;
        state.set(0);  // 状态放开，注意立马会出现竞争。但头节点的下个节点可能没来得及设置，所以上面的park方法前要自己尝试获取一次锁
        if (next != null) {
            System.out.println(Thread.currentThread().getName() + "唤醒了" + next.thread.getName());
            LockSupport.unpark(next.thread);
        }
    }
}
