package com.lx.第三季.数组_排序;


// https://leetcode-cn.com/problems/sub-sort-lcci/comments/
public class 部分排序 {
    /**
     * 找出源序列(这里以升序视角来看)的一个区间，使得对该区间内的所有元素进行排序后，整个序列也变得有序
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     *
     * @param array 源序列
     * @return 待排序区间的左右端点: [left, right]
     */
    public int[] subSort(int[] array) {
        if (array == null || array.length == 0) return new int[]{-1, -1};

        int max = array[0]; // 记录 i 遍历过的最大值
        int right = -1; // 记录部分排序区间的右端点

        for (int i = 1; i < array.length; i++) {
            int num = array[i];
            if (num > max) // 更新 max
                max = num;
            else if (num < max) // 找到一个可能的右端点
                right = i;
        }

        if (right == -1) return new int[]{-1, -1}; // 整个序列原本有序

        int min = array[array.length - 1]; // 记录 j 遍历过的最小值
        int left = -1; // 记录部分排序区间的左端点

        for (int j = array.length - 2; j >= 0; j--) {
            int num = array[j];
            if (num < min)
                min = num;
            else if (num > min)
                left = j;
        }

        return new int[]{left, right};
    }

    public static void main(String[] args) {
        System.out.println(new 部分排序().subSort(new int[]{1, 3, 9, 7, 5}));
    }
}
