package com.lx.第二季.排序.none_cmp;

import com.lx.第二季.排序.cmp.Sort;

public class RadixSort2 extends Sort<Integer> {
    /**
     * 基数排序实现2(稳定排序)(非原地排序)
     * 最好:O(dN) d为最大元素的位数
     * 平均:O(dN)
     * 最坏:O(dN)
     * 空间复杂度:O(kN+k) k 为元素的进制,d为最大元素的位数
     */
    @Override
    protected void sort() {
        // 1.首先开辟 10 个数组空间作为桶
        int[][] buckets = new int[10][this.array.length]; // 空间复杂度: O(K*N)

        // 2.找出最大元素以便确定收集和分配的趟数
        int max = this.array[0];
        for (int i = 0; i < this.array.length; i++) // O(N)
            if (max < this.array[i]) max = this.array[i];

        // 3.进行 d 趟分配和收集(d 为 max 的位数)
        for (int divider = 1; divider <= max; divider *= 10) { // O(d)
            // 记录每趟分配的桶中元素数(后续收集桶元素时使用)
            int[] bucketLens = new int[buckets.length]; // 空间复杂度 O(k)

            // 分配
            for (int i = 0; i < this.array.length; i++) { // O(N)
                int index = (this.array[i] / divider) % 10;
                buckets[index][bucketLens[index]++] = this.array[i];
            }

            int index = 0; // 标记 array 的工作指针

            // 收集 O(N)(因为有了 bucketLens 的帮助, 使得空桶无需遍历)
            for (int i = 0; i < buckets.length; i++) // 遍历每个桶
                for (int j = 0; j < bucketLens[i]; j++) // 取出每个桶中的元素
                    this.array[index++] = buckets[i][j];
        }
    }
}
