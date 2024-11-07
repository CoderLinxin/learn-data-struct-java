package com.lx.test;

import java.util.Arrays;

public class Main {
    /**
     * 数组去重
     *
     * @param nums 源数组
     * @return 去重后的数组
     */
    private int[] duplicateRemoval(int[] nums) {
        if (nums == null || nums.length <= 1) return nums;

        Arrays.sort(nums);
        int cur = 0;

        for (int i = 0; i <= nums.length - 2; i++) {
            if (nums[i] != nums[i + 1]) {
                nums[cur++] = nums[i];

                if (i == nums.length - 2)
                    nums[cur++] = nums[i + 1];
            }
        }

        int[] newNums = new int[cur];

        if (cur >= 0)
            System.arraycopy(nums, 0, newNums, 0, cur);

        return newNums;
    }

    /**
     * 求序列 1 和序列 2 组成的大的序列的中位数
     * 序列 1 和序列 2 都是递增序列
     */
    private int getMedian(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) return Integer.MAX_VALUE;
        if (nums1.length == 0 || nums2.length == 0) return Integer.MAX_VALUE;
        if (nums1.length == 1 && nums2.length == 1) return nums1[0];

        int i = 0, j = 0;
        int position = 1;

        while (true) {
            if (nums1[i] <= nums2[j]){
                if (position == (nums1.length + nums2.length) / 2) return nums1[i];
                position++;
                i++;
            }else{
                if (position == (nums1.length + nums2.length) / 2) return nums2[j];
                position++;
                j++;
            }
        }
    }

    public static void main(String[] args) {
//        System.out.println(Arrays.toString(new Main().duplicateRemoval(new int[]{1, 2, 5, 5, 6, 8, 9, 1, 55, 100})));
        System.out.println(new Main().getMedian(
                new int[]{1, 5, 8, 9, 10},
                new int[]{2, 6, 9, 40, 30}
        ));
    }
}
