package com.lx.第二季.分治.src;

public class ViolentSolution {
    // 暴力法求解 nums 最大连续子序列和
    // 时间复杂度: O(n^2)
    // 空间复杂度: O(1)
    public int solution1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int max = Integer.MIN_VALUE;

        // 查找所有可能的子序列 [begin, end]
        for (int begin = 0; begin < nums.length; begin++) { // 查看所有可能的 begin
            int sum = 0; // 记录每一个可能的最大连续子序列和

            // 注意需要遍历到最后，因为存在最后面有较大的数的可能
            for (int end = begin; end < nums.length; end++) {
                sum += nums[end];
                max = Math.max(max, sum);
            }
        }

        return max;
    }

    public static void main(String[] args) {

    }
}







