package com.lx.leetcode刷题.链表;

/**
 * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
 */
public class _237_删除链表中的结点 {
    /**
     * @param node 需要被删除的结点
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val; // 下一结点值拷贝到当前结点
        node.next = node.next.next; // 当前结点指向下下个结点
    }
}
