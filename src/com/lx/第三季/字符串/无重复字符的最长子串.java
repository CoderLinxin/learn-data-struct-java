package com.lx.第三季.字符串;

import java.util.HashMap;
import java.util.Map;

// https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
public class 无重复字符的最长子串 {
    /**
     * 求出字符串的无重复字符的最长子串: abcdd 的最长无重复字符子串为 abcd
     * 空间复杂度: O(n)
     * 时间复杂度: O(n)
     *
     * @param s 源字符串
     * @return 最长无重复字符子串的长度
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;

        char[] sChars = s.toCharArray();
        // 记录遍历过的每个字符上一次出现的位置(之所以不搞一个 int 变量来记录的原因而使用 map 是因为这样可以起到一个缓存的作用,
        // 这样就不必在遍历第 i 个字符时再从前一个个字符扫描以确定前一个与 s[i] 相同的字符的索引)
        Map<Character, Integer> map = new HashMap<>();
        map.put(sChars[0], 0); // 初始化第一个字符的上一次出现位置
        int li = 0; // 初始化以 s[0] 字符结尾的最长无重复字符的子串的开始索引
        int max = 1; // 初始化以为 s[0] 结尾的最长无重复字符的子串的长度

        // 从索引为 1 处开始遍历
        for (int i = 1; i < sChars.length; i++) {
            Integer pi = map.get(sChars[i]); // 提取缓存的上一个 s[i] 字符出现的位置
            map.put(sChars[i], i); // 更新上一个 s[i] 字符出现的位置为当前位置以便后续的遍历

            if (pi == null) pi = -1; // 前面不存在与 s[i] 相同的字符时

            if (pi >= li)
                // 更新 li 的值为当前遍历的第 i 个字符结尾的最长无重复字符的子串的起始索引便于后续推导第 i+1 个字符...
                li = pi + 1;

            // 计算出以 s[i] 结尾的最长无重复字符的子串长度并更新 max
            max = Math.max(max, i + 1 - li);
        }

        return max;
    }
}
