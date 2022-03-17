package com.lx.堆.src;

import com.lx.树.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * 实现一个大根堆(数组实现)
 *
 * @param <E> 元素类型
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {
    private E[] elements; // 承载二叉堆结点的数组
    private static final int DEFAULT_CAPACITY = 10; // 数组容量

    /**
     * @param comparator 比较接口
     * @param elements   待堆化的数组
     */
    public BinaryHeap(Comparator<E> comparator, E[] elements) {
        super(comparator);

        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            this.size = elements.length;
            int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            for (int i = 0; i < this.size; i++) // 对数据源进行深拷贝
                this.elements[i] = elements[i];

            // 堆化
            this.downTopHeapify();
        }
    }

    /**
     * 自上而下的上滤建堆
     */
    private void topDownHeapify() {
        // 从第二个结点开始上滤
        for (int i = 1; i < this.size; i++)
            this.shiftUp(i);
    }

    /**
     * 自下而上的下滤建堆
     */
    private void downTopHeapify() {
        // 从最后非叶子结点开始下滤
        for (int i = (this.size >> 1) - 1; i >= 0; i--)
            this.shiftDown(i);
    }

    public BinaryHeap(E[] elements) {
        this(null, elements);
    }

    public BinaryHeap() {
        this(null, null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        this(comparator, null);
    }

    /**
     * 清空二叉堆
     */
    public void clear() {
        if (this.size == 0) return;

        // 完全二叉堆按顺序(无缝)存储，直接遍历到 size 即可
        for (int i = 0; i < this.size; i++)
            this.elements[i] = null;

        this.size = 0;
    }

    /**
     * 添加一个元素到堆中
     *
     * @param element 待添加的元素
     */
    public void add(E element) {
        this.elementNotNullCheck(element); // 检查元素是否为 null
        this.ensureCapacity(this.size + 1); // 确保容量

        this.elements[this.size++] = element;
        this.shiftUp(this.size - 1); // 堆插入的结点进行上滤
    }

    /**
     * 获取根顶元素
     *
     * @return 根顶元素
     */
    public E get() {
        this.emptyCheck();

        return this.elements[0];
    }

    /**
     * 删除堆顶元素
     *
     * @return 堆顶元素
     */
    public E remove() {
        this.emptyCheck();

        E root = this.elements[0];
        int lastIndex = --this.size;
        this.elements[0] = this.elements[lastIndex]; // 数组最后一个元素覆盖根元素
        this.elements[lastIndex] = null;

        this.shiftDown(0); // 对新的根元素进行下滤
        return root;
    }

    /**
     * 删除堆顶元素并插入一个新元素
     *
     * @param element 插入的新元素
     * @return 删除的堆顶元素
     */
    public E replace(E element) {
        E root = null;

        if (this.size == 0) { // 初始时为空直接添加
            this.elements[0] = element;
            this.size++;
        } else {// 数组不为空则覆盖第一个元素(注意这里的 size 不用动)
            root = this.elements[0];
            this.elements[0] = element; // 新添加元素覆盖第一个元素
            this.shiftDown(0); // 执行下滤操作
        }

        return root;
    }

    /**
     * 确保数组要有 capacity 的容量
     *
     * @param capacity 当前数组实际容量(size) + 1
     */
    private void ensureCapacity(int capacity) {
        int currentCapacity = this.elements.length;

        // 是否超出当前数组的最大容量
        if (currentCapacity < capacity) {
            // 新容量设置为原来容量的 1.5 倍
            int newCapacity = currentCapacity + (currentCapacity >> 1);
            E[] newElements = (E[]) new Object[newCapacity];

            // 拷贝原数组到新数组中
            for (int i = 0; i < currentCapacity; i++)
                newElements[i] = this.elements[i];

            this.elements = newElements;
        }
    }

    /**
     * 检查二叉堆是否为空
     */
    private void emptyCheck() {
        if (this.size == 0)
            throw new IndexOutOfBoundsException("heap is empty~");
    }

    /**
     * 检查添加的元素是否为 null
     *
     * @param element 添加的元素
     */
    private void elementNotNullCheck(E element) {
        if (element == null)
            throw new IllegalArgumentException("element must not be null~");
    }

    /**
     * index 位置上的元素上滤
     *
     * @param index 索引
     */
    private void shiftUp(int index) {
        E element = this.elements[index]; // 暂时保存添加的结点\
        int parentIndex;
        E parentElement;

        while (index > 0) { // index 仍不是根结点则继续循环
            parentIndex = (index - 1) >> 1; // 更新父结点索引
            parentElement = this.elements[parentIndex]; // 获取父结点元素

            if (this.compare(element, parentElement) <= 0) break; // 满足大根堆性质时退出循环

            this.elements[index] = parentElement; // 父节点元素下榻
            index = parentIndex; // 继续向上遍历
        }

        this.elements[index] = element; // 确定了 element 的最终位置并赋值
    }

    /**
     * index 位置上的元素下滤
     *
     * @param index 索引
     */
    private void shiftDown(int index) {
        E element = this.elements[index]; // 获取待下滤的元素

        int lastNotLeafIndex = (this.size >> 1) - 1; // 获取最后一个非叶子结点的索引(n/2-1)
        int childIndex;
        E childElement;

        // 开始迭代(直到叶子结点停止迭代)
        while (index <= lastNotLeafIndex) {
            childIndex = (index << 1) + 1; // 获取左子结点的索引(2*i+1)(非叶子结点必有左子结点)
            childElement = this.elements[childIndex];

            // 对比右子结点找出较大者
            int rightIndex = childIndex + 1;
            if (rightIndex <= this.size - 1
                    && this.compare(this.elements[rightIndex], childElement) > 0)
                childElement = this.elements[childIndex = rightIndex];

            if (this.compare(childElement, element) <= 0) break;

            // 继续迭代
            this.elements[index] = childElement;
            index = childIndex;
        }

        this.elements[index] = element; // 确定元素的最终位置并赋值
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int) node << 1) + 1;
        return index >= this.size ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = ((int) node << 1) + 2;
        return index >= this.size ? null : index;
    }

    @Override
    public Object string(Object node) {
        return this.elements[(int) node];
    }
}
