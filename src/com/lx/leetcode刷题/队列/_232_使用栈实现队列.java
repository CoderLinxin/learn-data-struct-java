package com.lx.leetcode刷题.队列;

import java.util.Stack;

public class _232_使用栈实现队列 {
    Stack<Integer> inStack;
    Stack<Integer> outStack;

    public _232_使用栈实现队列() {
        this.inStack = new Stack<>();
        this.outStack = new Stack<>();
    }

    /**
     * 入队
     *
     * @param x 入队的元素
     */
    public void push(int x) {
        this.inStack.push(x);
    }

    /**
     * 出队
     *
     * @return 出队元素
     */
    public int pop() {
        // outStack 为空则将 inStack 中的元素全部出栈然后入栈到 outStack
        if (this.outStack.isEmpty())
            while (!this.inStack.isEmpty())
                this.outStack.push(this.inStack.pop());

        // outStack 不为空则出栈一个元素
        return this.outStack.pop();
    }

    /**
     * 取队头元素
     *
     * @return 队头元素
     */
    public int peek() {
        // outStack 为空则将 inStack 中的元素全部出栈然后入栈到 outStack
        if (this.outStack.isEmpty())
            while (!this.inStack.isEmpty())
                this.outStack.push(this.inStack.pop());

        // 取 outStack 的栈顶元素
        return this.outStack.peek();
    }

    /**
     * 队列是否为空
     *
     * @return 是否为空
     */
    public boolean empty() {
        // 两个栈都为空则说明队列为空
        return this.outStack.isEmpty() && this.inStack.isEmpty();
    }
}
