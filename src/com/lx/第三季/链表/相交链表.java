package com.lx.第三季.链表;

public class 相交链表 {
    /**
     * 求出链表 A、B 相交的起始结点
     *
     * @param headA 链表 A 的首结点
     * @param headB 链表 B 的首结点
     * @return 相交的起始结点
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;

        ListNode curA = headA;
        ListNode curB = headB;

        // curA、curB 可能同时为 null，此时也会终止循环
        while (curA != curB) {
            // curA 遍历完 A 链表开始遍历 B 链表，此时对齐了 B 的尾部
            curA = curA == null ? headB : curA.next;
            // curB 遍历完 B 链表开始遍历 A 链表，此时对齐了 A 的尾部
            curB = curB == null ? headA : curB.next;
        }

        /* curA == curB */
        return curA;
    }
}
