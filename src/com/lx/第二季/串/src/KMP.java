package com.lx.第二季.串.src;

public class KMP {
    /**
     * 字符串匹配:KMP 算法实现
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

        int ti = 0, pi = 0;
        int delta = tLen - pLen;
        int[] next = getNext(patternArray); // 获取模式串的 next 数组

        while (ti - pi <= delta && pi < pLen) {
            // 匹配成功的情况: ti++，pi++ 匹配下一个字符
            // pi < 0，说明模式串第一个字符就匹配失败，pi = -1，此时执行 pi++; ti++
            // 恰好让 pi = 0, ti 前进一步。（这就是为什么 next[0]=-1 的原因，在这里可以与匹配成功的情况共用逻辑）
            if (pi < 0 || textArray[ti] == patternArray[pi]) {
                ti++;
                pi++;
            } else {
                // 匹配失败 pi 按照 next 数组的指示仅需回溯，ti 不需要改变
                pi = next[pi];
            }
        }

        return pi == pLen ? ti - pi : -1;
    }

    /**
     * 求解模式串的 next 数组
     *
     * @param pattern 模式串
     * @return 模式串的 next 数组
     */
    private static int[] getNext(char[] pattern) {
        int[] next = new int[pattern.length];

        return next;
    }
}
