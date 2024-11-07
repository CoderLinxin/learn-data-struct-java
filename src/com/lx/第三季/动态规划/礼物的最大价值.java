package com.lx.第三季.动态规划;

// https://leetcode-cn.com/problems/li-wu-de-zui-da-jie-zhi-lcof/
public class 礼物的最大价值 {
    /**
     * 经过棋盘中的每个格子能够获得一个礼物,礼物存在价值,
     * 行走时只能往下或往右,要求从棋盘的左上角走到右下角能获得的礼物的最大价值
     * 动态规划实现: dp 数组空间复杂度优化
     * 时间复杂度:O(mn), m 为棋盘的行数,n 为棋盘的列数
     * 空间复杂度:O(n)
     *
     * @param grid 二维棋盘
     * @return 礼物的最大价值
     */
    public int maxValue2(int[][] grid) {
        // 1.定义状态: dp[k][col] 表示从左上角走到 [k][col] 位置时的最大值(k 为常数,col 为变量)
        int[] dp = new int[grid[0].length]; // dp 数组仅使用一行的大小
        int rows = grid.length;
        int cols = grid[0].length;

        // 2.设置初始状态:
        dp[0] = grid[0][0];

        // 初始化第一行的 dp 值
        for (int col = 1; col < cols; col++)
            dp[col] = dp[col - 1] + grid[0][col];

        for (int row = 1; row < rows; row++) {
            dp[0] = dp[0] + grid[row][0];
            for (int col = 1; col < cols; col++)
                // 3.定义状态转移方程: dp[k][col] = Math.max(左边的dp值,上面的dp)+当前位置的礼物价值
                dp[col] = Math.max(dp[col], dp[col - 1]) + grid[row][col];
        }

        return dp[cols - 1];
    }

    /**
     * 经过棋盘中的每个格子能够获得一个礼物,礼物存在价值,
     * 行走时只能往下或往右,要求从棋盘的左上角走到右下角能获得的礼物的最大价值
     * 动态规划实现
     * 时间复杂度:O(mn), m 为棋盘的行数,n 为棋盘的列数
     * 空间复杂度:O(mn)
     *
     * @param grid 二维棋盘
     * @return 礼物的最大价值
     */
    public int maxValue(int[][] grid) {
        // 1.定义状态: dp[row][col] 表示从左上角走到 [row][col] 位置时的最大值
        int[][] dp = new int[grid.length][grid[0].length];
        int rows = dp.length;
        int cols = dp[0].length;

        // 2.设置初始状态:
        dp[0][0] = grid[0][0];

        // 初始化第一列的 dp 值
        for (int row = 1; row < rows; row++)
            dp[row][0] = dp[row - 1][0] + grid[row][0];
        // 初始化第一行的 dp 值
        for (int col = 1; col < cols; col++)
            dp[0][col] = dp[0][col - 1] + grid[0][col];

        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++)
                // 3.定义状态转移方程: dp[row][col] = Math.max(左边的dp值,上面的dp)+当前位置的礼物价值
                dp[row][col] = Math.max(dp[row - 1][col], dp[row][col - 1]) + grid[row][col];
        }

        return dp[rows - 1][cols - 1];
    }
}
