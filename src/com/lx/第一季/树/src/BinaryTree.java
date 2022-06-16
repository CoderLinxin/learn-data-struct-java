package com.lx.第一季.树.src;

import com.lx.第一季.树.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 实现一棵普通的二叉树
 *
 * @param <E> 结点数据类型
 */
public class BinaryTree<E> implements BinaryTreeInfo { // BinaryTreeInfo 用于打印二叉树的类
    protected int size; // 总结点数
    protected Node<E> root; // 根结点指针

    // 在遍历二叉树中，此接口提供外界自定义访问结点时的策略
    public static interface Visitor<E> {
        void visit(E element);
    }

    /**
     * 返回二叉树的总结点数
     *
     * @return 总结点数
     */
    public int size() {
        return this.size;
    }

    public void preorderTraversal(Visitor<E> visitor) {
        this.preorderTraversal(this.root, visitor);
    }

    public void preorderTraversalNoRecursion1(Visitor<E> visitor) {
        this.preorderTraversalNoRecursion1(this.root, visitor);
    }

    public void preorderTraversalNoRecursion2(Visitor<E> visitor) {
        this.preorderTraversalNoRecursion2(this.root, visitor);
    }

    public void inorderTraversal(Visitor<E> visitor) {
        this.inorderTraversal(this.root, visitor);
    }

    public void inorderTraversalNoRecursion(Visitor<E> visitor) {
        this.inorderTraversalNoRecursion(this.root, visitor);
    }

    public void postOrderTraversal(Visitor<E> visitor) {
        this.postOrderTraversal(this.root, visitor);
    }

    public void postOrderTraversalNoRecursion(Visitor<E> visitor) {
        this.postOrderTraversalNoRecursion(this.root, visitor);
    }

    public void postOrderTraversalNoRecursion2(Visitor<E> visitor) {
        this.postOrderTraversalNoRecursion2(this.root, visitor);
    }

    public void levelTraversal(Visitor<E> visitor) {
        this.levelTraversal(this.root, visitor);
    }

    /**
     * 判断二叉树是否为空树
     *
     * @return 是否为空树
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * 清空二叉树
     */
    public void clear() {
        this.size = 0;
        this.root = null;
    }

    /**
     * 检查结点数据是否为 null
     *
     * @param element 结点的数据
     */
    protected void elementNotNullCheck(E element) {
        if (element == null)
            throw new IllegalArgumentException("element must not be null~");
    }

    /**
     * 前序遍历（递归实现）
     *
     * @param node 根结点
     */
    private void preorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null)
            return;

        visitor.visit(node.element); // 访问根结点
        this.preorderTraversal(node.left, visitor);
        this.preorderTraversal(node.right, visitor);
    }

    /**
     * 前序遍历（栈实现方式 1）
     *
     * @param node 根结点
     */
    private void preorderTraversalNoRecursion1(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();
        stack.push(node); // 将根结点入栈

        while (!stack.isEmpty()) {
            node = stack.pop(); // 遍历右子树(第一次循环时是取出根结点)

            while (node != null) {
                visitor.visit(node.element); // 遍历根结点

                stack.push(node.right); // 右子树暂存起来
                node = node.left; // 继续遍历左子树
            }

        }
    }

    /**
     * 前序遍历（栈实现方式 2）
     *
     * @param node 根结点
     */
    private void preorderTraversalNoRecursion2(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();
        stack.push(node); // 首先将根结点入栈

        while (!stack.isEmpty()) {
            Node<E> popNode = stack.pop(); // 弹出栈顶结点
            visitor.visit(popNode.element);

            if (popNode.right != null) stack.push(popNode.right); // 右子结点先入栈后出栈
            if (popNode.left != null) stack.push(popNode.left); // 左子结点后入栈先出栈
        }
    }

    /**
     * 中序遍历（递归实现）
     *
     * @param node 根结点
     */
    private void inorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null)
            return;

        this.inorderTraversal(node.left, visitor);
        visitor.visit(node.element); // 访问根结点
        this.inorderTraversal(node.right, visitor);
    }

    /**
     * 中序遍历（栈实现）
     *
     * @param node 根结点
     */
    private void inorderTraversalNoRecursion(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();

        while (true) {
            if (node != null) { // 根结点/左子树全部入栈
                stack.push(node);
                node = node.left;
            }

            if (node == null) { // node == null 说明遍历完左子树
                if (stack.isEmpty()) return;

                node = stack.pop(); // 访问根结点
                visitor.visit(node.element);

                node = node.right; // 开始遍历右子树
            }
        }
    }

    /**
     * 后序遍历（递归实现）
     *
     * @param node 根结点
     */
    private void postOrderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null)
            return;

        this.postOrderTraversal(node.left, visitor);
        this.postOrderTraversal(node.right, visitor);
        visitor.visit(node.element); // 访问根结点
    }

    /**
     * 后序遍历（栈实现）
     *
     * @param node 根结点
     */
    private void postOrderTraversalNoRecursion(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            Node<E> topNode = stack.peek();
            if ((topNode.left == null
                    && topNode.right == null)
                    // 由于右子树根结点先入栈而后左子树根结点后入栈，
                    //（1）在某结点A的左右子树均存在的情况下，当栈顶结点为A时，A的右子树如果已访问完(且上一次访问的结点必然为右子树的根结点)，
                    //  那么A的左子树必然在右子树访问之前也已访问完（出栈），因此结点A出栈条件仅需满足 topNode.right == node
                    //（2）在某结点A仅存在左子树的情况下，当栈顶结点为A时，A出栈条件需要满足 topNode.left == node
                    //（3）在某结点A仅存在右子树的情况下，当栈顶结点为A时，A出栈条件需要满足 topNode.right == node
                    || (topNode.left == node
                    || topNode.right == node)) {
                // 记录上一次访问的结点，由于第一次访问(出栈)的必然是叶子结点
                // 注意不能声明一个初始值为 null 的 preNode 变量记录第一次访问的栈顶结点，
                // 否则可能提前满足 topNode.left == node || topNode.right == node 的条件
                // 使得非叶子结点可能提前出栈
                node = stack.pop();
                visitor.visit(node.element);
            } else {
                if (topNode.right != null) stack.push(topNode.right);
                if (topNode.left != null) stack.push(topNode.left);
            }
        }
    }

    /**
     * 后序遍历（栈实现2）
     * 主要思路:
     *  沿着根结点的左子树一直往下走，假设工作指针为node，当node.left为null时，存在两种情况:
     *      node.right不为空且未访问过node的右子树:
     *          这种情况表明node是首次从祖先结点一路往左走下来的，为了保证左右根的访问顺序，此时需要往右子树的路径走;
     *      node.right不为空且已访问过node的右子树:
     *          这种情况表明node是在访问了node的右子树之后返回的，此时为了确保能够识别这种情况，因此设置一个visited指针记录上次访问的结点，
     *          记录上一次出栈的元素(必然是node的右子树的根结点)，当node.right==visited，将node出栈即可;
     *      node.right为空:
     *          此时直接访问node即可
     *
     * @param node 根结点
     */
    private void postOrderTraversalNoRecursion2(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;

        Stack<Node<E>> stack = new Stack<>();
        stack.push(node);
        node = node.left;
        Node<E> visited = null; // 记录上一次访问的结点

        while (!stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left; // 往左走
            } else { // 判断当前栈顶结点的右子树遍历情况
                node = stack.peek();

                // 栈顶结点右子树不为空且上一次访问的结点不是栈顶结点的右子树
                if (node.right != null && node.right != visited) {
                    node = node.right; // 往右走
                } else {
                    // 栈顶结点的右子树为空或已经被访问过，则栈顶结点可以出栈并访问
                    visited = stack.pop();
                    visitor.visit(visited.element);
                    // 当前栈顶元素A出栈后，最新的栈顶元素B必然为A的父结点，置node为null，便于下次遍历仅判断B的右子树遍历情况。
                    // (1)当前栈顶元素A为B的左孩子(B的右子树必然没访问过):
                    //  B的右子树存在，下次遍历时考虑往B的右子树走
                    // (2)当前栈顶元素A为B的右孩子, 则B的左子树必然在A之前已访问过
                    //  下次遍历时考虑B直接出栈并访问
                    node = null;
                }
            }
        }
    }

    /**
     * 层序遍历（队列实现）
     */
    private void levelTraversal(Node<E> root, Visitor<E> visitor) {
        if (root == null)
            return;
        else {
            Queue<Node<E>> queue = new LinkedList<>();
            queue.offer(root); // 首先将根结点入队

            while (!queue.isEmpty()) {
                Node<E> node = queue.poll(); // 出队一个结点
                visitor.visit(node.element);

                if (node.left != null) queue.offer(node.left); // 左子结点入队(出队比早于右子结点)
                if (node.right != null) queue.offer(node.right); // 右子结点入队(出队比晚于左子结点)
            }
        }
    }

    /**
     * 获取 node 结点的后继结点
     *
     * @param node 需要获取后继结点的结点
     * @return node 后继结点
     */
    protected Node<E> successor(Node<E> node) {
        if (node == null) return null;

        Node<E> right = node.right;
        if (right != null) { // node 的右子树不为空，那么 node 的后继必定位于右子树中
            while (right.left != null) right = right.left; // 从 node 的右子树根结点一直向下查找左子树
            return right;
        }

        Node<E> parent = node.parent;
        while (parent != null) { // node 的父节点不为空，那么 node 的后继结点就可能位于祖先结点之中
            node = parent;
            parent = parent.parent; // 继续向上查找

            if (parent.left == node) break; // node 位于 parent 的左子树中，则说明找到 node 的后继，后继结点为 parent
        }

        return parent;
    }

    /**
     * 获取某个结点的前驱结点
     *
     * @param node 需要获取前驱结点的结点
     * @return node 的前驱结点
     */
    protected Node<E> predecessor(Node<E> node) {
        if (node == null) return null;

        Node<E> left = node.left;
        if (left != null) { // node 的左子树不为空，那么 node 的前驱必定位于左子树中
            while (left.right != null) left = left.right; // 从 node 的左子树根结点一直向下查找右子树
            return left;
        }

        Node<E> parent = node.parent;
        while (parent != null) { // node 的父节点不为空，那么 node 的前继结点就可能位于祖先结点之中
            node = parent;
            parent = parent.parent; // 继续向上查找

            if (parent.right == node) break; // node 位于 parent 的右子树中，则说明找到 node 的前继，前继结点为 parent
        }

        return parent;
    }

    /**
     * 结点数据类型
     *
     * @param <E> 数据类型
     */
    protected static class Node<E> {
        E element; /// 数据
        Node<E> left; // 左子结点指针
        Node<E> right; // 右子结点指针
        Node<E> parent; // 父结点指针

        Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        /**
         * 判断是否是叶子结点
         *
         * @return 是否是叶子结点
         */
        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        /**
         * 判断结点的度是否为 2
         *
         * @return 度是否为 2
         */
        public boolean hasTowChildren() {
            return this.left != null && this.right != null;
        }

        /**
         * 判断当前结点是否是其父节点的左子树
         *
         * @return 是否是左子树
         */
        public boolean isLeftChild() {
            return this.parent != null && this.parent.left == this;
        }

        /**
         * 判断当前结点是否是其父节点的右子树
         *
         * @return 是否是右子树
         */
        public boolean isRightChild() {
            return this.parent != null && this.parent.right == this;
        }

        /**
         * 返回当前结点的兄弟结点
         *
         * @return 兄弟结点
         */
        public Node<E> sibling() {
            if (this.isRightChild()) return this.parent.left;
            if (this.isLeftChild()) return this.parent.right;

            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.toString(this.root, sb, "");
        return sb.toString();
    }

    /**
     * 使用前序遍历树状打印二叉树
     *
     * @param node   根结点
     * @param sb     结果字符串
     * @param prefix 每级结点打印时的前缀
     */
    private void toString(Node<E> node, StringBuilder sb, String prefix) {
        if (node == null) return;

        sb.append(prefix).append(node.element).append("\n");
        this.toString(node.left, sb, prefix + "L---");
        this.toString(node.right, sb, prefix + "R---");
    }

    public int getHeightWithRecursion() {
        return this.getHeightWithRecursion(this.root);
    }

    /**
     * 递归求解树的高度
     *
     * @param node 根结点
     * @return 疏的高度
     */
    private int getHeightWithRecursion(Node<E> node) {
        if (node == null) return 0;

        // 根结点的高度等于左子树和右子树之间的较大高度者 + 1
        return Math.max(getHeightWithRecursion(node.left), getHeightWithRecursion(node.right)) + 1;
    }

    public int getHeightWithLevelTraversal() {
        return this.getHeightWithLevelTraversal(this.root);
    }

    /**
     * 层次遍历求解树的高度
     * 思路：树的每一层的结点数可以由刚开始遍历该层时的队列长度得到，因为此时将要遍历的该层结点全部都已入队
     * 每次出队一个元素则代表本层待遍历元素数减一，
     * 当出队次数达到记录的结点数则表示遍历完树的一层
     *
     * @param root 根结点
     * @return 树的高度
     */
    private int getHeightWithLevelTraversal(Node<E> root) {
        if (root == null)
            return 0;
        else {
            Queue<Node<E>> queue = new LinkedList<>();
            queue.offer(root); // 首先将根结点入队

            int height = 0; // 记录树的高度
            int levelSize = queue.size(); // 记录树的每一层的结点数

            while (!queue.isEmpty()) {
                Node<E> node = queue.poll(); // 出队一个结点
                levelSize--; // 出队一个结点则代表本层待遍历结点树减一

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);

                if (levelSize == 0) { // 遍历完树的一层
                    levelSize = queue.size(); // 更新下一层的数量
                    height++; // 更新高度
                }
            }

            return height;
        }
    }

    public boolean isComplete() {
        return this.isComplete2(this.root);
    }

    /**
     * 判断一棵树是否是完全二叉树(使用层序遍历)
     *
     * @param node 根结点
     * @return 是否是完全二叉树
     */
    private boolean isComplete(Node<E> node) {
        if (node == null) return false;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        boolean isLeaf = false; // 标记后续访问的是否必须都是叶子结点

        while (!queue.isEmpty()) {
            node = queue.poll();

            // isLeaf 为 true 后访问到的结点只要有一个不是叶子结点则说明不是完全二叉树
            if (isLeaf && !node.isLeaf()) return false;

            if (node.hasTowChildren()) { // 访问的结点的度为 2，则继续层序遍历
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            } else if (node.left == null && node.right != null) // 左结点为空，右结点不为空违背了完全二叉树的原则
                return false;
            else { // 剩下的情况是 left != null && right == null || node.isLeaf()，后续遍历的结点必须都为叶子结点才符合完全二叉树的定义
                isLeaf = true;
                if (node.left != null) queue.offer(node.left); // 注意不要漏了将这个结点入队
            }
        }

        return true;
    }

    /**
     * 判断一棵树是否是完全二叉树:第二种写法(推荐)
     *
     * @param node 根结点
     * @return 是否是完全二叉树
     */
    private boolean isComplete2(Node<E> node) {
        if (node == null) return false;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        boolean isLeaf = false; // 标记后续访问的是否必须都是叶子结点

        while (!queue.isEmpty()) {
            node = queue.poll();

            if (isLeaf && !node.isLeaf()) return false;

            if (node.left != null) { // 确保所有结点都能入队
                queue.offer(node.left);
            } else if (node.right != null) { // node.left == null & node.right != null 的情况
                return false;
            }

            if (node.right != null) { // 确保所有结点都能入队
                queue.offer(node.right);
            } else {
                // node.right == null && node.left == null
                // node.right == null && node.left != null
                isLeaf = true;
            }
        }

        return true;
    }

    /*       明杰老师打印工具需实现的接口        */

    @Override
    public Object root() {
        return this.root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node) node).element;
    }
}
