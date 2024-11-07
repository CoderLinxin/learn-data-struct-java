package com.lx.第一季.线性表.队列.src;

/**
 * 实现一个循环双端队列
 */
public class CircleDEQueue<E> {
    private final CircleQueue<E> queue = new CircleQueue<>();

    /**
     * 获取队列元素个数
     *
     * @return 元素个数
     */
    public int size() {
        return queue.size;
    }

    /**
     * 队列是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    /**
     * 清空队列
     */
    public void clear() {
        this.queue.clear();
    }

    /**
     * 从队尾入队
     *
     * @param element 入队元素
     */
    public void enQueueRear(E element) {
        queue.enQueue(element);
    }

    /**
     * 从队头入队
     *
     * @param element 入队元素
     */
    public void enQueueFront(E element) {
        this.queue.ensureCapacity(this.queue.size + 1);

        this.queue.front = this.getCircleIndex(-1); // 获取新的队头指针
        this.queue.elements[this.queue.front] = element;
        this.queue.size++;
    }

    /**
     * 从队尾出队
     *
     * @return 出队元素
     */
    public E deQueueRear() {
        int rearIndex = this.getCircleIndex(this.queue.size - 1); // 取队尾指针
        E rearElement = this.queue.elements[rearIndex]; // 获取队尾元素
        this.queue.elements[rearIndex] = null; // 队尾元素置 null

        this.queue.size--;
        return rearElement;
    }

    /**
     * 从队头出队
     *
     * @return 出队元素
     */
    public E deQueueFront() {
        return this.queue.deQueue();
    }

    /**
     * 取队头元素
     *
     * @return 队头元素
     */
    public E front() {
        return this.queue.front();
    }

    /**
     * 取队尾元素
     *
     * @return 队尾元素
     */
    public E rear() {
        int rearIndex = this.getCircleIndex(this.queue.size() - 1);
        return this.queue.elements[rearIndex];
    }

    /**
     * 根据循环队列的逻辑索引获取其对应真实的物理索引(循环队列所有的逻辑索引都是以 front 为基准的)
     *
     * @return 循环队列逻辑索引对应的物理索引
     */
    public int getCircleIndex(int index) {
        // index 小于 0 则说明到达动态数组的最左边，因此需要加上动态数组的长度 length 进行偏移，
        index = (this.queue.front + index < 0) ? index + this.queue.elements.length : index;

        // 获取逻辑索引对应的真实索引
        return this.queue.getCircleIndex(index);
    }

    public void traver() {
        this.queue.traver();
    }
}
