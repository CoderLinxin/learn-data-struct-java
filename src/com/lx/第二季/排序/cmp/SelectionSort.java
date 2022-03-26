package com.lx.第二季.排序.cmp;

public class SelectionSort<E extends Comparable<E>> extends Sort<E> {
    /**
     * 选择排序(稳定排序)
     * 最好: O(N^2): 每轮迭代仍需查找整个无序序列才能确定最大值
     * 平均: O(N^2)
     * 最坏: O(N^2)
     *
     */
    @Override
    protected void sort() {
        for (int end = this.array.length - 1; end >= 1; end--) {
            int maxIndex = 0;

            for (int begin = 1; begin <= end; begin++) {
                // 为了使得选择排序是稳定的,判定条件要包括 =, 目的是为了找到相同元素的最右边的元素然后放到最右边的有序序列中
                // 1 10(1) 10(2) 3      maxIndex = 2
                // 1 10(1) 3 10(2)
                // 1 3 10(1) 10(2)
                if (this.cmp(maxIndex, begin) <= 0) // 记录最大值索引
                    maxIndex = begin;
            }

            this.swap(maxIndex, end);
        }
    }
}
