package interview.queue;

/**
 * 题目 3：循环队列设计
 * 场景：设计一个循环队列（MyCircularQueue），支持以下操作：
 *
 * enQueue(value)：向队尾插入元素，成功返回 true。
 *
 * deQueue()：从队首删除元素，成功返回 true。
 *
 * Front()：获取队首元素。
 *
 * Rear()：获取队尾元素。
 *
 * isEmpty()：检查队列是否为空。
 *
 * isFull()：检查队列是否已满。
 */
public class MyCircularQueue {
    private int[] data;
    private int head;
    private int tail;
    private int size;

    public MyCircularQueue(int k) {
        data = new int[k];
        head = -1;
        tail = -1;
        size = k;
    }

    public boolean enQueue(int value) {
        if (isFull()) return false;
        if (isEmpty()) head = 0;
        tail = (tail + 1) % size;
        data[tail] = value;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) return false;
        if (head == tail) {
            head = -1;
            tail = -1;
        } else {
            head = (head + 1) % size;
        }
        return true;
    }

    public int Front() {
        return isEmpty() ? -1 : data[head];
    }

    public int Rear() {
        return isEmpty() ? -1 : data[tail];
    }

    public boolean isEmpty() {
        return head == -1;
    }

    public boolean isFull() {
        return (tail + 1) % size == head;
    }
}