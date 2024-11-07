package com.lx.第二季.排序.cmp;

public class BubbleSort2<E extends Comparable<E>> extends Sort<E> {
    /**
     * 冒泡排序优化 1: 序列完全有序时结束迭代
     * 最好: O(N): 序列完全有序时仅迭代一次
     * 平均: O(N^2)
     * 最坏: O(N^2)
     *
     */
    @Override
    protected void sort() {
        for (int end = this.array.length - 1; end >= 1; end--) {
            boolean isSorted = true; // 假设本趟冒泡排序的序列已经完全有序

            for (int begin = 1; begin <= end; begin++) {
                if (this.cmp(begin - 1, begin) > 0) {
                    this.swap(begin - 1, begin);

                    isSorted = false;
                }
            }

            if (isSorted) break;
        }
    }
}
