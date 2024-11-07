package com.lx.第三季.链表;

// https://leetcode-cn.com/problems/add-two-numbers/comments/
public class 两数相加 {
    /**
     * 求两数相加的结果并存储在一条链表中
     * 链表存储数的格式为: 个位->十位->百位->...
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     *
     * @param l1 指向第一个加数的首结点
     * @param l2 指向第二个加数的首结点
     * @return 存放结果的链表
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode newHead = new ListNode(); // 指向结果链表的虚拟头结点
        ListNode newTail = newHead;
        int carry = 0; // 存储加法过程中的进位

        // 需要遍历完较长的链表(加法的两个操作数可能位数不相同)
        while (l1 != null || l2 != null) {
            // l1 或 l2 为 null，则参与相加的位为 0
            int v1 = 0;
            int v2 = 0;
            int sum;

            if (l1 != null) v1 = l1.val;
            if (l2 != null) v2 = l2.val;
            sum = v1 + v2 + carry; // 对应位相加并加上低位的进位
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;

            carry = sum / 10; // 更新进位
            newTail.next = new ListNode(sum % 10); // 得到结果位
            newTail = newTail.next;
        }

        if (carry != 0) // 检测最后一位的相加是否产生了进位
            newTail.next = new ListNode(carry);

        return newHead.next;
    }
}
