package com.lx.第一季.树.src;

import java.util.Comparator;

/**
 * 平衡二叉搜索树
 */
public class BinaryBalancedSearchTree<E> extends BinarySearchTree<E>{
    public BinaryBalancedSearchTree() {
        this(null);
    }

    public BinaryBalancedSearchTree(Comparator<E> comparator) {
        super();
    }

    /**
     * 统一旋转的代码（目的为了塑造调衡后 a、b、c、e、e、f、g 所构成的子树）
     * a，g 对于当前 AVL 树和红黑树来说这里可以不作处理
     * //               b、d、f 必不为空，且 a < b < c < d < e < f < g
     * //                           d      (d 的父节点为 root)
     * //                         /   \
     * //                        b     f
     * //                       / \   / \
     * //                      a   c e   g
     */
    protected void rotate(
            Node<E> r, // 失衡结点
            Node<E> a, Node<E> b, Node<E> c,
            Node<E> d, Node<E> e, Node<E> f, Node<E> g
    ) {
        // 塑造 a、b、c 子树（a、c 可能为空）
        b.left = a;
        b.right = c;
        if (c != null) c.parent = b;
        if (a != null) a.parent = b;

        // 塑造 b、d、f 子树
        d.parent = r.parent;
        if (r.isRightChild()) {
            r.parent.right = d;
        } else if (r.isLeftChild()) {
            r.parent.left = d;
        } else { // 最终塑造的子树 d 为根结点
            this.root = d;
        }
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;

        // 塑造 e、f、g 子树（e、g 可能为空）
        f.left = e;
        f.right = g;
        if (e != null) e.parent = f;
        if (g != null) g.parent = f;
    }

    /**
     * 对传入的结点进行左旋转
     * //             g
     * //              \
     * //               p           ->        p
     * //              / \                   / \
     * //           child n                 g   n
     * //                                    \
     * //                                   child
     *
     * @param grand 待左旋转的结点
     */
    protected void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;

        // grand 进行左旋
        parent.left = grand;
        grand.right = child;

        // 左旋后的善后工作
        this.afterRotate(grand, parent, child);
    }

    /**
     * 对传入的结点进行右旋转
     * //            g
     * //           /
     * //          p         ->          p
     * //         / \                   / \
     * //        n child               n  g
     * //                                /
     * //                              child
     *
     * @param grand 待右旋转的结点
     */
    protected void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;

        // 进行右旋
        parent.right = grand;
        grand.left = child;

        // 右旋后的善后工作
        this.afterRotate(grand, parent, child);
    }

    /**
     * 旋转后的善后工作（配置右旋转和左旋转的图来看代码）
     *
     * @param grand  旋转前的根结点
     * @param parent 旋转后新的根结点
     * @param child  旋转后需另外保存的结点
     */
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        /* 从上到下更新 parent、grand、child 的父节点指针 */

        parent.parent = grand.parent; // 更新 parent 的父结点指针
        // 让原来 grand 的父节点指向 parent
        if (grand.isLeftChild()) grand.parent.left = parent; // grand 原来为左子结点
        else if (grand.isRightChild()) grand.parent.right = parent; // grand 原来为右子结点
        else this.root = parent; // grand 为根结点

        grand.parent = parent; // 更新 grand 的父节点指针
        if (child != null) child.parent = grand; // 更新 child 的父节点指针
    }
}
