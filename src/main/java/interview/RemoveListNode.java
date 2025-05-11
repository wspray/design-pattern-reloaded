package interview;

public class RemoveListNode {

    static class ListNode<T> {
        T val;
        ListNode<T> next;

        ListNode(T x) {
            val = x;
        }
    }

    static class ListArray<T> {
        ListNode<T> head;
        ListNode<T> tail;
        int size;

        void add(ListNode<T> listNode) {
            if (listNode == null) return;
            if (head != null) {
                tail.next = listNode;
                tail = listNode;
            } else {
                head = listNode;
                tail = head;
            }
            size++;
        }


        void remove(ListNode<T> node) {
            if (size == 0 || node == null) return;
            ListNode<T> cur = head;
            ListNode<T> pre = null;
            while (cur != null) {
                if (cur != node) {
                    pre = cur;
                    cur = cur.next;
                    continue;
                }
                if (cur == head) {
                    head = null;
                    return;
                }
                pre.next = cur.next;
                cur.next = null;
                cur = null;
            }
        }

        void print() {
            if (size == 0) return;
            ListNode<T> temp = head;
            while (temp != null) {
                System.out.println(temp.val);
                temp = temp.next;
            }
        }

        void reverse() {
            if (size == 0 || size == 1) return;
            ListNode<T> pre = null;
            ListNode<T> cur = head;
            ListNode<T> next = null;
            while (cur != null) {
                next = cur.next;
                cur.next = pre;

                pre = cur;
                if (next == null) {
                    head = cur;
                }
                cur = next;
            }
        }
    }

    public static void main(String[] args) {
        ListNode<Integer> node1 = new ListNode<>(1);
        ListNode<Integer> node2 = new ListNode<>(2);
        ListNode<Integer> node3 = new ListNode<>(3);
        ListArray<Integer> listArray = new ListArray<>();
        listArray.add(node1);
        listArray.add(node2);
        listArray.add(node3);
        listArray.print();

//        listArray.remove(node1);
//        listArray.print();

        listArray.reverse();
        listArray.print();
    }
}
