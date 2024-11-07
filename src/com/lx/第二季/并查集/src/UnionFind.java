package com.lx.第二季.并查集.src;

/**
 * 并查集公共接口类
 */
public abstract class UnionFind {
    // 本案例的并查集存储的是整型元素
    protected int[] parents;

    /**
     * 初始化并查集:所有元素自成一个集合(其父结点都指向自身)
     * 数组索引表示的是元素本身，数组的值表示的是元素的父结点指针
     *
     * @param capacity 并查集的容量
     */
    public UnionFind(int capacity) {
        this.capacityCheck(capacity);

        this.parents = new int[capacity];

        for (int i = 0; i < capacity; i++)
            parents[i] = i;
    }

    /**
     * 查找元素 v 所属的集合
     *
     * @param v 待查找的元素
     * @return 元素 v 的根结点指针
     */
    public abstract int find(int v);

    /**
     * 合并 v1、v2 所属集合
     *
     * @param v1 元素1
     * @param v2 元素2
     */
    public abstract void union(int v1, int v2);

    /**
     * 判断 v1、v2 是否属于同一个集合
     *
     * @param v1 元素 1
     * @param v2 元素 2
     * @return 是否属于同一个集合
     */
    public boolean isSame(int v1, int v2) {
        return this.find(v1) == this.find(v2);
    }

    /**
     * 确保 capacity 合法
     *
     * @param capacity 并查集容量
     */
    private void capacityCheck(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("capacity must be better than zero~");
    }

    /**
     * 确保索引合法
     *
     * @param v 整型元素索引
     */
    protected void rangeCheck(int v) {
        if (v < 0 || v >= this.parents.length)
            throw new IllegalArgumentException("v must be better than zero~");
    }
}
