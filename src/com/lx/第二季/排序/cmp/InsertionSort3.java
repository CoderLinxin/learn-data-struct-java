package com.lx.第二季.排序.cmp;

public class InsertionSort3<E extends Comparable<E>> extends Sort<E> {
    /**
     * 插入排序优化 2: 由于待插入元素的目标序列是有序的,因此使用二分法查找待插入元素的位置
     * 最好: O(N): 序列完全有序时仅迭代一次
     * 平均: O(N^2)
     * 最坏: O(N^2)
     */
    @Override
    protected void sort() {
        for (int begin = 1; begin <= this.array.length - 1; begin++) { // 从索引 1 开始调整元素
            // 待插入元素位置不需要调整的情况就不需要迭代
            if (this.cmp(begin, begin - 1) >= 0) continue;
            this.insert(begin, this.search(begin));
        }
    }

    /**
     * source 位置的元素插入到 dest 位置,
     * source > dest
     *
     * @param source 源位置
     * @param dest   目标位置
     */
    private void insert(int source, int dest) {
        E value = this.array[source]; // 保存待插入元素

        // [dest, source-1] 中的元素向后挪动一位
        while (source > dest) {
            this.array[source] = this.array[source - 1];
            source--;
        }

        this.array[dest] = value;
    }

    /**
     * 二分法查找 this.array[index] 在 this.array 中的插入位置
     *
     * @param index 待插入元素的索引
     * @return 插入的元素最终在 this.array 中的索引
     */
    public int search(int index) {
        // 查找区间为 [begin, end)
        int begin = 0;
        int end = index;
        int mid;
        E value = this.array[index];

        // begin < end 的情况说明搜索区间存在，可以迭代
        while (begin < end) {
            mid = (begin + end) >> 1;

            if (this.cmp(value, this.array[mid]) < 0)
                end = mid;
            else  // value >= this.array[mid] 的情况，为了避免算法不稳定, 查找区间继续右移
                begin = mid + 1;
        }

        // begin == end 的情况说明找到了插入位置
        return begin; // 返回 begin 或 end 都可以
    }
}
