package com.lx.leetcode刷题.链表;

public class _206迭代反转链表_ {
    /**
     * @param head 原来旧的头结点
     * @return 反转后新的头结点
     */
    public ListNode reverseListByIteration(ListNode head) {
        // 待反转的两个结点
        ListNode back = null;
        ListNode front = head;

        while (front != null) {
            // 每轮反转前保存后续待处理链表的头结点
            ListNode temp = front.next;

            // 进行反转
            front.next = back;

            // 反转指针后移
            back = front;
            front = temp;
        }

        // front 为 null 表示 back 为原来链表的尾结点，此时即为新链表的头结点
        return back;
    }
}
