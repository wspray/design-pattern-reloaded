package interview.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 题目 1：用队列实现栈
 * 场景：仅使用队列（Queue）的操作实现栈（Stack）的 push、pop、top 和 empty 方法。
 * 要求：
 *
 * 只能使用标准队列操作（如 poll, offer, peek, size）。
 *
 * 实现时间复杂度均摊 O(1) 的 pop 和 top。
 */
public class MyStack {
    private Queue<Integer> queue = new LinkedList<>();

    public void push(int x) {
        queue.offer(x);
        // 将新元素之前的元素全部移到队尾
        for (int i = 0; i < queue.size() - 1; i++) {
            queue.offer(queue.poll());
        }
    }

    public int pop() {
        return queue.poll();
    }

    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}