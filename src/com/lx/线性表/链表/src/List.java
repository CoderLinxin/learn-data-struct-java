package com.lx.线性表.链表.src;

/**
 * 动态数组和链表的公共接口
 */
public interface List<E> {
    /**
     * 未查找到数组时返回的非法索引
     */
    static final int ELEMENT_NOT_FOUND = -1;

    /**
     * 清除所有元素
     */
    void clear();

    /**
     * 获取元素的数量
     *
     * @return 元素的数量
     */
    int size();

    /**
     * 数组是否为空
     *
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 数组中是否包含某个元素
     *
     * @param element 欲查找的元素
     * @return 是否包含元素
     */
    boolean contains(E element);

    /**
     * 添加元素到尾部
     *
     * @param element 欲添加的元素
     */
    void add(E element);

    /**
     * 获取 index 位置的元素
     *
     * @param index 欲访问的索引
     * @return 目标元素
     */
    E get(int index);

    /**
     * 设置index位置的元素
     *
     * @param index   欲设置元素所在的位置
     * @param element 新元素
     * @return 该位置上原来的元素ֵ
     */
    E set(int index, E element);

    /**
     * 插入元素到数组中
     *
     * @param index   插入数组中的位置
     * @param element 插入的目标元素
     */
    void add(int index, E element);


    /**
     * 删除index位置的元素
     *
     * @param index 欲删除元素的位置
     * @return 被删除的元素
     */
    E remove(int index);

    /**
     * 查找某个元素
     *
     * @param element 查找的目标元素
     * @return 返回元素的索引
     */
    int indexOf(E element);

    /**
     * 遍历链表
     */
    void traver();
}
