package com.lx.第二季.动态规划.src;

// 0-1 背包问题
public class Knapsack {
    /**
     * 动态规划求承重为 capacity，有 values.length 件物品可选时恰好装满背包的最大总价值(每件物品至多选一次)
     *
     * @param values   物品的价值数组, weights.length == values.length
     * @param weights  物品的重量数组, weights.length == values.length
     * @param capacity 背包的最大承重
     * @return 最大总价值, 0 表示无法装满背包
     */
    public static int knapsack3(int[] values, int[] weights, int capacity) {
        if (values == null || weights == null) return 0;
        if (values.length == 0 || weights.length == 0) return 0;
        if (values.length != weights.length) return 0;

        // 1.定义状态: dp[j] 表示在前 k (k 为常数)件物品中挑选总重量恰好为 j (j 为变量)的物品的最大总价值
        int[] dp = new int[capacity + 1];

        // 2.定义初始状态:
        // dp[0] = 0;(表示挑选了一件物品后恰好装满背包)
        // dp[j] = -∞;(表示无法装满背包)
        for (int j = 1; j < dp.length; j++)
            dp[j] = Integer.MIN_VALUE;

        for (int i = 1; i < values.length; i++) {
            // 有能力选择第 i 件物品的情况
            for (int j = capacity; j >= weights[i - 1]; j--) {
                // Math.max(不选择第 i 件物品,选择了第 i 件物品)
                dp[j] = Math.max(dp[j], values[i - 1] + dp[j - weights[i - 1]]);
            }
        }

        return Math.max(dp[capacity], 0); // 如果 dp[capacity] 是不合理的值则返回 0 表示无法装满背包
    }

    /**
     * 动态规划求承重为 capacity，有 values.length 件物品可选时背包的最大总价值(每件物品至多选一次)
     *
     * @param values   物品的价值数组, weights.length == values.length
     * @param weights  物品的重量数组, weights.length == values.length
     * @param capacity 背包的最大承重
     * @return 最大总价值
     */
    public static int knapsack2(int[] values, int[] weights, int capacity) {
        if (values == null || weights == null) return 0;
        if (values.length == 0 || weights.length == 0 || capacity == 0) return 0;

        // 1.定义状态: dp[j] 表示最大承重为 j (j 为变量)，有前 k (k 为常数)件物品可选时的最大总价值。
        int[] dp = new int[capacity + 1];

        // 2.定义初始状态: dp[j] == dp[0] = 0 (默认初始化)

        // 3.定义状态转移方程
        for (int i = 1; i <= values.length; i++) {
            // j 的下界改为 weights[i - 1]，跳过当前承重 j 无法选择第 i 件物品的情况
            for (int j = capacity; j >= weights[i - 1]; j--) { // 从后往前遍历
                // 承重 j 可以选择第 i 件物品
                // Math.max(最终选择了第 i 件物品, 最终不选择第 i 件物品)
                dp[j] = Math.max(
                        values[i - 1] + dp[j - weights[i - 1]], // 用到上一行左边部分的 dp 值
                        dp[j] // 用到上边的 dp
                );
            }
        }

        return dp[capacity];
    }

    /**
     * 动态规划求承重为 capacity，有 values.length 件物品可选时背包的最大总价值(每件物品至多选一次)
     *
     * @param values   物品的价值数组, weights.length == values.length
     * @param weights  物品的重量数组, weights.length == values.length
     * @param capacity 背包的最大承重
     * @return 最大总价值
     */
    public static int knapsack1(int[] values, int[] weights, int capacity) {
        if (values == null || weights == null) return 0;
        if (values.length == 0 || weights.length == 0 || capacity == 0) return 0;

        // 1.定义状态: dp[i][j] 表示最大承重为 j，有前 i 件物品可选时的最大总价值。
        int[][] dp = new int[values.length + 1][capacity + 1];

        // 2.定义初始状态: dp[0][j] == dp[i][0] = 0 (默认初始化)

        // 3.定义状态转移方程
        for (int i = 1; i <= values.length; i++) {
            for (int j = 1; j <= capacity; j++) {
                // 承重 j 不足以选择第 i 件物品
                if (j < weights[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 承重 j 可以选择第 i 件物品
                    // Math.max(最终选择了第 i 件物品, 最终不选择第 i 件物品)
                    dp[i][j] = Math.max(
                            values[i - 1] + dp[i - 1][j - weights[i - 1]],
                            dp[i - 1][j]
                    );
                }
            }
        }

        return dp[values.length][capacity];
    }

    public static void main(String[] args) {
        System.out.println(knapsack1(
                new int[]{6, 3, 5, 4, 6},
                new int[]{2, 2, 6, 5, 4},
                10
        ));

        System.out.println(knapsack2(
                new int[]{6, 3, 5, 4, 6},
                new int[]{2, 2, 6, 5, 4},
                10
        ));

        System.out.println(knapsack3(
                new int[]{6, 3, 5, 4, 6},
                new int[]{2, 2, 6, 5, 4},
                13
        ));
    }
}
