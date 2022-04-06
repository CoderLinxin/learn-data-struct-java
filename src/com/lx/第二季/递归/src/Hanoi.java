package com.lx.第二季.递归.src;

public class Hanoi {
    /**
     * 汉诺塔问题(由于所有步骤都不重复,因此汉诺塔问题无法进行优化)
     * 时间复杂度:O(2^n), T(n) = 2T(n-1) + O(1)
     * 空间复杂度:O(n),需要递归深度为 n
     *
     *
     * @param n n个盘子(盘子编号从从上到下依次为 1~n)
     * @param a 起点柱
     * @param b 中间柱
     * @param c 终点柱
     */
    public static void hanoi(int n, String a, String b, String c) {
        // 如何确定递归基?
        // 看到下面进行递归调用传递的参数是 n-1,由于 hanoi 要求 n>=1,
        // 因此需要保证传递的 n-1 >=1 同样要成立,
        // 当 n==1 时, n-1 == 0,递归调用时可能传递n=0,不符合 hanoi 对 n 的定义
        // 所以 hanoi 内部要在递归调用前对 n==1 进行特殊处理
        if (n == 1) {
            System.out.println("将 1 号盘子从 " + a + " 搬到了 " + c + "~");
            return;
        }

        /* 首先明确 hanoi 这个函数的作用是将盘子从 a 搬到 c */

        // 要想将 n 个盘子从 a 搬到 c,就必须先将上面的 n-1 个盘子(编号从 n-1 ~ 1)从 a 搬到 b
        hanoi(n - 1, a, c, b); // 由于是从 a 搬到 b,因此需要借助 c,即 c 变成了中间柱

        // 接着将最下面的 n 号盘子直接从 a 搬到 c
        System.out.println("将第 " + n + " 号盘子从 " + a + " 搬到了 " + c + "~");

        // 最后将 b 柱上的 n-1 个盘子从 b 搬到 c
        hanoi(n - 1, b, a, c); // 由于是从 b 搬到 c,因此需要借助 a,即 a 变成了中间柱
    }

    public static void main(String[] args) {
        hanoi(2, "A", "B", "C");
    }
}
