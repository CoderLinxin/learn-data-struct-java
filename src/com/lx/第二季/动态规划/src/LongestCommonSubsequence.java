package com.lx.第二季.动态规划.src;

// 最长公共子序列:https://leetcode-cn.com/problems/longest-common-subsequence/
public class LongestCommonSubsequence {
    /**
     * 求 text1 与 text2 的最长公共子序列长度: 动态规划+优化至一维数组
     * 直接对所有需要映射的索引进行 & 1 运算即可。
     * 时间复杂度: O(n*m), n、m 分别为 text1、text2 的总长度
     * 空间复杂度: O(k), k = min{n,m}
     *
     * @param text1 序列1
     * @param text2 序列2
     * @return 最长公共子序列长度
     */
    public int longestCommonSubsequence4(String text1, String text2) {
        if (text1 == null || text2 == null) return 0;
        int text1Len = text1.length();
        int text2Len = text2.length();
        if (text1.length() == 0 || text2.length() == 0) return 0;

        String rows = text1;
        String cols = text2;

        /* 取长度较小者作为列数 */
        if (text1Len < text2Len) {
            rows = text2;
            cols = text1;
        }

        // 定义状态；dp[col] 表示 rows 前 k(k为常数) 个字符与 cols 的前 col(col 为变量) 个字符的最长公共子序列长度
        int[] dp = new int[cols.length() + 1];

        int leftTop; // 保存左上角元素

        for (int row = 1; row <= rows.length(); row++) {
            int current = dp[0]; // 保存本次迭代被覆盖的旧的 dp[j],每一轮迭代开始前都要初始化为 0(dp[0])

            for (int col = 1; col <= cols.length(); col++) {
                // 或者直接从尾部开始遍历 cols，这样就不用弄一个临时遍历 leftTop
                leftTop = current; // 获取上一次迭代保存的旧的被覆盖掉的 dp[col-1]
                current = dp[col]; // 保存本次迭代即将被覆盖的旧的 dp[col]

                // text1 第 row 个字符与 text2 第 col 个字符相同: 左上角 dp 值 + 1
                if (rows.charAt(row - 1) == cols.charAt(col - 1))
                    dp[col] = leftTop + 1;
                else // 取 上边、左边 dp 值较大值
                    dp[col] = Math.max(dp[col], dp[col - 1]);
            }
        }

        return dp[cols.length()];
    }

    /**
     * 求 text1 与 text2 的最长公共子序列长度: 动态规划+滚动数组+取余优化(使用 & 1 代替 % 2)
     * 直接对所有需要映射的索引进行 & 1 运算即可。
     * 时间复杂度: O(n*m)
     * 空间复杂度: O(2*m), m 为 text2 的长度
     *
     * @param text1 序列1
     * @param text2 序列2
     * @return 最长公共子序列长度
     */
    public int longestCommonSubsequence3(String text1, String text2) {
        if (text1 == null || text2 == null) return 0;
        if (text1.length() == 0 || text2.length() == 0) return 0;

        int text1Len = text1.length();
        int text2Len = text2.length();

        // 1.定义状态；dp[i][j] 表示 text1 前 i 个字符与 text2 前 j 个字符的最大公共子序列长度
        int[][] dp = new int[2][text2.length() + 1];

        // 2.设置初始状态: dp[i][0] = dp[0][j] = 0,由于 java 默认数组元素就是 0,因此这个步骤我们可以省略

        for (int i = 1; i <= text1Len; i++) {
            for (int j = 1; j <= text2Len; j++) {
                // 3.定义状态转移方程
                if (text1.charAt(i - 1) == text2.charAt(j - 1))
                    dp[i & 1][j] = dp[(i - 1) & 1][j - 1] + 1;
                else
                    dp[i & 1][j] = Math.max(dp[(i - 1) & 1][j], dp[(i) & 1][j - 1]);
            }
        }

        return dp[text1Len & 1][text2Len];
    }

    /**
     * 求 text1 与 text2 的最长公共子序列长度: 动态规划实现
     * 时间复杂度: O(n*m)
     * 空间复杂度: O(n*m), n、m 分别为 text1、text2 的长度
     *
     * @param text1 序列1
     * @param text2 序列2
     * @return 最长公共子序列长度
     */
    public int longestCommonSubsequence2(String text1, String text2) {
        if (text1 == null || text2 == null) return 0;
        if (text1.length() == 0 || text2.length() == 0) return 0;

        int text1Len = text1.length();
        int text2Len = text2.length();

        // 1.定义状态；dp[i][j] 表示 text1 前 i 个字符与 text2 前 j 个字符的最大公共子序列长度
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];

        // 2.设置初始状态: dp[i][0] = dp[0][j] = 0,由于 java 默认数组元素就是 0,因此这个步骤我们可以省略

        for (int i = 1; i <= text1Len; i++) {
            // 由于计算 dp[i][j],二维数组的每一项都可能用到(要用到 dp[i-1][j-1]、dp[i][j-1]、dp[i-1][j])
            // 因此这里的 j 需要从 1 开始而不是从 i 开始
            for (int j = 1; j <= text2Len; j++) {
                // 公共子序列结尾元素在 text1、text2 中的下标相同的情况
                if (text1.charAt(i - 1) == text2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else // 公共子序列结尾元素在 text1、text2 中的下标不同的情况
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return dp[text1Len][text2Len];
    }

    /**
     * 求 text1 与 text2 的最长公共子序列长度: 递归实现
     * (平均)空间复杂度: O(k), k=min{n,m}
     * (最坏)时间复杂度: O(2^n),当 n == m 时
     *
     * @param text1 序列1
     * @param text2 序列2
     * @return 最长公共子序列长度
     */
    public int longestCommonSubsequence1(String text1, String text2) {
        if (text1 == null || text2 == null) return 0;
        if (text1.length() == 0 || text2.length() == 0) return 0;

        return this.lCS(text1, text1.length() - 1, text2, text2.length() - 1);
    }

    /**
     * 求 text1 的前 i 个元素与 text2 的前 j 个元素的最长公共子序列长度
     *
     * @param text1 序列1
     * @param i     text1 第 i 个元素的下标: [0, text1.length)
     * @param text2 序列2
     * @param j     text2 第 j 个元素的下标: [0, text2.length)
     * @return 最长公共子序列长度
     */
    public int lCS(String text1, int i, String text2, int j) {
        if (i == -1 || j == -1) return 0; // i 与 j 为 -1 说明其中一个子序列长度为 0,则公共子序列的长度必为 0

        if (text1.charAt(i) == text2.charAt(j))
            return lCS(text1, i - 1, text2, j - 1) + 1; // 递归深度为 O(k), k = min{n,m}
        else
            return Math.max( // 递归深度为 O(k), k = n|m
                    lCS(text1, i, text2, j - 1),
                    lCS(text1, i - 1, text2, j)
            );
    }

    public static void main(String[] args) {

    }
}
