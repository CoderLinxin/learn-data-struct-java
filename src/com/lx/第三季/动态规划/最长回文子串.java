package com.lx.第三季.动态规划;

// https://leetcode-cn.com/problems/longest-palindromic-substring/
public class 最长回文子串 {
    /**
     * 求解字符串的(其中)一个最长回文子串: 马拉车算法求解(理论上最优)
     * 例如输入 abacd, 输出 aba
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param s 源字符串
     * @return 源字符串的其中一个最长回文子串
     */
    public String longestPalindrome5(String s) {
        if (s == null || s.length() == 0) return "";
        if (s.length() == 1) return s;

        // 获取预处理后的字符数组
        char[] cs = this.preProcess(s);

        // 创建一个数组 m，m[i] 表示以 cs[i] 为扩展中心的最大回文子串的半径长度（不包含 cs[i]）
        int[] m = new int[cs.length];

        int maxLen = 1; // 最长回文子串的长度
        int index = 2; // 初始化可以确定的 cs 中最长回文子串的中心索引
        int c = 2; // 初始化第一个可以确定的对称中心索引
        m[2] = 1; // 初始化可以确定的最大回文子串的半径长度

        // 确定以 c 为对称中心的对称范围右端点: [l, r]
        int r = c + m[c];

        // 只从遍历 cs 数组的第四个字符开始遍历，直到倒数第三个有效字符
        for (int i = 3; i <= cs.length - 3; i++) {
            // i<r 的情况，
            if (i < r) {
                int li = c - (i - c); // 确定 i 关于 c 位置对称的 li

                // i + m[li] < r 时，m[i] 就是 m[li]
                // i + m[li] == r 时，m[i] 至少是 m[li]
                // i + m[li] > r 时，m[i] 至少是 r-i
                m[i] = i + m[li] <= r ? m[li] : r - i;
            }

            // i == r，i + m[li] > r，i + m[li] == r，三种情况都需要进行扩展(可以合并逻辑)
            // 从 i + m[i] + 1 向右，i - m[i] - 1 向左扩展
            // (i + m[li] < r 的情况进入下面的代码也会直接跳出达到与上面三种情况的逻辑复用的效果)
            m[i] = this.extension(cs, i - m[i] - 1, i + m[i] + 1) >> 1;

            if (m[i] > maxLen) {
                index = i; // 记录当前 cs 中最长回文子串的中心索引，仅需在最后进行一次计算，映射回 s 中
                maxLen = m[i]; // 更新最长回文子串长度
            }

            // 对称范围可以向右扩展时及时更新 c、r
            if (i + m[i] > r) {
                c = i;
                r = i + m[i];
            }
        }

        int start = (index - maxLen) >> 1; // 根据记录的 cs 中的对称中心索引计算出对应的 s 中的索引

        return s.substring(start, start + maxLen); // 左闭右开
    }

    /**
     * 对字符串进行预处理: 每个字符之间添加特殊字符 #，最后首部添加一个特殊字符 ^，尾部添加一个特殊字符 $
     * 0 1 2    0 1 2 3 4 5 6 7 8
     * a b c -> ^ # a # b # c # $
     *
     * @return 预处理后的字符数组
     */
    private char[] preProcess(String s) {
        int len = s.length();
        char[] sChars = s.toCharArray();

        char[] cs = new char[(len << 1) + 3];

        // 处理第一、第二、最后一个字符
        cs[0] = '^';
        cs[1] = '#';
        cs[cs.length - 1] = '$';

        //
        for (int i = 0; i < sChars.length; i++) {
            int index = (i + 1) << 1; // 计算得到 sChars 中每个字符在 cs 数组中的索引

            cs[index] = sChars[i];
            cs[index + 1] = '#'; // 每个有效字符后面添加一个 #
        }

        return cs;
    }

    /**
     * 求解字符串的(其中)一个最长回文子串: 扩展中心法优化
     * 例如输入 abacd, 输出 aba
     * 时间复杂度: O(n~n^2)
     * 空间复杂度: O(1), 不算上源字符串转为字符数组所耗空间
     *
     * @param s 源字符串
     * @return 源字符串的其中一个最长回文子串
     */
    public String longestPalindrome4(String s) {
        if (s == null || s.length() == 0) return "";
        if (s.length() == 1) return s;

        char[] sChars = s.toCharArray();

        int start = 0; // 最长回文子串的起始索引
        int maxLen = 1; // 最长回文子串的长度

        int i = 0;
        // 从第一个字符扫描到倒数第二个字符
        while (i < sChars.length - 1) {
            // 如果找到的最长回文子串的长度大于剩余未扫描字符的长度 sChars.length-i 的两倍则可以直接退出
            if (maxLen > ((sChars.length - i) << 1)) break;

            int begin = i - 1, end = i;

            // 从 i+1 位置开始寻找于 s[i] 相同字符的连续子串
            while (++end < sChars.length && sChars[i] == sChars[end]) ;

            // 计算 i 位置开始的连续相同字符的子串长度
            int length = end - i;
            // begin(包含) 向左、end(包含) 向右扩展，寻找最长回文子串并获得其长度信息
            int len = this.extension(sChars, begin, end);

            if (len > maxLen) {
                start = (i + ((length - 1) >> 1)) - ((len - 1) >> 1);
                maxLen = len;
            }

            i = end;
        }

        return s.substring(start, start + maxLen); // 左闭右开
    }

    /**
     * 求解字符串的(其中)一个最长回文子串: 扩展中心法(优于动态规划)
     * 例如输入 abacd, 输出 aba
     * 时间复杂度: O(n^2)
     * 空间复杂度: O(1), 不算上源字符串转为字符数组所耗空间
     *
     * @param s 源字符串
     * @return 源字符串的其中一个最长回文子串
     */
    public String longestPalindrome3(String s) {
        if (s == null || s.length() == 0) return "";
        if (s.length() == 1) return s;

        char[] sChars = s.toCharArray();

        int begin = 0; // 最长回文子串的起始索引
        int maxLen = 1; // 最长回文子串的长度

        // 无须扫描第一个和最后一个字符
        for (int i = sChars.length - 2; i >= 1; i--) {
            // 如果找到的最长回文子串的长度大于剩余未扫描字符的长度 i+1 的两倍则可以直接退出
            // (因为接下来寻找的回文子串至多扩展到 2(i+1) 长度)
            if (maxLen > ((i + 1) << 1)) break;

            // 从 i 位置向两端扩展以寻找最长回文子串
            int len1 = this.extension(sChars, i - 1, i + 1);
            // 从 i 右边的间隙向两端扩展以寻找最长回文子串
            int len2 = this.extension(sChars, i, i + 1);

            int len = Math.max(len1, len2);

            // 更新最长回文字符长度和起始索引
            if (len > maxLen) {
                begin = i - ((len - 1) >> 1); // 统一两种情况的写法
                maxLen = len;
            }
        }

        // 处理遗漏的第一个字符右边的间隙的扩展
        if (sChars[0] == sChars[1] && maxLen < 2)
            maxLen = 2;

        return s.substring(begin, begin + maxLen); // 左闭右开
    }

    /**
     * 从 sChar[begin](包含) 开始向左扩展，从 sChar[end](包含) 开始向右扩展, 期待能够找到一个回文串
     * end - begin <= 2
     *
     * @param sChars 字符数组
     * @param begin  左端点
     * @param end    右端点
     * @return 得到的回文串的长度
     */
    private int extension(char[] sChars, int begin, int end) {
        // 两端字符相同则继续向两端扩展
        while (begin >= 0 && end < sChars.length && sChars[begin] == sChars[end]) {
            begin--;
            end++;
        }

        // (begin, end) 的长度即找到的回文串的长度
        return end - (begin + 1);
    }

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
//        new 最长回文子串().longestPalindrome("cbbd");
//        new 最长回文子串().longestPalindrome2("aaaa");
//        new 最长回文子串().longestPalindrome4("cbbd");
        System.out.println(new 最长回文子串().longestPalindrome5("babad"));
    }
}
