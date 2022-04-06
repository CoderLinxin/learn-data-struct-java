package com.lx.第二季.分治.src;

public class DivideAndConquer {
    // 分治法求 nums 的最大连续子序列和
    public int maxSubArray(int[] nums) {
        return this.maxSubArray(nums, 0, nums.length);
    }

    /**
     * 求 sums 的 [begin, end) 区间中的最大连续子序列和
     * 由 T(n) = 2*T(n/2) + O(n), (a=2,b=2,d=1)
     * 因为 log2(2) == 1 == d, 根据 master 定理可得时间复杂度
     * 时间复杂度: O(nlogn)
     * 因需要每次递归调用中都有for循环 O(n),可得递归调用了 logn 次,递归深度为 logn,每次递归所需辅助空间为 1
     * 空间复杂度: O(logn),
     *
     * @param nums  源数据
     * @param begin 区间起点(包含)
     * @param end   区间终点(不包含)
     * @return 最大连续子序列和
     */
    public int maxSubArray(int[] nums, int begin, int end) {
        if (end - begin <= 1) return nums[begin]; // 只有一个元素直接返回即可

        int mid = (end + begin) >> 1; // (end - begin)/2

        int leftMax = maxSubArray(nums, begin, mid); // 第 1 种情况:计算 [begin, mid) 的最大连续子序列和
        int rightMax = maxSubArray(nums, mid, end); // 第 2 种情况:计算 [mid, end) 的最大连续子序列和

        /* 对第 3 种情况进行特殊处理:原问题的最大连续子序列的左半部分落在 [begin, mid),右半部分落在 [mid, end) */

        // 求 [begin, mid) 区间中的最大连续子序列和(端点必须以 mid-1 为右端点)
        int lMax = Integer.MIN_VALUE;
        int lSum = 0;
        for (int i = mid - 1; i >= begin; i--)
            lMax = Math.max(lMax, lSum += nums[i]);

        // 求 [mid, end) 区间中的最大连续子序列和(端点必须以 mid 为左端点)
        int rMax = Integer.MIN_VALUE;
        int rSum = 0;
        for (int i = mid; i < end; i++)
            rMax = Math.max(rMax, rSum += nums[i]);

        // 对三者再取最大值
        return Math.max(
                lMax + rMax, // 得到第三种情况的最大连续子序列和
                Math.max(leftMax, rightMax)
        );
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(new DivideAndConquer().maxSubArray(nums));
    }
}
