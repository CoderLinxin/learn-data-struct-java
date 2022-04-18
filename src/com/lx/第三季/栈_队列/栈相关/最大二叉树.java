package com.lx.第三季.栈_队列.栈相关;

import com.lx.第三季.common.TreeNode;

import java.util.Arrays;
import java.util.Stack;

// https://leetcode-cn.com/problems/maximum-binary-tree/
public class 最大二叉树 {
    /**
     * 根据 nums 构造一棵最大二叉树，要求返回 nums 各个元素的父结点的索引所组成的数组
     * 无父节点则表示为 -1
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param nums 源数组
     * @return 父结点的数组
     */
    public int[] parentIndexes(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        if (nums.length == 1) return new int[]{-1};

        int[] pIndex = new int[nums.length]; // 父结点数组, pIndex[i] 表示 nums[i] 的父节点的索引
        int[] lIndex = new int[nums.length]; // 存放各个 nums[i] 左边第一个比 nums[i] 大的元素的索引
        int[] rIndex = new int[nums.length]; // 存放各个 nums[i] 右边第一个比 nums[i] 大的元素的索引

        // 初始化默认值
        Arrays.fill(pIndex, -1);
        Arrays.fill(lIndex, -1);
        Arrays.fill(rIndex, -1);

        Stack<Integer> stack = new Stack<>();

        // 求 lIndex、rIndex
        for (int i = 0; i < nums.length; i++) {
            // 栈不为空且栈顶指向的元素 A(记录的是元素的索引) < nums[i],说明 nums[i] 是 A 右边第一个大于 A 的值
            // 则出栈该元素并记录其对应 rIndex 中的值
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i])
                rIndex[stack.pop()] = i;

            // 栈不为空,且栈顶指向元素 A > nums[i],说明 A 是 nums[i] 左边第一个大于 nums[i] 的值
            // 则将 nums[i] 入栈并记录其对应 lIndex 中的值
            if (!stack.isEmpty())
                lIndex[i] = stack.peek();
            stack.push(i);
        }

        // 求 pIndex
        for (int i = 0; i < nums.length; i++) {
            int li = lIndex[i];
            int ri = rIndex[i];

            // 无父节点的情况
            if (li == -1 && ri == -1) continue;

            // 不知道是左子树还是右子树的情况:记录较小者的索引
            if (li != -1 && ri != -1)
                pIndex[i] = nums[li] < nums[ri] ? li : ri;
            else if (li == -1) // 为父结点的右子树的情况
                pIndex[i] = ri;
            else // 为父结点的左子树的情况
                pIndex[i] = li;
        }

        return pIndex;
    }

    /**
     * 根据 nums 构造一棵最大二叉树:
     * 最大二叉树的根结点值最大，其左右子树分别是 nums 数组中以根结点值索引为分界线
     * 的两个子数组所构造的最大二叉树。
     * 如:   nums: [1, 8, 4, 10, 2, 6, 4]
     * /                10
     * /              /    \
     * /             8      6
     * /            / \    / \
     * /           1  4   2   4
     *
     * @param nums 源数组
     * @return 最大二叉树的根结点
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        return this.findRoot(nums, 0, nums.length);
    }

    /**
     * 查找 nums 数组索引在 [begin, end) 范围内元素的最大值作为最大二叉树的根结点
     * 时间复杂度: O(αlogn), α 为每次调用时，[begin, end) 的区间长度
     * 空间复杂度: O(logn), 递归深度 logn
     *
     * @param nums  源数组
     * @param begin 左端点(包含)
     * @param end   右端点(不包含)
     * @return 最大二叉树的根结点
     */
    private TreeNode findRoot(int[] nums, int begin, int end) {
        if (begin == end) return null;
        if (end - begin == 1) return new TreeNode(nums[begin], null, null);

        // 从 [begin, end) 范围内挑选出最大值的索引 maxIndex
        int maxIndex = begin;
        for (int i = begin + 1; i < end; i++) {
            if (nums[i] > nums[maxIndex])
                maxIndex = i;
        }

        // 根据 nums[maxIndex] 为根结点构造最大二叉树
        TreeNode root = new TreeNode(nums[maxIndex]);
        // 根据 [begin, maxIndex) 继续构造最大二叉树
        root.left = this.findRoot(nums, begin, maxIndex);
        // 根据 [maxIndex+1, end) 继续构造最大二叉树
        root.right = this.findRoot(nums, maxIndex + 1, end);

        return root;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new 最大二叉树().parentIndexes(new int[]{3, 2, 1, 6, 0, 5})));
    }
}
