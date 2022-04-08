package com.lx.第二季.动态规划.src;

// 最长上升子序列问题:https://leetcode-cn.com/problems/longest-increasing-subsequence/
public class LengthOfLIS {
    /**
     * 动态规划解最长上升子序列问题
     * 时间复杂度: O(n^2)
     * 空间复杂度: O(n)
     *
     * @param nums 源序列
     * @return 最长上升子序列长度
     */
    public int lengthOfLIS(int[] nums) {
        // 1.定义状态: dp[i] 表示以 nums[i] 结尾的最长上升子序列的长度
        int[] dp = new int[nums.length];

        // 2.设置初始状态
        int max = dp[0] = 1;

        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1; // 最坏情况下 dp[i] 为 1

            // 求 dp[i] 的值，需要遍历 dp[0]~dp[i-1],在每个 nums[j] 后尝试接上 nums[i]
            for (int j = 0; j < i; j++) {
                /* 3.定义状态转移方程 */

                if (nums[i] <= nums[j]) continue; // nums[i] 不能接在 nums[j] 后面

                // nums[i] 可以接在 nums[j] 后面,得到一个 dp[i] 的候选值,即以 nums[j] 结尾的最长上升子序列长度(dp[j]) +1
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }

            max = Math.max(max, dp[i]); // 取最大的 dp[i]
        }

        return max;
    }

    public static void main(String[] args) {

    }
}
