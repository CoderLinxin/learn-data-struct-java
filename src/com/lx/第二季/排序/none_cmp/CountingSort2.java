package com.lx.第二季.排序.none_cmp;

import com.lx.第二季.排序.cmp.Sort;

public class CountingSort2 extends Sort<Integer> {
    /**
     * 优化的计数排序(稳定排序)(非原地排序)
     * 最好: O(N+K)
     * 平均: O(N+K)
     * 最坏: O(N+K)
     * 空间复杂度: O(N+K)
     */
    @Override
    protected void sort() {
        // 1.寻找最大、最小值以便确定 countArray 数组大小
        int min = this.array[0];
        int max = this.array[0];
        for (int i = 0; i < this.array.length; i++) { // O(N)
            if (max < this.array[i]) max = this.array[i];
            if (min > this.array[i]) min = this.array[i];
        }
        int[] countArray = new int[max - min + 1]; // O(K)

        // 2.计算每个元素出现次数
        for (int i = 0; i < this.array.length; i++) // O(N)
            countArray[this.array[i] - min]++;

        // 3.计算每个元素及其前面元素的累加次数(从第二个位置起才需进行累加)
        for (int i = 1; i <= countArray.length - 1; i++) // O(K)
            countArray[i] += countArray[i - 1];

        // 4.从尾到头(为了确保稳定性, 而不是从头到尾)遍历 array，并根据 countArray 指定的索引输出每个元素到 output, 成为一个有序序列
        int[] output = new int[this.array.length]; // O(N)
        for (int i = this.array.length - 1; i >= 0; i--) { // O(N)
            Integer element = this.array[i];
            output[--countArray[element - min]] = element;
        }

        // 5.将排序结果覆盖回 array
        for (int i = 0; i < this.array.length; i++) // O(N)
            this.array[i] = output[i];
    }
}
