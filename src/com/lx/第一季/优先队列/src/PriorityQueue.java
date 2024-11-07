package com.lx.第一季.优先队列.src;

import com.lx.第一季.堆.src.BinaryHeap;

import java.util.Comparator;

/**
 * 使用大顶堆实现优先级队列
 *
 * @param <E> 元素类型
 */
public class PriorityQueue<E> {
    private BinaryHeap<E> heap;
    Comparator<E> comparator;

    public PriorityQueue(Comparator<E> comparator) {
        this.comparator = comparator;
        this.heap = new BinaryHeap<>(comparator);
    }

    public PriorityQueue() {
        this(null);
    }

    /**
     * 获取优先级队列元素个数
     *
     * @return 元素个数
     */
    public int size() {
        return this.heap.size();
    }

    /**
     * 判断优先级队列是否为空
     *
     * @return 优先级队列是否为空
     */
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    /**
     * 清空优先级队列
     */
    public void clear() {
        this.heap.clear();
    }

    /**
     * 入队一个元素
     *
     * @param element 入队元素
     */
    public void enQueue(E element) {
        this.heap.add(element);
    }

    /**
     * 优先级最高的元素出队
     *
     * @return 优先级最高的元素
     */
    public E deQueue() {
        return this.heap.remove(); // 直接删除堆顶元素即可
    }

    /**
     * 获取优先级队列(优先级最高队头元素
     *
     * @return 队头元素
     */
    public E front() {
        return this.heap.get(); // 获取堆顶元素
    }
}
