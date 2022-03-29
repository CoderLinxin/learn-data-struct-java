package com.lx.第二季.并查集.src;

/**
 * quick union 基于 size 的优化实现并查集
 */
public class QuickUnion_Size extends QuickUnion {
    // 用于记录每个集合结点总数量的数组(使用集合的根结点索引 sizes)
    private final int[] sizes;

    public QuickUnion_Size(int capacity) {
        super(capacity);

        this.sizes = new int[capacity];

        for (int i = 0; i < sizes.length; i++)
            sizes[i] = 1; // 每个集合初始的结点数量都是 1
    }

    /**
     * 合并两个集合
     */
    @Override
    public void union(int v1, int v2) {
        int root1 = this.find(v1);
        int root2 = this.find(v2);

        if (root1 == root2) return;

        // v1 集合结点数量少于 v2 集合结点数量则 root1 嫁接到 root2 上
        if (this.sizes[root1] < this.sizes[root2]) {
            this.parents[root1] = root2;
            this.sizes[root2] += this.sizes[root1]; // 更新 size
        } else { // v2 集合结点数量少于等于 v2 集合结点数量则 root2 嫁接到 root1 上
            this.parents[root2] = root1;
            this.sizes[root1] += this.sizes[root2]; // 更新 size
        }
    }
}
