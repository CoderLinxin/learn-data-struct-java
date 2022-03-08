package com.lx.线性表.队列.src;

import com.lx.线性表.链表.src.BidirectionalLinkedList;
import com.lx.线性表.链表.src.List;

/**
 * 使用双向链表实现一个队列
 *
 * @param <E> 结点数据类型
 */
public class LinkedQueue<E> {
    // 同样为了避免双向链表的其他公共接口被队列暴露出去，仍然采用组合双向链表实例的方式实现队列
    private final List<E> list = new BidirectionalLinkedList<>();

    /**
     * 获取队列元素个数
     *
     * @return 元素总个数
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
     * 入队一个元素(队尾添加一个元素)
     */
    public void enQueue(E element) {
        this.list.add(element);
    }

    /**
     * 出队一个元素(队头删除一个元素)
     *
     * @return 出队的元素
     */
    public E deQueue() {
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

    public void traver() {
        this.list.traver();
    }
}
