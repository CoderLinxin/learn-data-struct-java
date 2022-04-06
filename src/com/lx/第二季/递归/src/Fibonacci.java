package com.lx.第二季.递归.src;

public class Fibonacci {
    // 1.朴素递归法求斐波那契数列第 n 项
    // 时间复杂度: O(2^n)
    // 空间复杂度: O(n)
    public static int fib0(int n) {
        if (n <= 2) return 1;
        return fib0(n - 1) + fib0(n - 2);
    }

    // 2.有记忆的递归法求斐波那契数列第 n 项
    // 时间复杂度: O(n)
    // 空间复杂度: O(n)
    public static int fib1(int n) {
        return fib1(n, new int[n + 1]); // 在这里调用仅用于创建一个数组
    }

    /**
     * 求解第 n 个斐波那契数列
     *
     * @param n        第 n 个
     * @param memories 用于记忆已经求得的斐波那契数列的各项值 memories[k] = Fk(第 k 个斐波那契数)
     * @return 第 n 个斐波那契数列
     */
    public static int fib1(int n, int[] memories) {
        if (memories[n] != 0) return memories[n]; // 此项斐波那契数是否已经求得

        if (n <= 2)
            memories[n] = 1;
        else
            memories[n] = fib1(n - 1) + fib1(n - 2); // 将求得的斐波那契数进行缓存

        return memories[n];
    }

    // 3.非递归法求斐波那契数列第 n 项
    // 时间复杂度: O(n)
    // 空间复杂度: O(n)
    public static int fib2(int n) {
        int[] fibonacciSequence = new int[n + 1];
        fibonacciSequence[1] = fibonacciSequence[2] = 1;

        for (int i = 3; i <= n; i++)
            fibonacciSequence[i] = fibonacciSequence[i - 1] + fibonacciSequence[i - 2];

        return fibonacciSequence[n];
    }

    // 4.非递归法求斐波那契数列第 n 项(利用滚动数组)
    // 时间复杂度: O(n)
    // 空间复杂度: O(1)
    public static int fib3(int n) {
        // 由于斐波那契数计算时仅需两个变量,因此仅需开辟2个空间大小的数组
        int[] fibonacciSequence = new int[2];
        fibonacciSequence[0] = fibonacciSequence[1] = 1;

        // 根据 0%2 = 0, 1%2 = 1,
        // 2%2=0 第三个斐波那契数可以放在索引为 0 的位置上(保留索引为 1 出的第 2 项斐波那契数)
        for (int i = 2; i < n; i++)
            fibonacciSequence[i % 2] = fibonacciSequence[(i - 1) % 2] + fibonacciSequence[(i - 2) % 2];

        return fibonacciSequence[(n - 1) % 2];
    }

    // 5.非递归法求斐波那契数列第 n 项(利用滚动数组)(优化取余操作)
    // 时间复杂度: O(n)
    // 空间复杂度: O(1)
    public static int fib4(int n) {
        int[] fibonacciSequence = new int[2];
        fibonacciSequence[0] = fibonacciSequence[1] = 1;

        // 根据 0&1= 0, 1&1=1, 2&1 = 0, 3&1=1, ...
        for (int i = 2; i < n; i++)
            fibonacciSequence[i & 1] = fibonacciSequence[(i - 1) & 1] + fibonacciSequence[(i - 2) & 1];

        return fibonacciSequence[(n - 1) & 1];
    }

    // 6.非递归法求斐波那契数列第 n 项(使用两个临时变量)
    // 时间复杂度: O(n)
    // 空间复杂度: O(1)
    public static int fib5(int n) {
        int first = 1;
        int second = 1;

        for (int i = 2; i < n; i++) {
            second = first + second;
            first = second - first;
        }

        return second;
    }

    public static void main(String[] args) {
        System.out.println(fib0(20));
        System.out.println(fib1(20));
        System.out.println(fib2(20));
        System.out.println(fib3(20));
        System.out.println(fib4(20));
        System.out.println(fib5(20));
    }
}
