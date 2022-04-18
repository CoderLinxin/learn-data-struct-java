package com.lx.第三季.链表;

// https://leetcode-cn.com/problems/partition-list/
public class 分隔链表 {
    /**
     * 让所有小于 x 的结点都出现在所有大于等于 x 的结点的前面
     * 要求不改变结点之间的相对位置
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     *
     * @param head 待分隔的链表首结点
     * @param x    分隔值
     * @return 符合条件的新链表首结点
     */
    public ListNode partition(ListNode head, int x) {
        if (head == null) return null;

        ListNode lHead = new ListNode(); // 指向串起所有小于 x 结点的链表的头结点
        ListNode lTail = lHead;
        ListNode rHead = new ListNode(); // 指向串起所有大于等于 x 结点的链表的头结点
        ListNode rTail = rHead;

        ListNode cur = head;

        // 遍历原链表
        while (cur != null) {
            if (cur.val < x) { // 尾插法串在 lTail 上
                lTail.next = cur;
                lTail = lTail.next;
            } else { // 尾插法串在 rTail 上
                rTail.next = cur;
                rTail = rTail.next;
            }

            cur = cur.next;
        }

        rTail.next = null; // 右链表尾结点 next 指针置 null
        lTail.next = rHead.next; // 将两个链表合并

        return lHead.next;
    }
}
