package com.lx.第三季.数组_排序;

// https://leetcode-cn.com/problems/merge-sorted-array/submissions/
public class 合并两个有序数组 {
    /**
     * 合并两个有序数组 nums1、nums2，将合并结果存储在 nums1 中(nums1 拥有足够的容量)
     * 最好时间复杂度: O(n)
     * 最坏时间复杂度: O(m+n)
     * 空间复杂度: O(1)
     *
     * @param nums1 有序数组1
     * @param m     nums1 的有效元素数
     * @param nums2 有序数组2
     * @param n     nums2 的有效元素数
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums1 == null || nums2 == null) return;
        if (n == 0) return;

        int i1 = m - 1; // 指向 nums1 最后一个待比较元素
        int i2 = n - 1; // 指向 nums2 最后一个待比较元素
        int cur = nums1.length - 1; // 从后往前存放正确的元素

        // i2 指针只要变为 -1 就表示合并 num1、nums2 成功
        while (i2 >= 0) {
            if (i1 >= 0 && nums1[i1] > nums2[i2])
                nums1[cur--] = nums1[i1--];
            else // i1 == -1 或 nums2[i2] >= nums1[i1] 的情况
                nums1[cur--] = nums2[i2--];
        }
    }
}
