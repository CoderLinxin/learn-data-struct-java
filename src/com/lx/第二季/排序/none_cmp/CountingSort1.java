package com.lx.第二季.排序.none_cmp;

import com.lx.第二季.排序.cmp.Sort;

public class CountingSort1 extends Sort<Integer> { // 计数排序针对的元素类型为整数
    /**
     * (简单)计数排序(不稳定排序(简单计数排序是不稳定的))(非原地排序)
     */
    @Override
    protected void sort() {
        /* 1.根据元素最大值开辟空间 */
        Integer max = this.array[0];
        for (int i = 0; i < this.array.length; i++)
            if (max < this.array[i]) max = this.array[i];
        int[] countArray = new int[max + 1];

        /* 2.记录每个(整数)元素出现次数 */
        for (int i = 0; i < this.array.length; i++)
            countArray[this.array[i]]++;

        /* 3.根据记录的元素次数重新排列到 array 中 */
        int traverIndex = 0; // 记录 array 当前排列位置的指针
        for (int i = 0; i < countArray.length; i++)
            while (countArray[i]-- > 0)
                this.array[traverIndex++] = i;
    }
}
