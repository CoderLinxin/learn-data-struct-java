package com.lx.leetcode刷题.栈;

import java.util.HashMap;
import java.util.Stack;

public class _20_括号匹配 {
    // 解决方式一
    public boolean isValid1(String s) {
        while (s.contains("()") || s.contains("{}") || s.contains("[]")) {
            s = s.replace("()", "");
            s = s.replace("{}", "");
            s = s.replace("[]", "");
        }

        return s.isEmpty();
    }

    static HashMap<Character, Character> map = new HashMap<>();

    // 解决方式二：使用栈
    public boolean isValid2(String s) {
        Stack<Character> stack = new Stack<>();

        // { 左括号: 右括号 } 的键值对
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');

        for (int i = 0; i < s.length(); i++) {
            char bracket = s.charAt(i);

            if (map.containsKey(bracket)) { // 遍历到左括号则入栈
                stack.push(bracket);
            } else { // 遍历到右括号，则进行匹配
                // 匹配右括号时，如果栈为空则说明匹配失败
                if (stack.isEmpty()) return false;

                // 右括号匹配左括号失败则匹配失败
                if (bracket != map.get(stack.pop())) return false;
            }
        }

        // 字符串遍历完后栈仍不为空则说明匹配失败
        return stack.isEmpty();
    }
}
