package com.lx.第一季.线性表.链表.src;

/**
 * 动态数组的链表的抽象父类
 */
public abstract class AbstractList<E> implements List<E> {
    /**
     * 当前数组元素个数
     */
    protected int size;

    /**
     * 获取元素的数量
     *
     * @return 元素的数量
     */
    public int size() {
        return this.size;
    }

    /**
     * 数组是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * 数组中是否包含某个元素
     *
     * @param element 欲查找的元素
     * @return 是否包含元素
     */
    public boolean contains(E element) {
        return this.indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 添加元素到尾部
     *
     * @param element 欲添加的元素
     */
    public void add(E element) {
        this.add(this.size, element);
    }

    /**
     * 检查索引是否越界
     *
     * @param index 欲访问的数组索引
     * @return 是否越界
     */
    protected boolean isIndexOutOfRange(int index) {
        return index >= this.size || index < 0;
    }

    /**
     * 检查插入元素时索引是否越界
     *
     * @param index 欲插入的元素索引
     * @return 是否越界
     */
    protected boolean isIndexOutOfRangeForAdd(int index) {
        return index > this.size || index < 0;
    }

    /**
     * 索引越界提示
     *
     * @param index 出现错误的数组索引
     */
    protected void outOfRangeTips(int index) {
        throw new IndexOutOfBoundsException("输入的索引为:" + index + ", 合法索引范围为: 0 ~ " + (this.size - 1));
    }
}
