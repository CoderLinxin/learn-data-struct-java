package com.lx.第二季.并查集.src;

public class QuickUnion extends UnionFind {
    public QuickUnion(int capacity) {
        super(capacity);
    }

    /**
     * 查找 v 所属的集合
     *
     * @param v 待查找的元素
     * @return v 的根结点
     */
    @Override
    public int find(int v) {
        this.rangeCheck(v);

        int parent = this.parents[v]; // v 的父结点指针

        // 向上查找 v 的根结点
        while (parent != v) { // O(logN)
            v = parent;
            parent = this.parents[parent];
        }

        return parent;
    }

    @Override
    public void union(int v1, int v2) {
        int root1 = this.find(v1); // O(logN)
        int root2 = this.find(v2); // O(logN)

        if (root1 == root2) return;

        this.parents[root1] = root2; // v1 根结点嫁接到 v2 的根结点上
    }
}
