package com.lx.第一季.堆.src;

import java.util.Comparator;

/**
 * 实现了堆的部分公共接口
 *
 * @param <E> 元素类型
 */
public abstract class AbstractHeap<E> implements Heap<E> {
    protected int size; // 二叉堆结点数量
    protected Comparator<E> comparator;

    public AbstractHeap() {
        this(null);
    }

    public AbstractHeap(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * 获取堆总结点数量
     *
     * @return 总结点数量
     */
    public int size() {
        return this.size;
    }

    /**
     * 堆是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * 用于比较两个元素的接口
     *
     * @param e1 元素1
     * @param e2 元素2
     * @return -1 0 1
     */
    protected int compare(E e1, E e2) {
        return this.comparator != null
                ? this.comparator.compare(e1, e2)
                : ((Comparable) e1).compareTo(e2);
    }
}
