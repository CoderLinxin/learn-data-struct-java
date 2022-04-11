package com.lx.第二季.动态规划.src;

// 最长上升子序列问题:https://leetcode-cn.com/problems/longest-increasing-subsequence/
public class LengthOfLIS {
    /**
     * 贪心 + 二分搜索 解最长上升子序列问题
     * 由于 tops 数组是一个递增序列，因此在寻找目标牌堆时采用二分搜索方式来进行
     * 空间复杂度: O(n)
     * 时间复杂度: O(nlogn)
     *
     * @param nums 源序列
     * @return 最长上升子序列长度
     */
    public int lengthOfLIS3(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        // 用于存放每个牌堆的堆顶元素的数组(最坏情况下可能一个元素自成一个牌堆)
        int[] tops = new int[nums.length];
        int len = 0; // 记录牌堆数量(最长上升子序列长度)

        for (int num : nums) { // O(n)
            // 查找区间为 [begin, end)
            int begin = 0;
            int end = len;
            int mid;

            // begin < end 的情况说明搜索区间存在，可以迭代
            while (begin < end) { // O(logn)
                mid = (begin + end) >> 1;

                // 查找第一个大于等于 value 的元素位置
                if (num <= tops[mid])
                    end = mid;
                else
                    begin = mid + 1;
            }

            // begin == end 的情况说明找到了插入位置
            tops[begin] = num;
            if (begin == len) len++; // 插入位置索引为 len，则说明建立了新牌堆
        }

        return len;
    }

    /**
     * 贪心算法解最长上升子序列问题
     * 空间复杂度: O(n)
     * 时间复杂度: O(n^2)
     *
     * @param nums 源序列
     * @return 最长上升子序列长度
     */
    public int lengthOfLIS2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        // 用于存放每个牌堆的堆顶元素的数组(最坏情况下可能一个元素自成一个牌堆)
        int[] tops = new int[nums.length];
        int len = 0; // 记录牌堆数量(最长上升子序列长度)

        // nums 序列所有元素试图加入牌堆(寻找以 nums[j] 结尾的最长上升子序列长度)
        for (int num : nums) { // O(n)
            int i = 0;

            while (i < len) { // O(n)
                // 跳过所有比 num 小的堆顶元素
                if (num <= tops[i]) {
                    tops[i] = num; // 覆盖牌堆的堆顶元素
                    break;
                }

                i++;
            }

            if (i == len) { // 新建牌堆的情况
                tops[i] = num;
                len++;
            }
        }

        return len;
    }

    /**
     * 动态规划解最长上升子序列问题
     * 时间复杂度: O(n^2)
     * 空间复杂度: O(n)
     *
     * @param nums 源序列
     * @return 最长上升子序列长度
     */
    public int lengthOfLIS1(int[] nums) {
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
