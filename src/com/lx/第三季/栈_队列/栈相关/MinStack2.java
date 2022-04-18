package com.lx.第三季.栈_队列.栈相关;

// https://leetcode-cn.com/problems/min-stack/
public class MinStack2 {
    private Node head;

    public MinStack2() {
        // 初始指向一个虚拟头结点
        this.head = new Node(0, Integer.MAX_VALUE, null);
    }

    public void push(int val) {
        // head -> newNode -> oldHead
        this.head = new Node(val, Math.min(val, head.min), head);
    }

    public void pop() {
        this.head = this.head.next;
    }

    public int top() {
        return this.head.val;
    }

    public int getMin() {
        return this.head.min;
    }

    private static class Node {
        int val; // 结点的数据
        int min; // 当前链表的最小值
        Node next;

        public Node(int val, int min, Node next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }
}
