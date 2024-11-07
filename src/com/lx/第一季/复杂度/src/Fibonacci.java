package com.lx.第一季.复杂度.src;

/**
 * 测试斐波那契数算法的复杂度
 */
public class Fibonacci {
    // 号码:      0 1 2 3 4 5 6 7  ...
    // 斐波那契数：0 1 1 2 3 5 8 13 ...

    /**
     * 递归法计算斐波那契数
     * 时间复杂度：O(2^N)
     * @param n: 想要输出的斐波那契数的第 n 个(n >= 0)
     * @return 返回第 n 个斐波那契数
     */
    public static long fib1(int n) {
        if (n <= 1)
            return n;

        return fib1(n - 2) + fib1(n - 1);
    }

    /**
     * for 循环计算斐波那契数
     * 时间复杂度：O(N)
     */
    public static long fib2(int n) {
        if (n <= 1)
            return n;

        long first = 0;
        long second = 1;

        for (int i = 0; i < n - 1; i++) {
            long sum = first + second;
            first = second;
            second = sum;
        }

        return second;
    }

    public static void main(String[] args) {
        System.out.println(fib1(5));
        System.out.println(fib2(50));
    }
}
