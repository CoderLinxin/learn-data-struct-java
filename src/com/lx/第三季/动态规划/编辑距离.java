package com.lx.第三季.动态规划;

// https://leetcode-cn.com/problems/edit-distance/submissions/
public class 编辑距离 {
    /**
     * 计算出 word1 转换为 word2 所需的最少操作数量: 优化 dp 的空间复杂度
     * 由于推导 dp[i][j] 仅需用到 左边、上边、左上角的 dp 值，因此可以将 dp 数组优化至一维
     * 只能对单词进行以下三种操作:
     * 插入一个字符
     * 删除一个字符
     * 替换一个字符
     * 时间复杂度: O(m*n), m、n 分别为 word1、word2 的长度
     * 空间复杂度: O(n)
     *
     * @param word1 单词1
     * @param word2 单词2
     * @return 最少操作数量
     */
    public int minDistance2(String word1, String word2) {
        if (word1 == null || word2 == null) return 0;
        if (word1.length() == 0) return word2.length();
        if (word2.length() == 0) return word1.length();

        char[] word1Chars = word1.toCharArray();
        char[] word2Chars = word2.toCharArray();

        // 1.定义状态:
        // dp[j] 表示 [word1[0] ~ word1[k]) (k为常数)转换为 [word2[0] ~ word2[j]) 所需的最少操作数量
        int[] dp = new int[word2Chars.length + 1];

        // 2.设置初始状态:
        for (int j = 1; j < dp.length; j++) {
            // '' 转换为 [word2[0] ~ word2[j]) 的最少操作数就是插入 word2 的所有字符
            dp[j] = j;
        }

        for (int i = 1; i < word1Chars.length + 1; i++) {
            // 初始化 '左上角' 的 dp 值
            int leftTop = dp[0];
            // 迭代新的一行的 dp 值前，初始化第一列的 dp 值
            dp[0] = i;

            for (int j = 1; j < dp.length; j++) {
                // 3.定义状态转移方程: 求 [word1[0] ~ word1[i]) 转换为 [word1[0] ~ word1[j]) 的最小操作数
                int first = 1 + dp[j]; // 用到上边的 dp 值
                int second = dp[j - 1] + 1; // 用到左边的 dp 值
                int third = word1Chars[i - 1] == word2Chars[j - 1] ? leftTop : leftTop + 1; // 用到左上角的 dp 值

                leftTop = dp[j]; // 更新左上角的 dp 值

                dp[j] = Math.min(Math.min(first, second), third);
            }
        }

        return dp[dp.length - 1];
    }

    /**
     * 计算出 word1 转换为 word2 所需的最少操作数量
     * 只能对单词进行以下三种操作:
     * 插入一个字符
     * 删除一个字符
     * 替换一个字符
     * 时间复杂度: O(m*n), m、n 分别为 word1、word2 的长度
     * 空间复杂度: O(m*n)
     *
     * @param word1 单词1
     * @param word2 单词2
     * @return 最少操作数量
     */
    public int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) return 0;
        if (word1.length() == 0) return word2.length();
        if (word2.length() == 0) return word1.length();

        char[] word1Chars = word1.toCharArray();
        char[] word2Chars = word2.toCharArray();

        // 1.定义状态:
        // dp[i][j] 表示 [word1[0] ~ word1[i]) 转换为 [word2[0] ~ word2[j]) 所需的最少操作数量
        int[][] dp = new int[word1Chars.length + 1][word2Chars.length + 1];

        // 2.设置初始状态:
        // '' 转换为 '' 所需的最少操作数为 0
        dp[0][0] = 0;
        for (int i = 1; i < dp.length; i++) {
            // [word1[0] ~ word1[i]) 转换为 '' 的最少操作数就是删除 word1 的所有字符
            dp[i][0] = i;
        }
        for (int j = 1; j < dp[0].length; j++) {
            // '' 转换为 [word2[0] ~ word2[j]) 的最少操作数就是插入 word2 的所有字符
            dp[0][j] = j;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                // 3.定义状态转移方程: 求 [word1[0] ~ word1[i]) 转换为 [word1[0] ~ word1[j]) 的最小操作数
                // 第一种情况: [word1[0] ~ word1[i]) 删除尾字符(操作次数1)得到 [word1[0] ~ word1[i-1]) 然后再转换为 [word2[0] ~ word2[j])
                int first = 1 + dp[i - 1][j]; // 用到上边的 dp 值

                // 第二种情况: [word1[0] ~ word1[i]) 转换为 [word2[0] ~ word2[j-1]) 然后再从尾部插入字符 word2[j] (操作次数1)
                int second = dp[i][j - 1] + 1; // 用到左边的 dp 值

                // 第三种情况: 如果 word1[i-1] == word2[j-1] 则直接将 [word1[0] ~ word1[i-2]] 转换为 [word2[0] ~ word2[j-2]]
                //  hors -> ros:
                //      hor -> ro = ros
                // 第四种情况: 否则将 [word1[0] ~ word1[i-2]] 转换为 [word2[0] ~ word2[j-2]] 后, 新的 word1 的最后一个字符替换为 word2[j]
                //  horse -> ros:
                //      hors -> ro = roe
                //      roe -> ros
                int third = word1Chars[i - 1] == word2Chars[j - 1] ? dp[i - 1][j - 1] : dp[i - 1][j - 1] + 1; // 用到左上角的 dp 值

                dp[i][j] = Math.min(Math.min(first, second), third);
            }
        }

        return dp[dp.length - 1][dp[0].length - 1];
    }

    public static void main(String[] args) {
        new 编辑距离().minDistance2("horse", "ros");
    }
}
