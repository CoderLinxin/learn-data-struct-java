package com.lx.第三季.字符串;

import com.lx.第三季.common.TreeNode;

// https://leetcode-cn.com/problems/subtree-of-another-tree/
public class 另一棵树的子树 {
    /**
     * 判断树 t 是否是树 s 的子树（树 t 必须包含树 s 中某棵子树的根结点和所有后代结点）
     * 时间复杂度: O(N)
     * 空间复杂度: O(N)
     *
     * @param root    树 s 的根结点
     * @param subRoot 树 t 的根结点
     * @return t 是否是 s 的子树
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null || subRoot == null) return false;

        // 判断 t 的后序遍历序列是否是 s 的后续遍历序列的子串
        return this.postSerialize(root)
                .contains(this.postSerialize(subRoot));
    }

    private String postSerialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        this.postSerialize(root, sb);
        return sb.toString();
    }

    /**
     * 生成树的扩展二叉树的后序遍历序列:每个结点值右边使用符号 ! 隔开，空结点值使用 # 表示
     *
     * @param root 树的根结点
     * @param sb   用于拼接序列化的字符串
     */
    private void postSerialize(TreeNode root, StringBuilder sb) {
        if (root == null) { // 处理空结点
            sb.append("#").append("!");
            return;
        }

        postSerialize(root.left, sb);
        postSerialize(root.right, sb);

        sb.append(root.val).append("!");
    }
}
