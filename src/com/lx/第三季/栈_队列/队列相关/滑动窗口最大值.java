package com.lx.第三季.栈_队列.队列相关;

import java.util.Deque;
import java.util.LinkedList;

// https://leetcode-cn.com/problems/sliding-window-maximum/comments/
public class 滑动窗口最大值 {
    /**
     * 求大小为 k 的滑动窗口从 nums 的最左侧滑动到 nums 的最右侧，记录每当滑动窗口变化时当前窗口中元素的最大值并返回
     * 平均时间复杂度: O(n)
     * 最坏时间复杂度: O(n*k), 当 nums 为递减序列时
     * 空间复杂度: O(n-k)
     *
     * @param nums 源数组
     * @param k    滑动窗口大小
     * @return 滑动 nums.length - (k-1) 次所记录的最大值数组
     */
    public int[] maxSlidingWindow1(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1) return new int[0];
        if (k == 1) return nums;

        int[] maxes = new int[nums.length - (k - 1)];

        /* 首先初始化第一个滑动窗口 [0, k-1] 的最大值索引 */
        int maxIndex = 0;
        for (int i = 1; i <= k - 1; i++) {
            if (nums[i] > nums[maxIndex])
                maxIndex = i;
        }
        maxes[0] = nums[maxIndex];

        for (int li = 1; li < maxes.length; li++) {
            int ri = li + k - 1;

            // 先判断滑进来的新元素是否比 maxIndex 指向的元素还大(即使此时 maxIndex 已经非法
            // 但是 nums[ri] 如果比 nums[maxIndex] 还大, 由于 nums[maxIndex] 是旧滑动窗口的最大值，
            // 那么 nums[ri] 必然会比当前滑动窗口中的其他元素都大,
            // 此时 maxIndex 是否非法不影响结论(可优化一点因 maxIndex 非法而进行扫描所消耗的性能))
            if (nums[ri] >= nums[maxIndex]) {
                maxIndex = ri;
            } else if (maxIndex < li) { // maxIndex 非法，重新扫描 [li, ri] 确定最大值
                maxIndex = li;
                for (int i = li + 1; i <= ri; i++) {
                    if (nums[i] >= nums[maxIndex])
                        maxIndex = i;
                }
            }

            maxes[li] = nums[maxIndex];
        }

        return maxes;
    }

    /**
     * 求大小为 k 的滑动窗口从 nums 的最左侧滑动到 nums 的最右侧，记录每当滑动窗口变化时当前窗口中元素的最大值并返回
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param nums 源数组
     * @param k    滑动窗口大小
     * @return 滑动 nums.length - (k-1) 次所记录的最大值数组
     */
    public int[] maxSlidingWindow0(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1) return new int[0];
        if (k == 1) return nums;

        // 存储最大值的数组
        int[] maxes = new int[nums.length - (k - 1)];
        // 维护一个长度至多为 k 的双端队列
        Deque<Integer> deque = new LinkedList<>();

        for (int ri = 0; ri < nums.length; ri++) {
            int li = ri - k + 1;
            int value = nums[ri];

            // 将所有小于 nums[ri] 的元素索引从队尾一一出队
            while (!deque.isEmpty() && nums[deque.peekLast()] <= value)
                deque.pollLast();
            // 将较大值的索引从队尾入队
            deque.offerLast(ri);

            // li >= 0 表示滑动窗口形成,
            // 因为 li 受到 ri 的限制，ri < nums.length，li 必定 < nums.length，因此 li 只需判断左端点。
            if (li >= 0) {
                // 将非法的队头索引出队(由于 ri 的步长为 1，因此双端队列中至多只会在队头存在一个非法索引)
                if (deque.peekFirst() < li)
                    deque.pollFirst();

                // 取队头元素加入 maxes
                maxes[li] = nums[deque.peekFirst()];
            }
        }

        return maxes;
    }
}
