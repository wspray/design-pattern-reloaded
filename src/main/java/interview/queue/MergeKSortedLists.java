package interview.queue;

import java.util.PriorityQueue;

/**
 * 优先级队列（PriorityQueue）相关题目
 * 题目 4：合并 K 个有序链表
 * 场景：给定 K 个升序排列的链表，将它们合并为一个升序链表。
 * 要求：
 *
 * 使用优先级队列（最小堆）优化时间复杂度至 O(n log k)。
 */
public class MergeKSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.getVal() - b.getVal());
        // 将所有链表的头节点加入堆
        for (ListNode node : lists) {
            if (node != null) minHeap.offer(node);
        }
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (!minHeap.isEmpty()) {
            ListNode minNode = minHeap.poll();
            current.setNext(minNode);
            current = current.getNext();
            if (minNode.getNext() != null) {
                minHeap.offer(minNode.getNext());
            }
        }
        return dummy.getNext();
    }
}

