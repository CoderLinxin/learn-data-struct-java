package com.lx.第三季.栈_队列.栈相关;

import java.util.Stack;

// https://leetcode-cn.com/problems/daily-temperatures/
public class 每日温度 {
    /**
     * 求每个 temperatures[i] 经过 n 天后温度会变高超过 temperatures[i]: 倒推法实现
     * 如果温度经过多少天都不会变高则记为 0
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param temperatures 温度数组
     * @return 所有日期经过 n 天后温度会变高的天数数组
     */
    public int[] dailyTemperatures2(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) return null;

        int[] values = new int[temperatures.length]; // 存储结果的数组

        for (int i = temperatures.length - 2; i >= 0; i--) {
            int j = i + 1;

            // 根据 values[j] ~ values[temperatures.length-1] 倒推 values[i]
            while (true) {
                // 第 j 天温度比第 i 天高，即第 i 天经过 j-i 天后温度会变高
                if (temperatures[i] < temperatures[j]) {
                    values[i] = j - i;
                    break;
                } else if (values[j] == 0) {
                    // 第 i 天的温度 >= 第 j 天的温度的情况:
                    // 且第 j 天后面的天数中温度都不会变高, 那么第 i 天后面的天数中温度也不会变高
                    values[i] = 0;
                    break;
                } else if (temperatures[i] == temperatures[j]) {
                    // 第 i 天的温度与第 j 天的温度相同的情况:
                    // 且第 j 天后面的天数中温度会变高, 那么第 i 天经过 j-i + values[j] 天后温度会变高
                    values[i] = values[j] + j - i;
                    break;
                }

                // 第 i 天的温度大于第 j 天温度的情况:
                // 且第 j 天后面的天数中温度会变高(但是可能仍然比第 i 天的温度低,因此继续下一轮循环)
                j += values[j];
            }
        }

        return values;
    }

    /**
     * 求每个 temperatures[i] 经过 n 天后温度会变高超过 temperatures[i]
     * 如果温度经过多少天都不会变高则记为 0
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param temperatures 温度数组
     * @return 所有日期经过 n 天后温度会变高的天数数组
     */
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) return null;

        int[] result = new int[temperatures.length]; // 存储结果的数组
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < temperatures.length; i++) {
            int topIndex = stack.peek();

            // 当栈非空且栈顶指向的元素 A 小于 temperatures[i] 时，说明此时找到 A 右边第一个大于 A 的元素
            // 将栈顶出栈并记录对应的索引差值到 result 数组中，表示经过这个差值的天数后温度会升高
            while (!stack.isEmpty() && temperatures[topIndex] < temperatures[i])
                result[topIndex] = i - stack.pop();

            // 栈为空或栈顶指向的元素 A 大于等于 temperatures[i] 时，直接将 i 进栈
            stack.push(i);
        }

        return result;
    }
}
