package com.lx.第二季.排序;

/**
 * 二分搜索算法
 *
 */
public class BinarySearch {
    /**
     * 使用二分法查找 value 在 array 中的位置
     *
     * @param array 升序序列
     * @param value 待查找的元素
     * @return 查找的元素在 array 中的索引
     */
    public int indexOf(int[] array, int value) {
        this.arrayCheck(array);

        // 搜索区间定为 [begin, end)
        int begin = 0;
        int end = array.length;
        int mid;

        // begin 与 end 只有: begin < end 和 begin == end 两种关系
        // 因为在 begin < end 的前提下(搜索区间的长度 >= 1),搜索区间进行调整时:
        // 当 end = mid 时, 由于 (begin + end)/2 >= begin，得到调整后的 end >= begin
        // 当 begin = mid 时, 由于 end >= (begin + end)/2, 得到调整后的 end >= begin
        while (begin < end) {
            mid = (begin + end) >> 1;

            if (array[mid] < value) // 往右边找
                begin = mid + 1; // 前闭
            else if (array[mid] > value) // 往左边找
                end = mid; // 后开
            else return mid;
        }

        return -1;
    }

    private void arrayCheck(int[] array) {
        if (array == null || array.length == 0)
            throw new IllegalArgumentException("array must not be a empty array~");
    }
}
