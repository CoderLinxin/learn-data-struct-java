package com.lx.第三季.动态规划;

// https://leetcode-cn.com/problems/longest-palindromic-substring/
public class 最长回文子串 {
    /**
     * 求解字符串的(其中)一个最长回文子串: 动态规划
     * 例如输入 abacd, 输出 aba
     * 时间复杂度: O(n^2)
     * 空间复杂度: O(n^2)
     *
     * @param s 源字符串
     * @return 源字符串的其中一个最长回文子串
     */
    public String longestPalindrome2(String s) {
        if (s == null || s.length() == 0) return "";
        if (s.length() == 1) return s;

        char[] sChars = s.toCharArray();

        int begin = 0; // 最长回文子串的起始索引
        int maxLen = 1; // 最长回文子串的长度

        // 1.定义状态: dp[i][j] 代表 [s[i], s[j]] 是否是回文串([s[i], s[j]]长度为 1 的情况)
        boolean[][] dp = new boolean[sChars.length][sChars.length];

        // 从最后一行开始迭代
        for (int i = dp.length - 1; i >= 0; i--) {
            // 2.设置初始状态: 设置 '主对角线' 的 dp 值为 true, 表示 [s[i], s[i]] 是一个回文子串
            dp[i][i] = true;

            // 右端点从 s[j=i+1] 开始, 判断每一个 [s[i], s[j]] 是否是一个回文子串
            for (int j = i + 1; j < dp[0].length; j++) {
                int len = j + 1 - i; // 当前迭代子串的长度

                // s[i] 是否等于 s[j]
                boolean i_isSame_j = sChars[i] == sChars[j];

                // 3.定义状态转移方程
                // 在 s[i] == s[j] 的前提下:
                //  如果 [s[i+1], s[j-1]] 也是一个回文串那么 [s[i], s[j]] 是回文串
                //  如果子串中仅包含 s[i]、s[j] 这两个字符，那么 [s[i], s[j]] 是回文串
                dp[i][j] = i_isSame_j && (
                        // [s[i], s[j]] 长度为 2 的情况 || [s[i], s[j]] 长度 > 2 的情况
                        len == 2 || dp[i + 1][j - 1]
                );

                // [s[i], s[j]] 是回文串且长度比记录的最大回文串长度还大则更新最大回文串为 [s[i], s[j]]
                if (dp[i][j] && len > maxLen) {
                    maxLen = len;
                    begin = i;
                }
            }
        }

        return s.substring(begin, begin + maxLen); // 左闭右开
    }

    /**
     * 求解字符串的(其中)一个最长回文子串: 暴力法
     * 例如输入 abacd, 输出 aba
     * 时间复杂度: O(n^3)
     * 空间复杂度: O(1), 不算上源字符串转为字符数组所耗空间
     *
     * @param s 源字符串
     * @return 源字符串的其中一个最长回文子串
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return "";
        if (s.length() == 1) return s;

        char[] sChars = s.toCharArray();

        int begin = 0; // 最长回文子串的起始索引
        int maxLen = 1; // 最长回文子串的长度

        for (int i = 0; i < sChars.length - 1; i++) { // O(n)
            // 扫描所有以 sChars[i] 作为起始字符的子串
            for (int j = i + 1; j < sChars.length; j++) { // O(n)
                // 判断 [i, j] 区间中的字符是否构成一个回文串
                boolean result = isPalindrome(sChars, i, j + 1); // O(n)

                int newMaxLen = j + 1 - i;
                if (result && newMaxLen > maxLen) {
                    maxLen = newMaxLen;
                    begin = i;
                }
            }
        }

        return s.substring(begin, begin + maxLen); // 左闭右开
    }

    /**
     * 判断 [begin, end) 的字符是否构成一个回文串
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     *
     * @param sChars 字符数组
     * @param begin  左端点(包含)
     * @param end    右端点(不包含)
     * @return 是否是回文子串
     */
    private boolean isPalindrome(char[] sChars, int begin, int end) {
        end--;

        while (begin < end) {
            if (!(sChars[begin] == sChars[end])) {
                return false;
            } else {
                begin++;
                end--;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        new 最长回文子串().longestPalindrome("cbbd");
        new 最长回文子串().longestPalindrome2("aaaa");
    }
}
