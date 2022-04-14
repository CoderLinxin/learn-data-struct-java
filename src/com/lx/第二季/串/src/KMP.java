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
        int[] next = getNextVal(pattern); // 获取模式串的 next 数组

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
     * 求解模式串的 nextVal 数组
     *
     * @param pattern 模式串
     * @return 模式串的 next 数组
     */
    private static int[] getNextVal(String pattern) {
        char[] patternArray = new char[pattern.length()];
        int[] next = new int[patternArray.length];

        next[0] = -1; // 初始化 next[0]
        int j = 0;
        int n = next[j];
        int jMax = next.length - 1;

        while (j < jMax) {
            /* 每轮迭代求解 next[j+1]->nextVal[j+1] */
            if (n == -1 || patternArray[j] == patternArray[n]) {
                // 本轮迭代解得 next[j+1] 时,对 next[j+1] 进行调整使之变成 nextVal[j+1]
                if (patternArray[++j] == patternArray[++n]) {
                    // pattern[j+1] == pattern[next[j+1]] == pattern[n+1] 的情况
                    // 由于递推的特性,next[n] 这个值也考虑了 pattern[n]==pattern[next[n]] 的情况了.
                    next[j] = next[n];
                } else {
                    // pattern[j+1] != pattern[n+1] 则 next[j+1] 就是 nextVal[j+1]
                    next[j] = n;
                }
            } else {
                // p[j]!=p[n] 的情况: 继续向前查找公共前后缀
                n = next[n];
            }
        }

        return next;
    }


    /**
     * 求解模式串的 next 数组
     *
     * @param pattern 模式串
     * @return 模式串的 next 数组
     */
    private static int[] getNext(String pattern) {
        char[] patternArray = new char[pattern.length()];
        int[] next = new int[patternArray.length];

        next[0] = -1; // 初始化 next[0]
        int j = 0;
        int n = next[j];
        int jMax = next.length - 1;

        while (j < jMax) {
            // n==-1:
            //      next[j+1] = 0; 已找不到公共前后缀，因此置 0，相当于 next[j+1] = n+1
            //      j++; j 后移,以便进行下一轮迭代
            //      n=next[j]; 记录下一轮的 next[j], 相当于 n=0，相当于 n++
            // p[j]==p[n]:
            //      next[j+1]=n+1;
            //      j++; j 后移,以便进行下一轮迭代
            //      n++; 记录下一轮的 next[j], 相当于 n=next[j]=n+1
            if (n == -1 || patternArray[j] == patternArray[n]) {
                // 两种情况都适应此句代码
                next[++j] = ++n;
            } else {
                // p[j]!=p[n] 的情况: 继续向前查找公共前后缀
                n = next[n];
            }
        }

        return next;
    }
}
