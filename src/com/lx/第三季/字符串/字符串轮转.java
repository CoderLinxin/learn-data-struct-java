package com.lx.第三季.字符串;

// https://leetcode-cn.com/problems/string-rotation-lcci/
public class 字符串轮转 {
    /**
     * 判断字符串 s1 是否由 s2 旋转而来
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     *
     * @param s1 字符串 1
     * @param s2 字符串 2
     * @return s1 是否由 s2 旋转而来
     */
    public boolean isFlipedString(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.length() != s2.length()) return false;

        return (s1 + s1).contains(s2);
    }
}
