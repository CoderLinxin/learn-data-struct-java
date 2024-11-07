package com.lx.第二季.排序.cmp;

public class BubbleSort3<E extends Comparable<E>> extends Sort<E> {
    /**
     * 冒泡排序优化 2: 序列尾部局部有序时调整下限，实际上优化 2 也包括了优化 1 的情况(sortIndex 初始值设为 1)
     * 最好: O(N): 序列完全有序时仅迭代一次
     * 平均: O(N^2)
     * 最坏: O(N^2)
     *
     */
    @Override
    protected void sort() {
        for (int end = this.array.length - 1; end >= 1; end--) {
            int sortIndex = 1; // 假设本趟冒泡排序的序列已经完全有序(实际上取 0、-1... 都可以)

            for (int begin = 1; begin <= end; begin++) {
                if (this.cmp(begin - 1, begin) > 0) {
                    this.swap(begin - 1, begin);

                    // 这里之所以赋值为 begin 而不是 begin-1，
                    // 是因为 sortIndex 赋值给 end 后，end 会进行 -- 的操作
                    // （所以实际上下轮比较的上限仍然是所希望的 begin-1）
                    sortIndex = begin;
                }
            }

            end = sortIndex;
        }
    }
}
