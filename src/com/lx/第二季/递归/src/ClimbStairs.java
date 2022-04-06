package com.lx.第二季.递归.src;

public class ClimbStairs {
    /**
     * 求存在 n 阶楼梯时每一步可以走一阶或两阶,共有多少种走法
     * 时间复杂度: O(2^n)
     * 空间复杂度: O(h), 会首先沿着 climbStairs1(n - 1)，climbStairs1(n - 2) 递归下去(树高为h)
     *
     * @param n 楼梯的阶数(n>=1)
     * @return 多少种走法
     */
    public static int climbStairs1(int n) {
        // 楼梯只有1阶时只有1中走法
        // 楼梯有2阶时有2种走法: 2步1阶 和 1步2阶
        // 如何确定递归基?
        // 查看递归调用时传递的参数(如下面递归调用传递了 n-2, 由于 n>0, 因此需要令传递的 n-2>0 同样成立,
        // 当 n 取 1 时, n-2 = -1,因此递归调用时可能传递-1,此时的 n 不符合 climbStairs1 对 n 的定义
        // 当 n 取 2 时, n-2 = 0,因此递归调用时可能传递0,此时的 n 不符合 climbStairs1 对 n 的定义
        // 因此需要在非法的递归参数传递前拦截掉 n==1 和 n==2 的特殊情况)
        if (n <= 2) return n;

        /* 首先明确 climbStairs 这个函数的功能就是可以求 n 阶楼梯的走法数 */

        // 第一步如果走 1 阶,则接下来一共还有 climbStairs(n-1) 个走法
        int time1 = climbStairs1(n - 1);

        // 第二步如果走 两 阶,则接下来一共还有 climbStairs(n-2) 个走法
        int time2 = climbStairs1(n - 2);

        return time1 + time2;
    }

    // 仿造上面代码使用非递归进行优化
    public static int climbStairs2(int n) {
        int time1 = 1;
        int time2 = 2;

        for (int i = 3; i <= n; i++) {
            time2 = time1 + time2;
            time1 = time2 - time1;
        }

        return time2;
    }

    public static void main(String[] args) {
        System.out.println(climbStairs1(20));
        System.out.println(climbStairs2(20));
    }
}
