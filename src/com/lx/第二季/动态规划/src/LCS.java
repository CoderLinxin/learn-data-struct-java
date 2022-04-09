package com.lx.第二季.动态规划.src;

// 最长公共子串:https://www.nowcoder.com/practice/f33f5adc55f444baa0e0ca87ad8a6aac?tpId=295&sfm=discuss&channel=nowcoder
public class LCS {
    /**
     * 获取 str1 和 str2 的最长公共子串
     * 时间复杂度: O(n*m), n=str1.length(),m=str2.length()
     * 空间复杂度: O(k), k=min{m,n}
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 最长公共子串
     */
    public String lCS2(String str1, String str2) {
        if (str1 == null || str2 == null) return "";
        int str1Len = str1.length();
        int str2Len = str2.length();
        if (str1Len == 0 || str2Len == 0) return "";

        String rows = str1;
        String cols = str2;

        if (str1Len < str2Len) {
            rows = str2;
            cols = str1;
        }

        // 1.定义状态: dp[i][j] 表示在 str1 中以第 i 个字符结尾,在 str2 中以第 j 个字符结尾时的公共子串长度，
        int[] dp = new int[cols.length() + 1]; // 列数取长度较小者

        // 2.定义初始状态: dp[i][0] = dp[0][j] = 0

        int max = 0; // 记录最长公共子串长度
        int tail = 0; // 记录最长公共子串的尾指针(不包含)

        int leftTop;

        for (int row = 1; row <= rows.length(); row++) {
            int current = dp[0];

            for (int col = 1; col <= cols.length(); col++) {
                leftTop = current; // 获取旧的 dp[col-1]
                current = dp[col]; // 保留旧的 dp[col]

                // 3.定义状态转移方程
                if (rows.charAt(row - 1) == cols.charAt(col - 1)) {
                    dp[col] = leftTop + 1;
                    if (max < dp[col]) {
                        max = dp[col];
                        tail = row;
                    }
                } else
                    dp[col] = 0;
            }
        }
        System.out.println("tail: " + tail + "max: " + max);

        return rows.substring(tail - max, tail); // 返回最长公共子串(注意由于 tail 记录的是 rows 的索引,因此要用 rows 来截取子串)
    }

    /**
     * 获取 str1 和 str2 的最长公共子串
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 最长公共子串
     */
    public String lCS1(String str1, String str2) {
        if (str1 == null || str2 == null) return "";
        int str1Len = str1.length();
        int str2Len = str2.length();
        if (str1Len == 0 || str2Len == 0) return "";

        // 1.定义状态: dp[i][j] 表示在 str1 中以第 i 个字符结尾,在 str2 中以第 j 个字符结尾时的公共子串长度，
        int[][] dp = new int[str1Len + 1][str2Len + 1];

        // 2.定义初始状态: dp[i][0] = dp[0][j] = 0,数组元素默认初始化为 0，因此不用改

        int max = 0; // 记录最长公共子串长度
        int tail = 0; // 记录最长公共子串的尾指针(不包含)

        for (int i = 1; i <= str1Len; i++) {
            for (int j = 1; j <= str2Len; j++) {
                // 3.定义状态转移方程: 当 str1[i-1] == str2[j-1]，继续向上查找上一个元素 str1[i-2]、str2[j-2]
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;

                    if (max < dp[i][j]) {
                        max = dp[i][j];
                        tail = i; // 记录 i 或 j 均可
                    }
                }

                // 当 str1[i-1] != str2[j-1] 时,dp[i][j] = 0,由于数组默认值为 0,因此不用显式赋值为0
            }
        }

        return str1.substring(tail - max, tail); // 返回最长公共子串(注意由于 tail 记录的是 i,因此要用 str1 来截取子串)
    }

    public static void main(String[] args) {
        System.out.println(new LCS().lCS1("AAABA", "BABCA"));
        System.out.println(new LCS().lCS2("AAABA", "BABCA"));
    }
}
