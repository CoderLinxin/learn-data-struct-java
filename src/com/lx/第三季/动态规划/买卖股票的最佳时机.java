package com.lx.第三季.动态规划;

// https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
public class 买卖股票的最佳时机 {
    /**
     * 计算出买入股票和卖出股票所得利润的最大值(只能买入一次和卖出一次): 动态规划求解
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param prices 多天中每天的股票价格
     * @return 最大利润
     */
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        // differences[i] 表示第 i+1 天 - 第 i 天的股价差
        int[] differences = new int[prices.length - 1];
        // 初始化 differences
        for (int i = 0; i < differences.length; i++)
            differences[i] = prices[i + 1] - prices[i];

        // 1.定义状态: dp[i] 表示第 i 天作为卖出天所能获得的最大利润(可优化至 2 个元素容量)
        int[] dp = new int[prices.length];

        // 2.定义初始状态: dp[0] 表示第一天作为卖出天所得的最大利润:0
        dp[0] = 0;
        int maxProfit = dp[0];

        for (int i = 1; i < dp.length; i++) {
            // 获取第 i 天 - 第 i-1 天的股价差
            int difference = differences[i - 1];
            // 3.定义状态转移方程:
            // 如果 dp[i-1] > 0, 即以第 i-1 天作为卖出天有利可图, 则将该利润加上第 i 天与第 i-1 天的股价差作为第 i 天卖出股票的利润
            dp[i] = dp[i - 1] > 0 ? dp[i - 1] + difference : difference;
            maxProfit = Math.max(maxProfit, dp[i]);
        }

        return maxProfit;
    }

    /**
     * 计算出买入股票和卖出股票所得利润的最大值(只能买入一次和卖出一次)
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     *
     * @param prices 多天中每天的股票价格
     * @return 最大利润
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        int maxProfit = 0; // 记录最大利润
        int min = prices[0]; // 记录第 0~i-1 天中股票价格达到最小的那天(作为买入天)

        // 考察第 1~prices.length 任一天作为卖出天的可能
        for (int i = 1; i < prices.length; i++) {
            // 当前天的股票价格比过去天数中最小股票价格还小(因此当前天不能作为股票卖出天)
            if (prices[i] < min) {
                min = prices[i]; // 更新第i天为前面出现的最小价格股票的天数
            } else if (prices[i] > min) {
                // 计算当前天作为卖出天得到的利润并试图更新 maxProfit
                maxProfit = Math.max(maxProfit, prices[i] - min);
            }
        }

        return maxProfit;
    }
}
