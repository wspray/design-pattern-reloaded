package interview.queue;

import java.util.PriorityQueue;

/**
 * 题目 5：数据流的中位数
 * 场景：设计一个支持动态添加数字并快速查找中位数的数据结构。
 * 要求：
 *
 * 使用两个优先级队列（最大堆 + 最小堆）实现。
 *
 * addNum(int num) 时间复杂度 O(log n)。
 *
 * findMedian() 时间复杂度 O(1)。
 */
public class MedianFinder {
    private PriorityQueue<Integer> maxHeap; // 存储较小的一半（降序）
    private PriorityQueue<Integer> minHeap; // 存储较大的一半（升序）

    public MedianFinder() {
        maxHeap = new PriorityQueue<>((a, b) -> b - a);
        minHeap = new PriorityQueue<>();
    }

    public void addNum(int num) {
        maxHeap.offer(num);
        minHeap.offer(maxHeap.poll());
        // 平衡两个堆的大小
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            return maxHeap.peek();
        }
    }
}