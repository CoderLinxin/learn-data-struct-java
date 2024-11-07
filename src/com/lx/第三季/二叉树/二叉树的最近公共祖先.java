package com.lx.第三季.二叉树;

import com.lx.第三季.common.TreeNode;

// https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
public class 二叉树的最近公共祖先 {
    /**
     * 寻找以 root 为根结点的二叉树的最近公共祖先: p、q 为不同结点且同时存在(注意这个条件是该函数实现的一个重要前提)于这棵二叉树中
     * 思路(以最顶部函数调用为视角):
     * 先去 root 的左子树找最近公共祖先，找到即返回该结果
     * 先去 root 的右子树找最近公共祖先，找到即返回该结果
     * 如果 p、q 位于 root 的不同子树，那么此时 leftResult、rightResult 找到的就是 p、q，这种情况下最近公共祖先就是 root
     *
     * <p>
     * 以下情况是关于递归调用过程中关于某棵子树的讨论:
     * 1.如果 p、q 同时存在于这棵二叉树中，那么可以成功返回它们的最近公共祖先
     * 2.如果 p、q 都不存在于这棵二叉树中，返回 null
     * 3.如果只有 p 存在于这棵二叉树中，应该返回 p，供上层递归调用使用
     * 4.如果只有 q 存在于这棵二叉树中，应该返回 q，供上层递归调用使用
     *
     * 平均时间复杂度: O(n), 根据递推公式查表: T(n)=2T(n/2)+O(1)
     * 平均空间复杂度: O(h)，h 为树的高度
     *
     * @param root 根结点
     * @param p    结点 p
     * @param q    结点 q
     * @return p、q 的最近公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 对于最顶部函数调用而言:
        //  1.p == root 且 q 必然存在于 root 的子树中，所以最近公共祖先结点是 root
        //  2.q == root 且 p 必然存在于 root 的子树中，所以最近公共祖先结点是 root
        // 对于某次递归调用而言:
        //  1.p == root, 此时还可能 q 不存在于 root 的子树中，所以返回 p 供上层递归调用使用(为了让最顶层函数调用能够得到更多有用的信息)
        //  2.q == root, 此时还可能 p 不存在于 root 的子树中，所以返回 q 供上层递归调用使用
        if (root == null || p == root || q == root) return root;

        TreeNode leftResult = this.lowestCommonAncestor(root.left, p, q);
        TreeNode rightResult = this.lowestCommonAncestor(root.right, p, q);

        /* 对最顶部函数调用而言 */
        // 1.leftResult != null && rightResult != null 说明 p、q 必然存在于 root 的不同子树中，此时最近公共祖先结点为 root
        //   此时的 leftResult、rightResult 就是递归调用时层层返回上来的 p、q
        if (leftResult != null && rightResult != null) return root;

        // 2.leftResult != null && rightResult == null 说明 p、q 必然都存在于 root 的左子树中，此时最近公共祖先结点为 leftResult
        // 3.leftResult == null && rightResult != null 说明 p、q 必然都存在于 root 的右子树中，此时最近公共祖先结点为 rightResult

        // 3.leftResult == null && rightResult == null 这种情况是存在于某次函数的递归调用中的，此时应该返回 null (因为根据所给条件: p、q 必然都存在于最顶部的根结点中)
        return (leftResult != null) ? leftResult : rightResult;
    }
}
