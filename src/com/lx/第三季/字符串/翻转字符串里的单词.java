package com.lx.第三季.字符串;

public class 翻转字符串里的单词 {
    /**
     * 翻转字符串里的单词: 将字符串中单词的顺序翻转(单词内部字符不需要翻转),
     * 要求翻转后的字符串中单词之间仅含一个空格，去除字符串左右空格
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param s 源字符串
     * @return 处理后的字符串
     */
    public String reverseWords(String s) {
        if (s == null || s.length() == 0) return "";

        char[] sChars = s.toCharArray();

        // /* 1.去除字符串中的多余空格(单词间保留一个空格,去除字符串左右空格) */

        boolean space = true; // 标记遍历到的上一个字符是否是空格(默认值为 true 是为了防止遇到第一个字符就是空格的情况)
        int cur = 0; // 标记当前可覆盖位置
        int len; // 记录清除空格后字符串的有效长度

        for (int i = 0; i < sChars.length; i++) {
            char c = sChars[i];

            // 遍历的字符不是空格的情况
            if (c != ' ') {
                sChars[cur++] = c;
                space = false;
            } else if (!space) { // 遍历的字符是空格且上一个字符不是空格的情况(至多移动连续的一个空格)
                sChars[cur++] = c;
                space = true;
            } // 其余情况跳过（遇到连续的空格）
        }

        len = space ? cur - 1 : cur; // 更新字符串的有效长度(遍历的最后一个字符是否是空格对 len 的确定有影响)
        if (len < 0) return ""; // 源字符串都是空格的情况

        /* 2.对 [0, len) 范围内的字符进行逆序 */
        this.reverse(sChars, 0, len);

        /* 3.对字符串中的每个单词进行逆序 */

        int begin = 0;

        for (int i = 0; i < len; i++) {
            // 对(两个空格之间)单词进行逆序
            if (sChars[i] == ' ') { // 遇到空格说明是单词的结尾
                this.reverse(sChars, begin, i);
                begin = i + 1; // 更新 begin 的值为下一个单词的起始字符
            }
        }

        this.reverse(sChars, begin, len); // 对最后一个单词进行逆序

        return new String(sChars, 0, len);
    }

    /**
     * 对 [begin, end) 范围内的字符进行逆序
     *
     * @param chars 源字符数组
     */
    private void reverse(char[] chars, int begin, int end) {
        end--; // 移到正确位置
        char tmp;

        // 交换字符位置
        while (begin < end) {
            tmp = chars[begin];
            chars[begin++] = chars[end];
            chars[end--] = tmp;
        }
    }
}
