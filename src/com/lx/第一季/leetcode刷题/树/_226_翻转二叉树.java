package com.lx.第一季.leetcode刷题.树;

import java.util.LinkedList;
import java.util.Queue;

public class _226_翻转二叉树 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 使用前序遍历翻转二叉树
     *
     * @param root 根结点
     * @return 翻转后的 root
     */
    public TreeNode invertTree1(TreeNode root) {
        if (root == null) return null;

        TreeNode originalRight = root.right;
        root.right = invertTree1(root.left);
        // 因为执行完上面的语句后右子树变成了左子树，而我们还需要遍历原来的右子树
        root.left = invertTree1(originalRight);

        return root;
    }

    /**
     * 使用中序遍历翻转二叉树
     *
     * @param root 根结点
     * @return 翻转后的 root
     */
    public TreeNode invertTree2(TreeNode root) {
        if (root == null) return null;

        invertTree2(root.left); // 遍历左子树

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree2(root.left); // 遍历右子树(注意经过上面左右子树的交换，原来的右子树现在变成了左子树)

        return root;
    }

    /**
     * 使用后序遍历翻转二叉树
     *
     * @param root 根结点
     * @return 翻转后的 root
     */
    public TreeNode invertTree3(TreeNode root) {
        if (root == null) return null;

        TreeNode left = invertTree3(root.left);
        TreeNode right = invertTree3(root.right);

        root.right = left;
        root.left = right;

        return root;
    }

    /**
     * 使用层序遍历翻转二叉树
     *
     * @param root 根结点
     * @return 翻转后的根结点
     */
    public TreeNode invertTree4(TreeNode root) {
        if (root == null) return null;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll(); // 注意这里不要赋值给 root，因为最终需要返回的 root 是原来二叉树的根结点

            // 交换左右子树
            TreeNode right = node.right;
            node.right = node.left;
            node.left = right;

            // 即使左右子树交换了，但是不影响遍历结果
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }

        return root;
    }
}
