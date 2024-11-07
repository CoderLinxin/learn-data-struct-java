package com.lx.第三季.字符串;

// https://leetcode-cn.com/problems/valid-anagram/
public class 有效的字母异位词 {
    /**
     * 判断 s 和 t 是否是字母异位词(字符串中只可能出现小写字母): s 和 t 都包含相同数量的对应字符
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param s 字符串1
     * @param t 字符串2
     * @return 是否是字母异位词
     */
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null) return false;
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        if (sChars.length != tChars.length) return false;

        int[] count = new int[26]; // 统计字符串中各个字符出现的次数

        for (int i = 0; i < sChars.length; i++) {
            count[sChars[i] - 'a']++; // 统计个数
            count[tChars[i] - 'a']--; // 减回去
        }

        // 只要有一个 count 元素 < 0(说明 s、t 中对应字符的数量不同), 那 s,t 不是字母异位词,
        for (int value : count) {
            if (value < 0)
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(new 有效的字母异位词().isAnagram("rat", "car"));
    }
}
