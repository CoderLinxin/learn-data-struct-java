package com.lx.第一季.树.src;

import java.util.Comparator;

/**
 * 实现一棵红黑树
 *
 * @param <E> 结点的数据类型
 */
public class RedBlackTree<E> extends BinaryBalancedSearchTree<E> {
    public RedBlackTree() {
        this(null);
    }

    public RedBlackTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 添加结点后的调整（建议看之前的 md 文档中的图示分析代码）
     *
     * @param node 添加的结点
     */
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        if (parent == null) { // 添加的结点为根结点(包括上溢到根结点的情况)
            setBlack(node); // 根结点直接染黑即可
            return;
        }

        if (isBlack(parent)) return; // 如果父结点为黑色则返回

        Node<E> uncle = parent.sibling();
        Node<E> grand = this.setRed(parent.parent);

        if (this.isRed(uncle)) { // 上溢的情况
            this.setBlack(parent);
            this.setBlack(uncle);
            this.afterAdd(grand); // 继续向上处理上溢
        } else { // 非上溢的情况
            if (parent.isLeftChild()) { // L
                if (node.isLeftChild()) { // LL
                    this.setBlack(parent);
                } else { // LR
                    this.setBlack(node);
                    this.rotateLeft(parent);
                }

                this.rotateRight(grand);
            } else { // R
                if (node.isRightChild()) { // RR
                    this.setBlack(parent);
                } else { // RL
                    this.setBlack(node);
                    this.rotateRight(parent);
                }

                this.rotateLeft(grand);
            }
        }
    }

    /**
     * 删除结点时的回调
     *
     * @param node        实际被删除的结点
     * @param replacement 被删除结点的替代结点
     */
    protected void afterRemove(Node<E> node, Node<E> replacement) {
        if (this.isRed(node)) return; // 实际被删除结点是 RED 结点，直接返回

        /*   实际被删除结点是 BLACK 结点   */

        if (this.isRed(replacement)) { // 删除的是拥有一个 RED 子结点(替代结点)的 BLACK 结点
            setBlack(replacement); // 将替代结点染黑
        } else { // 删除的 BLACK 结点是叶子结点(处理下溢)
            Node<E> parent = node.parent; // 获取父结点
            if (parent == null) return; // 删除的 BLACK 结点是叶子结点且是根结点

            /*   删除的 BLACK 结点是叶子结点且不是根结点   */

            // 被删除结点是否曾是左子结点(左边的判断条件代表传入的 node 是真正被删除的结点，右边的条件代表 node 是因父节点向下合并时产生新的下溢而传入的父节点(并没有删除任何结点))
            boolean isLeft = parent.left == null || node.isLeftChild();
            // 获取兄弟结点(由于被删除结点的 parent 已经断了与被删除结点的连线，所以用下面的方法获取 node 的兄弟结点)
            Node<E> sibling = isLeft ? parent.right : parent.left;

            if (isLeft) { // 被删除结点在左边，兄弟结点在右边（与下面的情况对称）
                if (this.isRed(sibling)) {
                    setBlack(sibling);
                    setRed(parent);
                    this.rotateLeft(parent);
                    sibling = parent.right;
                }

                if (this.isBlack(sibling.left) && this.isBlack(sibling.right)) {
                    boolean parentBlack = this.isBlack(node.parent);
                    this.setBlack(parent);
                    this.setRed(sibling);
                    if (parentBlack) this.afterRemove(parent, null);
                } else {
                    if (this.isBlack(sibling.right)) {
                        this.rotateRight(sibling);
                        sibling = parent.right;
                    }

                    this.setColor(sibling, this.colorOf(parent));
                    this.setBlack(sibling.right);
                    this.setBlack(parent);
                    this.rotateLeft(parent);
                }
            } else { // 被删除结点在右边，兄弟结点在左边
                if (this.isRed(sibling)) { // 兄弟结点为红色:进行 B 树的等价变换
                    setBlack(sibling);
                    setRed(parent);
                    this.rotateRight(parent);

                    sibling = parent.left; // 更新兄弟结点
                }

                /*   统一处理兄弟结点为 BLACK 的情况   */

                // sibling 没有任何子结点(子结点指针都为 null)(不可能有两个黑色子结点，否则 sibling 就不是 B 树的叶子结点):不可借出
                if (this.isBlack(sibling.left) && this.isBlack(sibling.right)) { // parent 向下合并
                    // 判断原来的 parent 是否为黑色
                    boolean parentBlack = this.isBlack(node.parent);
                    this.setBlack(parent);
                    this.setRed(sibling);
                    if (parentBlack) this.afterRemove(parent, null); // 继续处理父节点的上溢
                } else { // sibling 至少有一个 RED 子结点:可以借出
                    // 黑兄弟左子结点为 null，需要进行双旋
                    if (this.isBlack(sibling.left)) {
                        this.rotateLeft(sibling); // 统一先处理左旋
                        sibling = parent.left; // 左旋后需要更新 sibling
                    }

                    /* 兄弟结点左子结点为 RED 或兄弟结点左/右子结点都是 RED 的情况 */

                    this.setColor(sibling, this.colorOf(parent)); // 中间结点继承父节点的颜色
                    this.setBlack(sibling.left);
                    this.setBlack(parent);
                    this.rotateRight(parent); // 统一处理右旋
                }
            }
        }
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RedBlackNode(element, parent);
    }

    /**
     * 将红黑树结点染成红色
     *
     * @param node 需要染色的红黑树结点
     * @return 染色后的结点
     */
    private Node<E> setRed(Node<E> node) {
        return this.setColor(node, Color.RED);
    }

    /**
     * 将红黑树结点染成黑色
     *
     * @param node 需要染色的红黑树结点
     * @return 染色后的结点
     */
    private Node<E> setBlack(Node<E> node) {
        return this.setColor(node, Color.BLACK);
    }

    /**
     * 对红黑树结点进行染色
     *
     * @param node  需要染色的红黑树结点
     * @param color 颜色
     * @return 染色后的红黑树结点
     */
    private Node<E> setColor(Node<E> node, Color color) {
        if (node != null)
            ((RedBlackNode) node).color = color;
        return node;
    }

    /**
     * 判断红黑树结点是否是黑色
     *
     * @param node 红黑树结点
     * @return 是否是黑色
     */
    private boolean isBlack(Node<E> node) {
        return this.colorOf(node) == Color.BLACK;
    }

    /**
     * 判断红黑树结点是否是红色
     *
     * @param node 红黑树结点
     * @return 是否是红色
     */
    private boolean isRed(Node<E> node) {
        return this.colorOf(node) == Color.RED;
    }

    /**
     * 判断结点的颜色
     *
     * @return 结点的颜色
     */
    private Color colorOf(Node<E> node) {
        // null 结点的颜色是 BLACK
        return node == null ? Color.BLACK : ((RedBlackNode) node).color;
    }

    /**
     * 颜色类
     */
    private enum Color {
        RED,
        BLACK,
    }

    /**
     * 红黑树结点类型
     */
    private class RedBlackNode extends Node<E> {
        private Color color = Color.RED; // 结点的颜色(新添加的结点默认为红色)

        RedBlackNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String color = this.color == Color.BLACK ? "B_" : "R_";
            return color + this.element;
        }
    }
}
