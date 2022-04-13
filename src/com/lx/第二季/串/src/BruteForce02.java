package com.lx.第二季.串.src;

// 暴力法实现方式2
public class BruteForce02 {
    /**
     * 字符串匹配:暴力法实现方式2
     *
     * @param text    主串
     * @param pattern 需匹配的模式串
     * @return 模式串在主串中的位置
     */
    public static int indexOf(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        int tLen = text.length();
        int pLen = pattern.length();
        if (tLen == 0 || pLen == 0) return -1;
        if (tLen < pLen) return -1;

        char[] textArray = text.toCharArray();
        char[] patternArray = pattern.toCharArray();

        int delta = tLen - pLen;

        // 每轮 for 循环匹配一个子串，ti 标识了每轮比较的子串首字符索引
        for (int ti = 0; ti <= delta; ti++) {
            // 每轮匹配前 pi 置 0
            int pi = 0;

            while (pi < pLen) {
                // 当前字符匹配失败直接进行下一轮比较：ti++, pi=0
                if (textArray[ti + pi] != patternArray[pi]) break;

                // 当前字符匹配成功:继续匹配下一个字符
                pi++;
            }

            // 模式串匹配子串成功，直接返回 ti 即可
            if (pi == pLen) return ti;
        }

        return -1;
    }
}
