package com.lx.第三季.链表;

// https://leetcode-cn.com/problems/remove-linked-list-elements/submissions/
public class 移除链表元素 {
    /**
     * 移除链表中等于给定值 val 的所有结点:将删除结点的过程看成在构建一条新的链表
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param head 链表的首结点
     * @param val  被删除结点的 value
     * @return 新链表的首结点
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode newHead = new ListNode(); // 创建一个虚拟头结点用于统一部分操作
        ListNode newTail = newHead; // 指向新链表的尾结点

        ListNode current = head; // 用于遍历原链表

        // 将所有不等于 val 的结点串起来形成一条新链表
        while (current != null) {
            if (current.val != val) {
                newTail.next = current;
                newTail = current; // 更新尾结点指针
            }

            current = current.next;
        }

        newTail.next = null; // 最后注意要将尾结点的 next 指针置 null

        return newHead.next;
    }
}
