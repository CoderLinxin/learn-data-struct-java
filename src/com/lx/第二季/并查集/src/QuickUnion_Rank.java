package com.lx.第二季.并查集.src;

/**
 * quick union 基于 rank 的优化实现并查集
 */
public class QuickUnion_Rank extends QuickUnion {
    // 用于记录每个集合树高的数组(使用集合的根结点索引 ranks)
    private final int[] ranks;

    public QuickUnion_Rank(int capacity) {
        super(capacity);

        ranks = new int[capacity];

        for (int i = 0; i < ranks.length; i++)
            ranks[i] = 1; // 初始树高都为 1
    }

    /**
     * 合并两个集合
     */
    @Override
    public void union(int v1, int v2) {
        int root1 = this.find(v1);
        int root2 = this.find(v2);

        if (root1 == root2) return;

        if (this.ranks[root1] < this.ranks[root2]) {
            // root1 索引的树高小于 root2，root1 嫁接到 root2 上,root2 索引的高度不变
            this.parents[root1] = root2;
        } else if (this.ranks[root2] < this.ranks[root1]) {
            // root2 索引的树高小于 root1，root2 嫁接到 root1 上,root1 索引的高度不变
            this.parents[root2] = root1;
        } else {
            // 两树高度相同则 root1 嫁接到 root2 上即可，root2 索引的高度 +1
            this.parents[root1] = root2;
            this.ranks[root2]++;
        }
    }
}
