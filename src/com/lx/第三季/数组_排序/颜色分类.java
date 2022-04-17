package com.lx.第三季.数组_排序;

// https://leetcode-cn.com/problems/sort-colors/
public class 颜色分类 {
    /**
     * 对颜色进行排序，从小到大依次为 红(0)、白(1)、蓝(2)
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     *
     * @param nums 颜色数组
     */
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) return;

        int green = 0; // 指向数组最左边待调整位置
        int red = 0; // 用于遍历
        int black = nums.length - 1; // 指向数组最右边待调整位置

        // 从左往右遍历
        while (red <= black) {
            switch (nums[red]) {
                // red 遇到 0: red > green 时, green 必指向1，交换完元素后 red、green 都后移即可
                case 0:
                    this.swap(nums, red++, green++);
                    break;
                // red 遇到 1, 直接跳过
                case 1:
                    red++;
                    break;
                // red 遇到 2,由于 black 可能指向 0 或 1，交换元素后 red 指针需要保持不变、black--
                case 2:
                    this.swap(nums, red, black--);
                    break;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
