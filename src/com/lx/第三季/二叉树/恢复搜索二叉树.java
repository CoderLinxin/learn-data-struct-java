package com.lx.第三季.二叉树;

import com.lx.第三季.common.TreeNode;

// https://leetcode-cn.com/problems/recover-binary-search-tree/
public class 恢复搜索二叉树 {
    private TreeNode predecessor = null; // 中序遍历过程中的前驱结点
    private TreeNode first = null; // 第一个错误结点
    private TreeNode second = null; // 第二个错误结点

    /**
     * 修复二叉树，使其符合二叉搜索树的性质
     * 平均时间复杂度: O(n)
     * 平均空间复杂度: O(h), h 为树的高度
     *
     * @param root 待修复的二叉树的根结点
     */
    public void recoverTree(TreeNode root) {
        if (root == null) return;

        this.inorder(root); // 通过中序遍历查找两个错误结点
        this.swap(this.first, this.second); // 将两个错误结点进行交换以修复二叉搜索树性质
    }

    /**
     * 对 root 为根结点的二叉树进行中序遍历，并查找二叉搜索树的两个错误结点
     *
     * @param root 根结点
     */
    private void inorder(TreeNode root) {
        if (root == null) return;

        inorder(root.left);

        if (this.predecessor != null && this.predecessor.val > root.val) {
            // 当相邻两个结点被错误交换时,中序遍历序列中存在一个逆序对:
            // first 是逆序对的较大数
            // second 是逆序对的较小数

            // 当非相邻两个结点被错误交换时,中序遍历序列中存在两个逆序对:
            // first 是第一个逆序对的较大数
            // second 是第二个逆序对的较小数

            this.second = root;

            if (this.first != null) return; // first 不为空，说明此时遇到的是第二个逆序对，因此直接退出
            this.first = this.predecessor;
        }

        this.predecessor = root; // 更新前驱指针

        inorder(root.right);
    }

    /**
     * 交换两个结点的值
     *
     * @param p 结点 p
     * @param q 结点 q
     */
    private void swap(TreeNode p, TreeNode q) {
        int tmp = p.val;
        p.val = q.val;
        q.val = tmp;
    }
}
