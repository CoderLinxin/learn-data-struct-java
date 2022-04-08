package com.lx.第二季.动态规划.src;

// 最大连续子序列和问题:https://leetcode-cn.com/problems/maximum-subarray/
public class MaxSubArray {
    /**
     * 动态规划求 nums 的最大连续子序列和(进一步降低空间复杂度)
     * 因为注意到求 dp[i] 仅需知道 dp[i-1] 一个数,那么仅使用一个整型数据存储 dp 即可
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     *
     * @param nums 源数列
     * @return 最大连续子序列和
     */
    public static int maxSubArray2(int[] nums) {
        // 1.定义 dp: dp 表示以 num[i] 结尾的最大连续子序列和
        // 2.定义初始状态
        int dp = nums[0];

        int max = dp;

        for (int i = 1; i < nums.length; i++) {
            dp = dp > 0 ? dp + nums[i] : nums[i]; // 根据旧的 dp 得到新的 dp

            max = Math.max(max, dp);
        }

        return max;
    }

    /**
     * 动态规划求 nums 的最大连续子序列和
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param nums 源数列
     * @return 最大连续子序列和
     */
    public static int maxSubArray1(int[] nums) {
        // 1.定义 dp[i]: dp[i] 表示以 num[i] 结尾的最大连续子序列和
        int[] dp = new int[nums.length];

        // 2.定义初始状态
        dp[0] = nums[0]; // 只有一个数的最大连续子序列和为其本身
        int max = dp[0]; // 记录最大连续子序列和

        for (int i = 1; i < nums.length; i++) {
            int dpi_1 = dp[i - 1];

            dp[i] = dpi_1 > 0 ? dpi_1 + nums[i] : nums[i]; // 如果 dp[i - 1] > 0 则 dp[i] 要加上这一项

            max = Math.max(max, dp[i]);
        }

        return max;
    }

    public static void main(String[] args) {
    }
}
