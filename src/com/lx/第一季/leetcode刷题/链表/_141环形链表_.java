package com.lx.第一季.leetcode刷题.链表;

public class _141环形链表_ {
    /**
     * 判断链表是否有环
     *
     * @param head 链表首结点
     * @return 是否有环
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        // 快慢指针
        ListNode slow = head, fast = head;

        // 确保快指针的安全
        while (fast != null && fast.next != null) {
            slow = slow.next; // 慢指针走一步
            fast = fast.next.next; // 快指针走两步

            if (slow == fast) return true;
        }

        return false;
    }
}
