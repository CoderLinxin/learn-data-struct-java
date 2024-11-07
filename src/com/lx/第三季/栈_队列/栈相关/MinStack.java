package com.lx.第三季.栈_队列.栈相关;

import java.util.Stack;

// https://leetcode-cn.com/problems/min-stack/
public class MinStack {
    Stack<Integer> stack; // 用于存放普通数据
    Stack<Integer> minStack; // 用于存放 stack 中当前已存放 n 个数的最小值

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        this.stack.push(val);

        if (minStack.isEmpty()) {
            // stack 初始时加入一个元素(此时 minStack 为空)，该元素就是当 stack 中所有元素的最小值
            minStack.push(val);
        } else {
            // 取 minStack 的栈顶元素与 val 较小者入栈
            minStack.push(Math.min(val, minStack.peek()));
        }
    }

    public void pop() {
        this.stack.pop();
        this.minStack.pop();
    }

    public int top() {
        return this.stack.peek();
    }

    // 获取当前 stack 中所拥有元素的最小值
    public int getMin() {
        return this.minStack.peek();
    }
}
