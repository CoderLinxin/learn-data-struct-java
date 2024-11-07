package com.lx.第二季.并查集.src;

/**
 * 基于 quick union 的 rank + 路径减半 优化
 */
public class QuickUnion_Rank_Path_Halving extends QuickUnion_Rank {
    public QuickUnion_Rank_Path_Halving(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        this.rangeCheck(v);

        int parent = this.parents[v];

        // 路径上每隔一个结点的父结点指针指向其祖父结点
        while (v != parent) {
            // v 的父结点指针指向祖父结点, 并且 v 向上迈两步
            v = this.parents[v] = this.parents[parent];
            parent = this.parents[v];
        }

        return parent;
    }
}
