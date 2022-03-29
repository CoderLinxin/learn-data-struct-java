package com.lx.第二季.并查集.src;

/**
 * 基于 quick union 的 rank + 路径分裂 优化
 */
public class QuickUnion_Rank_Path_Spliting extends QuickUnion_Rank {
    public QuickUnion_Rank_Path_Spliting(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        this.rangeCheck(v);

        int parent = this.parents[v];

        // 路径上所有结点的父结点指针更新为祖父结点
        while (v != parent) {
            this.parents[v] = this.parents[parent]; // v 的父结点指针指向祖父结点

            // v 继续向上遍历
            v = parent;
            parent = this.parents[v];
        }

        return parent;
    }
}
