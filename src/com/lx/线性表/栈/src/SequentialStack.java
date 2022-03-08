package com.lx.线性表.栈.src;

import com.lx.线性表.动态数组.src.MyArrayList;
import com.lx.线性表.链表.src.List;

/**
 * 使用动态数组实现一个顺序栈
 */
public class SequentialStack<E> {
    // 由于栈只提供有限的接口，因此不采用继承 MyArrayList 的方式（避免栈结构暴露 MyArrayList 的公共接口）
    // 而是采用组合的方式来复用代码
    private final List<E> list = new MyArrayList<>();

    /**
     * 返回栈中元素的数量
     *
     * @return 栈中元素的数量
     */
    public int size() {
        return this.list.size();
    }

    /**
     * 栈是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    /**
     * 入栈一个元素
     */
    public void push(E element) {
        this.list.add(element);
    }

    /**
     * 出栈一个元素
     *
     * @return 出栈的元素
     */
    public E pop() {
        return this.list.remove(this.size() - 1);
    }

    /**
     * 获取栈顶元素
     *
     * @return 栈顶元素
     */
    public E top() {
        return this.list.get(this.size() - 1);
    }

    /**
     * 清空栈
     */
    public void clear() {
        this.list.clear();
    }
}
