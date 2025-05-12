import interview.queue.ListNode;
import interview.queue.MergeKSortedLists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MergeKSortedListsTest {
    @Test
    public void testMergeKLists() {
        MergeKSortedLists merger = new MergeKSortedLists();
        // 构建链表：1->4->5, 1->3->4, 2->6
        ListNode[] lists = new ListNode[]{
            new ListNode(1, new ListNode(4, new ListNode(5))),
            new ListNode(1, new ListNode(3, new ListNode(4))),
            new ListNode(2, new ListNode(6))
        };
        ListNode merged = merger.mergeKLists(lists);
        // 预期合并结果：1->1->2->3->4->4->5->6
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 4, 5, 6}, toArray(merged));
    }

    private int[] toArray(ListNode node) {
        List<Integer> list = new ArrayList<>();
        while (node != null) {
            list.add(node.getVal());
            node = node.getNext();
        }
        return list.stream().mapToInt(i -> i).toArray();
    }
}