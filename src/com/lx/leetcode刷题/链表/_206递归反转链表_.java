package com.lx.leetcode刷题.链表;

public class _206递归反转链表_ {
    /**
     * @param head 原来旧的头结点
     * @return 反转后新的头结点
     */
    public ListNode reverseListByRecursion(ListNode head) {
        // head 为 null：null 不需要反转
        // header.next 为 null：只有一个结点的链表不需要反转
        if (head == null || head.next == null) return head;

        // 返回的 newHead 作为头指针指向已经成功反转的链表（只剩下 head 结点未处理）
        ListNode newHead = reverseListByRecursion(head.next);

        // 处理 head 结点的反转
        ListNode tail = head.next; // 反转链表的尾指针
        tail.next = head; // 指向 head 结点
        head.next = null; // 更新尾指针

        return newHead;
    }
}
