package com.lx.第一季.线性表.动态数组.src;

import com.lx.第一季.线性表.链表.src.AbstractList;

/**
 * 实现一个动态数组（java 中的 ArrayList）
 *
 * @param <E> 泛型类让动态数组的元素类型更加丰富(允许存储 null)
 */
public class MyArrayList<E> extends AbstractList<E> {
    /**
     * 承载动态数组的所有元素
     */
    private E[] elements;
    /**
     * 动态数组默认容量：因为所有动态数组实例共享一份，所以是静态变量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 默认构造器创建一个固定容量的动态数组
     */
    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 创建指定容量的动态数组
     *
     * @param capacity 数组的容量大小
     */
    public MyArrayList(int capacity) {
        // 容量数值不合法默认设置成固定容量数值
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        this.elements = (E[]) new Object[capacity];
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

    public void clear() {
        // 数组元素为引用类型时，清空数组时由于引用指向的对象不可重复利用，数据元素需要置 null
        for (int i = 0; i < this.size; i++)
            this.elements[i] = null;

        // 叛党是否需要缩容
        if (this.elements != null && this.elements.length > DEFAULT_CAPACITY)
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];

        this.size = 0;
    }

    public void add(int index, E element) {
        if (this.isIndexOutOfRangeForAdd(index))
            this.outOfRangeTips(index);

        this.ensureCapacity(this.size + 1);

        // 元素插入到 index 位置，则 size ~ index+1 位置上的元素将因前一个元素后移而被覆盖
        // i 标记所有后移过程中依次被覆盖的元素
        for (int i = this.size++; i >= index + 1; i--)
            this.elements[i] = this.elements[i - 1];

        this.elements[index] = element;
    }

    public E get(int index) {
        if (this.isIndexOutOfRange(index))
            this.outOfRangeTips(index);

        return this.elements[index];
    }

    public E set(int index, E element) {
        E oldElement = this.get(index);
        this.elements[index] = element;

        return oldElement;
    }

    public E remove(int index) {
        E removeElement = this.get(index);

        // 删除 index 位置上的元素，则 index+1 ~ size - 1 上的元素都需前移
        // i 标记所有需要前移的元素
        for (int i = index + 1; i <= this.size - 1; i++)
            this.elements[i - 1] = this.elements[i];

        // 由于数组元素可能是引用类型数据，最后第 size-1 个元素需置空
        this.elements[--this.size] = null;

        // 判断是否需要缩容
        this.trim();
        return removeElement;
    }

    /**
     * 删除匹配到的第一个元素
     *
     * @param element 需要删除的元素值
     * @return 删除元素的索引
     */
    public int remove(E element) {
        int index = this.indexOf(element);
        if (index != ELEMENT_NOT_FOUND) {
            this.remove(index);
            return index;
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 当数组超过指定容量时进行缩容
     */
    private void trim() {
        int oldCapacity = this.elements.length;
        int newCapacity = oldCapacity * 3 / 4;

        // 剩余容量大于 1/2 时进行缩容
        if (this.size < oldCapacity / 2 && oldCapacity > DEFAULT_CAPACITY) {
            E[] newElements = (E[]) new Object[newCapacity];

            // 拷贝原数组
            for (int i = 0; i < this.size; i++)
                newElements[i] = this.elements[i];

            this.elements = newElements;
        }
    }

    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < this.size; i++)
                if (this.elements[i] == null) return i;
        } else {
            for (int i = 0; i < this.size; i++)
                // 采用 equals 统一进行比较而不是 == 号（不重写 equals 的情况下等价于 == 运算符）
                // 基本数据类型的包装类默认实现该方法为比较两个基本数据类型的值
                // 引用数据类型默认实现该方法为比较两个引用的地址，好处是可重写该方法自定义比较规则
                if (element.equals(this.elements[i])) return i;
        }

        return ELEMENT_NOT_FOUND;
    }

    /**
     * 遍历数组
     */
    public void traver() {
        System.out.print("[");
        for (int i = 0; i < this.size; i++) {
            if (i != 0)
                System.out.print(",");
            System.out.print(" " + this.elements[i]);
        }
        System.out.print(" ]");
    }
}
