package com.lx.第二季.并查集.src;

/**
 * 基于 quick union 的 rank + 路径压缩 优化
 */
public class QuickUnion_Rank_Path_Compression extends QuickUnion_Rank {
    public QuickUnion_Rank_Path_Compression(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        this.rangeCheck(v);

        // 注此判断用于寻找根结点
        if (v != this.parents[v])
            // 将沿途的 v 的父节点指针指向根结点
            parents[v] = find(parents[v]);

        // 返回根结点
        return parents[v];
    }
}
