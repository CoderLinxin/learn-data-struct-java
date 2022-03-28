package com.lx.第二季.排序.none_cmp;

import com.lx.第二季.排序.cmp.Sort;

public class RadixSort1 extends Sort<Integer> {
    /**
     * 基数排序(稳定排序(因为基数排序基于计数排序))(非原地排序)
     * 最好:
     * 最坏:
     * 最差:
     * 空间复杂度:
     */
    @Override
    protected void sort() {
        // 1.寻找最大值(以便确定基数排序的趟数)
        int max = this.array[0];
        for (int i = 0; i < this.array.length; i++)
            if (max < this.array[i]) max = this.array[i];

        // 2.对每一位进行一趟计数排序
        for (int divider = 1; divider <= max; divider *= 10)
            this.countingSort(divider);
    }

    /**
     * 对整数的某一位(基数)进行计数排序
     * 得到元素某个位的计算方法:
     * 597 / 1 % 10 = 7
     * 597 / 10 % 10 = 9
     * 597 / 100 % 10 = 5
     *
     * @param divider 除数(用于确定排序依据的基数)
     */
    private void countingSort(int divider) {
        // 1.countArray 数组长度直接定为 10 即可, 因为整数的每一位都为 [0, 9]
        int[] countArray = new int[10];

        // 2.计算所有元素某个位的出现次数
        for (int i = 0; i < this.array.length; i++)
            countArray[(this.array[i] / divider) % 10]++;

        // 3.计算每个元素及其前面元素的累加次数(从第二个位置起才需进行累加)
        for (int i = 1; i <= countArray.length - 1; i++)
            countArray[i] += countArray[i - 1];

        // 4.从尾到头(为了确保稳定性, 而不是从头到尾)遍历 array，并根据 countArray 指定的索引输出每个元素到 output, 成为一个有序序列
        int[] output = new int[this.array.length];
        for (int i = this.array.length - 1; i >= 0; i--) {
            Integer element = (this.array[i] / divider) % 10;
            output[--countArray[element]] = element;
        }

        // 5.将排序结果覆盖回 array
        for (int i = 0; i < this.array.length; i++)
            this.array[i] = output[i];
    }
}
