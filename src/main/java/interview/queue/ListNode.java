package interview.queue;


public class ListNode {
    private int val;
    private ListNode next;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public ListNode(int x) {
        val = x;
    }

    public ListNode(int x, ListNode node) {
        val = x;
        next = node;
    }
}