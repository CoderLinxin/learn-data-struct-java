package com.lx.第二季.动态规划.src;

// 零钱兑换问题
public class MoneyChange {
    /**
     * 递推(动态规划): 自底向上(n: 小->大)调用
     * 由前面的递归调用我们可以知道:
     * 求 dp(n) 要先求 Math.min(dp(n-1),dp(n-5),dp(n-20),dp(n-25))
     * 那可否反过来先求(n值较小的情况) Math.min(dp(n-1),dp(n-5),dp(n-20),dp(n-25)) 再根据已求得的状态(递推)得到 dp(n) 呢
     * <p>
     * 由于采用自底向上的递推式求解,由于递推总是根据已求得的最优解 an-1 来更新 an,换句话说递推算法有一个特点就是前面已经确定的解不会再更改:
     * 一旦 an-1 确定后, an-1 就是相对于数据规模 n-1 来说的最优解,求后续的 an、an+1 都不会再影响到 an-1 的值,
     * 也就是说我们一旦求得 an 的解,那么相应地也得到了 a1、a2、... an-1 的解,且全都是最优解
     * 那么根据算法执行是自底向上的,且已求得的解不会再改变,我们可以很容易地在算法执行过程中保存自底向上求得的一系列的最优解,并且还可以很容易地得到各个最优解的求解过程
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param n 待找的 n 分硬币
     * @return 找 n 分所需的最少硬币数
     */
    public static int moneyChange3(int n) {
        if (n < 1) return Integer.MAX_VALUE;

        int[] dp = new int[n + 1]; // dp[n] 表示找 n 分钱所需的最少硬币数
        int[] faces = new int[n + 1]; // faces[n] 表示凑够 n 分钱所选择的最后那枚硬币的面额(因为在计算 an->an+1 时可以很容易地记录从 an 到 an+1 的变化因子)

        // 假设零钱面额是 1、5、20、25
        for (int i = 1; i <= n; i++) {
            // 此处的每一次迭代表示求: 找 i 分钱所需的最少硬币数 dp[i],
            // 而 dp[i] 又可根据已知的:
            //      dp[i-1] (找i分钱最后选择的是1分硬币,那么其余i-1分就需要采用前面已确定的最少硬币方案: dp[i-1])
            //      dp[i-5] (找i分钱最后选择的是5分硬币,那么其余i-5分就需要采用前面的最少硬币方案: dp[i-5])
            //      dp[i-20] (找i分钱最后选择的是20分硬币,那么其余i-20分就需要采用前面已确定的最少硬币方案: dp[i-20])
            //      dp[i-25] (找i分钱最后选择的是25分硬币,,那么其余i-25分就需要采用前面已确定的最少硬币方案: dp[i-25])
            //  求最小值递推得到

            int min = dp[i - 1]; // 当 i == 1 时, 恰好利用 dp[0] = 0 用于初始化 dp[1]
            faces[i] = 1;
            if (i >= 5 && dp[i - 5] < min) { // 当前选择的是 dp[i] = dp[i - 5] + 1 的方案,即凑够 i 分硬币达成最优解最后选择的是 5 分硬币
                min = dp[i - 5]; // 当 i == 5 时, 恰好利用 dp[0] = 0 用于初始化 dp[5]
                faces[i] = 5; // 根据我们采用的递推算法,这里可以很容易地记录:凑够 i 分硬币达成最优解所最后选择的那枚硬币
            }
            if (i >= 20 && dp[i - 20] < min) { // 当前选择的是 dp[i] = dp[i - 20] + 1 的方案,即凑够 i 分硬币达成最优解最后选择的是 20 分硬币
                min = dp[i - 20]; // 当 i == 20 时, 恰好利用 dp[0] = 0 用于初始化 dp[20]
                faces[i] = 20;
            }
            if (i >= 25 && dp[i - 25] < min) { // 当前选择的是 dp[i] = dp[i - 20] + 1 的方案,即凑够 i 分硬币达成最优解最后选择的是 25 分硬币
                min = dp[i - 25]; // 当 i == 25 时, 恰好利用 dp[0] = 0 用于初始化 dp[25]
                faces[i] = 25;
            }

            dp[i] = min + 1; // 表示采用了前面的某种最优方案后这里选择了一枚对应的硬币

            print(faces, i); // 打印不同的数据规模得到的最优解 dp[i]~dp[n] 所选择硬币的过程
        }

        return dp[n];
    }


    /**
     * 打印:解出 dp[1] ~ dp[n] 的过程
     *
     * @param faces 记录了凑够 n 分钱所选择的最后那枚硬币的面额
     * @param n     总共有 n 分钱
     */
    private static void print(int[] faces, int n) {
        System.out.println();
        System.out.println("凑够 " + n + " 分钱所选择的硬币:");
        while (n > 0) {
            // 既然 faces[n] 是凑够 n 分钱所选择的最后那枚硬币的面额
            // 那么 faces[n-face[n]] 就是凑够 n-face[n] 分钱所选择的最后那枚硬币的面额,也即凑够 n 分钱所选择的倒数第二枚硬币的面额
            System.out.print(faces[n]+"分、");
            n -= faces[n];
        }
        System.out.println();
    }

    /**
     * 记忆化搜索: 自顶向下(n: 大->小)调用
     *
     * @param n 待找的 n 分硬币
     * @return 找 n 分所需的最少硬币数
     */
    public static int moneyChange2(int n) {
        int[] memories = new int[n + 1]; // 数组索引为 n,数组值缓存求得的 dp(n) 的值
        int[] faces = new int[]{1, 5, 20, 25}; // 可供选择的面额

        for (int face : faces) {
            if (face < n) memories[face] = 1; // 2.设置初始状态
            else break;
        }

        return moneyChange2(n, memories);
    }

    public static int moneyChange2(int n, int[] memories) { // 1.定义状态: dp(n) 是凑到 n 分所需的最少硬币数
        if (n < 1) return Integer.MAX_VALUE;

        if (memories[n] != 0) // 记忆化剪枝
            return memories[n];
        else // 3.列出所有的递推方程
            memories[n] = Math.min(
                    Math.min(moneyChange2(n - 25, memories), moneyChange2(n - 20, memories)),
                    Math.min(moneyChange2(n - 5, memories), moneyChange2(n - 1, memories))
            ) + 1;

        return memories[n];
    }

    /**
     * 暴力递归法: 自顶向下(n: 大->小)调用
     * 这里存在很多重复的操作(存在重叠的子问题),例如:
     * 当 n = 30时, 回调用 moneyChange1(30-20=10) -> moneyChange1(5) -> moneyChange1(4) -> moneyChange1(3) -> ...
     * 当 n = 15时, 会调用 moneyChange1(15-5=10) -> moneyChange1(5) -> moneyChange1(4) -> moneyChange1(3) -> ...
     *
     * @param n 待找的 n 分硬币
     * @return 找 n 分所需的最少硬币数
     */
    public static int moneyChange1(int n) { // 1.定义状态: dp(n) 是凑到 n 分所需的最少硬币数
        if (n < 1) return Integer.MAX_VALUE; // n < 1 直接返回不合理的值(会被下面的 min 过滤掉)

        return switch (n) {
            // 2.定义初始状态: dp(1) == dp(5) == dp(20) == dp(25) = 1
            case 1, 5, 20, 25 -> 1; // 这种写法相当于直接 return 1

            // 3.列出所有的递推方程: 假设零钱面额有 25、20、5、1
            default -> Math.min(
                    Math.min(moneyChange1(n - 25), moneyChange1(n - 20)),
                    Math.min(moneyChange1(n - 5), moneyChange1(n - 1))
            ) + 1;
        };
    }

    public static void main(String[] args) {
        System.out.println(moneyChange1(41));
        System.out.println(moneyChange2(41));
        System.out.println(moneyChange3(41));
    }
}
