package com.lx.第三季.链表;

// https://leetcode-cn.com/problems/palindrome-linked-list/
public class 回文链表 {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true; // 链表为空或只有一个元素也视为回文链表
        if (head.next.next == null) return head.val == head.next.val; // 链表只有两个元素的情况

        // 1.找到中间结点
        ListNode midNode = findMiddleNode(head);

        // 2.根据中间结点反转右半部分链表
        ListNode rHead = reverseList(midNode.next);

        ListNode curR = rHead;
        ListNode curL = head;
        boolean result = true;

        // 3.同时遍历左半部分链表、右半部分反转链表
        while (curR != null) {
            if (curR.val != curL.val) { // 元素不同则不是回文链表
                result = false;
                break;
            }

            // 继续遍历
            curR = curR.next;
            curL = curL.next;
        }

        // 为了不破坏链表结点，将右边部分链表反转回来
        reverseList(rHead);

        // 遍历完右边的反转链表则说明源链表是一个回文链表
        return result;
    }

    /**
     * 使用快慢指针寻找链表的中间结点, 中间结点约定为第 head.size() / 2 个结点
     *
     * @param head 源链表的首结点
     * @return 源两步的中间结点
     */
    private ListNode findMiddleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // fast.next == null || fast.next.next == null
        // 此时相当于 fast 指针已走完全程，slow 指针刚好走到一半，指向链表的中间结点
        return slow;
    }

    /**
     * 反转链表
     *
     * @param head 源链表的首结点
     * @return 反转后链表的首结点
     */
    private ListNode reverseList(ListNode head) {
        ListNode before = null; // 指向源链表的前一个结点
        ListNode after = head; // 指向源链表的下一个结点

        while (after != null) {
            ListNode tmp = after.next; // 临时保存 after 下一个结点

            // 结点反转
            after.next = before;

            // 指针后移
            before = after;
            after = tmp;
        }

        return before;
    }

    public static void main(String[] args) {
    }
}














