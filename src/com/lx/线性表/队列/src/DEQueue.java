package com.lx.线性表.队列.src;

import com.lx.线性表.链表.src.BidirectionalLinkedList;
import com.lx.线性表.链表.src.List;

/**
 * 使用双向链表实现一个双端队列
 *
 * @param <E> 结点存储的数据类型
 */
public class DEQueue<E> {
    List<E> list = new BidirectionalLinkedList<>();

    /**
     * 获取队列元素个数
     *
     * @return 元素个数
     */
    public int size() {
        return this.list.size();
    }

    /**
     * 队列是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    /**
     * 清空队列
     */
    public void clear() {
        this.list.clear();
    }

    /**
     * 从队尾入队
     *
     * @param element 入队元素
     */
    public void enQueueRear(E element) {
        this.list.add(this.list.size(), element);
    }

    /**
     * 从队头入队
     *
     * @param element 入队元素
     */
    public void enQueueFront(E element) {
        this.list.add(0, element);
    }

    /**
     * 从队尾出队
     *
     * @return 出队元素
     */
    public E deQueueRear() {
        return this.list.remove(this.size() - 1);
    }

    /**
     * 从队头出队
     *
     * @return 出队元素
     */
    public E deQueueFront() {
        return this.list.remove(0);
    }

    /**
     * 取队头元素
     *
     * @return 队头元素
     */
    public E front() {
        return this.list.get(0);
    }

    /**
     * 取队尾元素
     *
     * @return 队尾元素
     */
    public E rear() {
        return this.list.get(this.list.size() - 1);
    }
}
