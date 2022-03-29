package com.lx.第二季.并查集.src;

/**
 * Quick Find 思路实现并查集: 并查集中任意元素的父结点指针就是其所属集合的根结点指针
 */
public class QuickFind extends UnionFind {
    public QuickFind(int capacity) {
        super(capacity);
    }

    /**
     * 查找元素 v 所属集合
     *
     * @param v 待查找的元素
     * @return v 所在集合的根结点索引本案例中也是 v 的父结点索引
     */
    @Override
    public int find(int v) { // O(1)
        this.rangeCheck(v);

        return this.parents[v];
    }

    /**
     * 合并 v1、v2 所属集合
     *
     * @param v1 元素1
     * @param v2 元素2
     */
    @Override
    public void union(int v1, int v2) {
        // 1.查找 v1、v2 的根结点指针(也即 v1、v2 的父结点指针)
        int p1 = this.find(v1);
        int p2 = this.find(v2);

        if (p1 == p2) return; // v1，v2 已经同属一个集合，不需要合并

        // 2.将 v1 所属集合中的所有元素全部合并到 v2 集合中
        for (int i = 0; i < this.parents.length; i++) // O(n)
            // 将 v1 所属集合中的所有元素的父结点(也即根结点)指针指向 p2
            if (this.parents[i] == p1)
                this.parents[i] = p2;
    }
}
