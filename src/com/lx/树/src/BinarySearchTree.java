package com.lx.树.src;

import com.lx.树.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉搜索树
 *
 * @param <E>
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {
    private int size; // 总结点数
    private Node<E> root; // 根结点指针
    private final Comparator<E> comparator; // 比较器对象

    public int size() {
        return this.size;
    }

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {

    }

    /**
     * 插入结点到二叉搜索树中
     *
     * @param element 结点的数据
     */
    public void add(E element) {
        this.elementNotNullCheck(element); // 检查结点数据是否为 null

        if (this.root == null) { // 添加的是根结点
            this.root = new Node<E>(element, null);
        } else { // 添加的不是根结点
            Node<E> parent = this.root; // 记录遍历过程中的父结点
            Node<E> traverNode = this.root; // 用于遍历的指针
            int cmp = 0; // 记录比较结果

            while (traverNode != null) {
                parent = traverNode; // 记录父节点
                cmp = this.compare(element, traverNode.element); // 记录比较结果

                // 插入的元素数据比当前结点大
                if (cmp > 0)
                    traverNode = traverNode.right; // 继续遍历右子树
                    // 插入的元素数据比当前结点小
                else if (cmp < 0)
                    traverNode = traverNode.left; // 继续遍历左子树
                else { // 相等则覆盖
                    traverNode.element = element;
                    return;
                }
            }

            Node<E> newNode = new Node<E>(element, parent);
            if (cmp > 0) parent.right = newNode; // 插入右结点
            else parent.left = newNode; // 插入左结点
        }

        this.size++;
    }

    public void remove(E element) {

    }

//    public boolean contains(E element) {
//
//    }

    /**
     * 检查结点数据是否为 null
     *
     * @param element 结点的数据
     */
    private void elementNotNullCheck(E element) {
        if (element == null)
            throw new IllegalArgumentException("element must not be null~");
    }

    /**
     * 比较结点数据时调用此方法
     *
     * @return 大于 0 表示 e1 > e2, 小于 0 表示 e1 < e2, 等于 0 表示 e1 == e2
     */
    private int compare(E e1, E e2) {
        // 结点数据在比较时可以有两种比较方式（为了更加灵活地指定比较规则）：
        // 第一种是构造二叉搜索树时传入一个 Comparator 比较器对象自定义比较规则
        // 第二种是某个类通过实现 Comparable 接口的 compareTo 方法来自定义比较规则
        // 可以在构造每棵二叉搜索数实例时传入比较器对象自定义每棵二叉搜索树的比较规则(针对不同的二叉搜索树)
        // 可以让某个类实现 Comparable 接口的 compareTo 方法来自定义该类实例对象的比较规则(针对不同的类)
        // 所有基本数据类型的包装类默认都实现了 Comparable 接口
        if (this.comparator != null) // 优先使用比较器定义的规则进行比较（因为这种方式较灵活）
            return this.comparator.compare(e1, e2);

        return ((Comparable<E>) e1).compareTo(e2);
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

    public void levelTraversal(Visitor<E> visitor) {
        this.levelTraversal(this.root, visitor);
    }

    // 在遍历二叉树中，此接口提供外界自定义访问结点时的策略
    public static interface Visitor<E> {
        void visit(E element);
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
     * 结点数据类型
     *
     * @param <E> 数据类型
     */
    private class Node<E> {
        E element; /// 数据
        Node<E> left; // 左子结点指针
        Node<E> right; // 右子结点指针
        Node<E> parent; // 父结点指针

        Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }
    }

    @Override
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
     * @return 打印的结果字符串
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
        Object str = ((Node<E>) node).element;
        Node<E> parent = ((Node<E>) node).parent;
        Object parentStr = "null";
        if (parent != null)
            parentStr = parent.element;
        return str + "_parent(" + parentStr + ")";
    }
}
