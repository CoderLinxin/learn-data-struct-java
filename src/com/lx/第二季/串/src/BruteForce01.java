package com.lx.第二季.串.src;

// 暴力法实现方式1
public class BruteForce01 {
    /**
     * 字符串匹配:暴力法基本实现(优化 ti 的回溯条件)
     *
     * @param text    主串
     * @param pattern 需匹配的模式串
     * @return 模式串在主串中的位置
     */
    public static int indexOf2(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        int tLen = text.length();
        int pLen = pattern.length();
        if (tLen == 0 || pLen == 0) return -1;
        if (tLen < pLen) return -1;

        char[] textArray = text.toCharArray();
        char[] patternArray = pattern.toCharArray();

        int ti = 0, pi = 0, delta = tLen - pLen;

        // ti - pi <= delta 表示本轮比较的子串长度要大于等于模式串的长度
        // 即 tLen - ti >= pLen - pi
        while (ti - pi <= delta && pi < pLen) {
            if (textArray[ti] == patternArray[pi]) {
                ti++;
                pi++;
            } else {
                ti = ti - pi + 1;
                pi = 0;
            }
        }

        return pi == pLen ? ti - pi : -1;
    }

    /**
     * 字符串匹配:暴力法基本实现
     *
     * @param text    主串
     * @param pattern 需匹配的模式串
     * @return 模式串在主串中的位置
     */
    public static int indexOf1(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        int tLen = text.length();
        int pLen = pattern.length();
        if (tLen == 0 || pLen == 0) return -1;
        if (tLen < pLen) return -1;

        // 字符串转字符数组
        char[] textArray = text.toCharArray();
        char[] patternArray = pattern.toCharArray();

        // ti、pi 分别标记当前比较的主串中某个子串、模式串中的字符
        int ti = 0, pi = 0;

        while (ti < tLen && pi < pLen) { // 判断条件仅为了保证 ti、pi 不越界
            if (textArray[ti] == patternArray[pi]) { // 匹配成功继续比较下一个字符
                ti++;
                pi++;
            } else { // 匹配失败回溯 ti、pi 指针
                // ti-pi 回到当前所匹配子串的起始字符,+1 用于进行下一个子串的匹配
                ti = ti - pi + 1;
                pi = 0;
            }
        }

        // pi == pLen 表示匹配成功
        // ti - pi 标识了匹配成功的子串的起始字符
        return pi == pLen ? ti - pi : -1;
    }
}
