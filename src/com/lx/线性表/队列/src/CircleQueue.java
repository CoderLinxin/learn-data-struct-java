package com.lx.线性表.队列.src;

/**
 * 使用动态数组实现一个循环队列
 *
 * @param <E> 队列元素的数据类型
 */
public class CircleQueue<E> {
    protected int front; // 队头指针
    protected int size; // 循环队列真实元素个数
    protected E[] elements; // 存放循环队列的动态数组
    private final int DEFAULT_CAPACITY = 10; // 动态数组的默认容量

    public CircleQueue() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * 获取队列元素个数
     *
     * @return 元素总个数
     */
    public int size() {
        return this.size;
    }

    /**
     * 队列是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * 清空队列
     */
    public void clear() {
        // 对于数组元素为引用类型的数据时需要置各个栈指针为 null 以释放堆空间
        for (int i = 0; i < this.size; i++)
            this.elements[this.getCircleIndex(i)] = null;
        this.front = 0;
        this.size = 0;
    }

    /**
     * 入队一个元素(队尾添加一个元素)
     */
    public void enQueue(E element) {
        this.ensureCapacity(this.size + 1); // 判断是否需要扩容

        this.elements[this.getCircleIndex(this.size)] = element;
        this.size++;
    }

    /**
     * 出队一个元素(队头删除一个元素)
     *
     * @return 出队的元素
     */
    public E deQueue() {
        if (this.isEmpty()) {
            System.out.println("出队失败，队列为空");
            return null;
        }

        E removeElement = this.elements[this.front]; // 获取出队元素
        this.elements[this.front] = null;

        this.front = this.getCircleIndex(1); // 更新 front 指针
        this.size--;
        return removeElement;
    }

    /**
     * 取队头元素
     *
     * @return 队头元素
     */
    public E front() {
        return this.elements[this.front];
    }

    /**
     * 根据循环队列的逻辑索引获取其对应于动态数组的物理索引（这样我们只需关注队列的逻辑索引而无需关注如何获取其真正的物理索引）
     *
     * @param index 循环队列的逻辑索引
     */
    protected int getCircleIndex(int index) {
//         return (this.front + index) % this.elements.length;

        // 以下用于替代上面的取模运算
        int newIndex = this.front + index; // 新索引的大小
        int len = this.elements.length; // 容器大小

        // 新索引大小超出或等于容器大小时减去容器大小得到正确的索引
        return newIndex >= len ? (newIndex - len) : newIndex;
    }

    /**
     * 动态数组扩容
     *
     * @param capacity 新的容量（当前队列元素数量 + 1(新入队的元素)）
     */
    protected void ensureCapacity(int capacity) {
        int currentCapacity = this.elements.length;

        if (currentCapacity < capacity) {
            int newCapacity = currentCapacity + (currentCapacity >>> 1); // 新数组的容量
            E[] newElements = (E[]) new Object[newCapacity];

            // 迁移旧数组到新数组中
            for (int i = 0; i < this.size; i++)
                newElements[i] = this.elements[this.getCircleIndex(i)];

            this.elements = newElements;
            this.front = 0; // front 指针置 0
        }
    }

    /**
     * 遍历整个动态数组
     */
    public void traver() {
        System.out.print("size: " + this.size);
        System.out.print(", front: " + this.front + " [ ");

        for (int i = 0; i < this.elements.length; i++) {
            if (i != this.elements.length - 1)
                System.out.print(this.elements[i] + ", ");
            else
                System.out.print(this.elements[i] + " ");
        }

        System.out.print("]\n");
    }
}
