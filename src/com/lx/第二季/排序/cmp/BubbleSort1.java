package com.lx.第二季.排序.cmp;

public class BubbleSort1<E extends Comparable<E>> extends Sort<E> {
    /**
     * 冒泡排序(无优化)(稳定排序)
     * 最好: O(N^2)
     * 平均: O(N^2)
     * 最坏: O(N^2)
     *
     */
    @Override
    protected void sort() {
        for (int end = this.array.length - 1; end >= 1; end--) {
            for (int begin = 1; begin <= end; begin++) {
                // 为了使得冒泡排序是稳定的，判断条件不包括 =
                if (this.cmp(begin - 1, begin) > 0)
                    this.swap(begin - 1, begin);
            }
        }
    }
}
