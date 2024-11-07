package com.lx.第三季.数组_排序;

public class 有序数组的平方 {
    /**
     * nums 是一个升序的数组，求出 nums 每个元素平方组成的新数组，该新数组也按升序排序
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param nums 源数组
     * @return nums 每个元素平方组成的新数组
     */
    public int[] sortedSquares(int[] nums) {
        if (nums == null || nums.length == 0) return nums;

        int left = 0; // 从左边开始遍历的指针
        int right = nums.length - 1; // 从右边开始遍历的指针

        int[] newNums = new int[nums.length]; // 存储结果的数组
        int i = newNums.length - 1;

        while (left <= right) {
            // 直接取平方和判断大小
            int leftVal = nums[left] * nums[left];
            int rightVal = nums[right] * nums[right];

            if (leftVal < rightVal) {
                newNums[i--] = rightVal;
                right--;
            } else {
                newNums[i--] = leftVal;
                left++;
            }
        }

        return newNums;
    }
}
