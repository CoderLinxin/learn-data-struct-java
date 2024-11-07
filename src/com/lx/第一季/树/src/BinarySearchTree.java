package com.lx.第一季.树.src;

import java.util.Comparator;

/**
 * 实现一个二叉搜索树
 *
 * @param <E> 结点存储的数据类型
 */
public class BinarySearchTree<E> extends BinaryTree<E> {
    private final Comparator<E> comparator; // 比较器对象

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        super();
        this.comparator = comparator;
    }

    /**
     * 根据传入的结点数据删除其所在的结点
     *
     * @param element 需要删除结点包含的数据
     */
    public void remove(E element) {
        this.remove(this.findNode(element));
    }

    /**
     * 根据所传入的结点在二叉搜索树中删除该结点
     *
     * @param node 需要删除的结点
     */
    public void remove(Node<E> node) {
        if (node == null) return;

        if (node.hasTowChildren()) { // 被删除的结点是度为 2 的结点
            Node<E> successor = this.successor(node); // 查找被删除结点的后继结点（度为 2，必然存在后继结点）
            node.element = successor.element; // 后继结点的数据覆盖被删除结点的数据
            node = successor; // 该引用用于后续删除该后继结点
        }

        /* 由于删除度为 2 的结点时同时需要用到删除度为 1，0 结点的逻辑，因此只需复用下述的逻辑即可 */

        Node<E> parent = node.parent; // 获取被删除结点的父结点
        Node<E> child = node.left != null ? node.left : node.right; // 获取被删除结点的子结点(用于替代被删除的结点)

        if (child != null) { // 被删除的结点是度为 1 的结点
            if (parent == null) { // 被删除的结点是度为 1 的结点且为根结点
                this.root = child;
            } else { // 被删除的结点是度为 1 的结点且非根结点
                if (node.isLeftChild()) parent.left = child;
                if (node.isRightChild()) parent.right = child;
            }

            child.parent = parent; // 更新 child.parent
            this.afterRemove(node, child); // 删除结点后的回调
        } else { // 被删除的结点是叶子结点
            if (parent == null) { // 被删除的结点是叶子结点且是根结点(说明二叉树只有这一个结点
                this.root = null; // 根结点直接置空

                this.afterRemove(node, null); // 删除结点后的回调
            } else { // 被删除的结点是叶子结点且非根结点
                if (node.isLeftChild()) parent.left = null;
                if (node.isRightChild()) parent.right = null;

                this.afterRemove(node, null); // 删除结点后的回调
            }
        }

        this.size--;
    }

    /**
     * 删除结点后的回调（子类可重写该方法在删除结点后进行一些操作）
     *
     * @param node        实际被删除的结点
     * @param replacement 被删除结点的替代结点
     */
    protected void afterRemove(Node<E> node, Node<E> replacement) {
    }

    /**
     * 插入结点到二叉搜索树中
     * (二叉搜索树才比较好根据一系列可比较数值来生成一个树，普通二叉树由于没有指定任何规则，生成的二叉树可能是随意的)
     *
     * @param element 结点的数据
     */
    public void add(E element) {
        this.elementNotNullCheck(element); // 检查结点数据是否为 null

        if (this.root == null) { // 添加的是根结点
            this.root = this.createNode(element, null);

            afterAdd(this.root); // 添加结点后的回调
        } else { // 添加的不是根结点
            Node<E> parent = this.root; // 记录遍历过程中的父结点
            Node<E> traverNode = this.root; // 用于遍历的指针
            int cmp = 0; // 记录比较结果

            while (traverNode != null) {
                parent = traverNode; // 记录遍历过程中的父节点
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

            // 找到插入的结点位置
            Node<E> newNode = this.createNode(element, parent); // 创建新结点
            if (cmp > 0) parent.right = newNode; // 插入右结点
            else parent.left = newNode; // 插入左结点

            afterAdd(newNode); // 添加结点后的回调
        }

        this.size++;
    }

    /**
     * 根据传入的数据创建一个结点（可被子类重写，创建出不同类型的结点，如 AVL 树结点，红黑树结点...）
     *
     * @param element 结点数据
     * @param parent  父节点
     * @return 创建的新结点
     */
    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<E>(element, parent);
    }

    /**
     * 添加结点后的回调（子类可重写该方法在添加结点后进行一些操作）
     *
     * @param node 添加的结点
     */
    protected void afterAdd(Node<E> node) {
    }

    /**
     * 二叉搜索树中查找是否包含 element 的结点
     *
     * @param element 所查找的结点数据
     * @return 是否存在目标结点
     */
    public boolean contains(E element) {
        return this.findNode(element) != null;
    }

    /**
     * 根据传入的结点数据找到其所在的结点
     *
     * @param element 结点数据
     * @return 查找到的结点
     */
    private Node<E> findNode(E element) {
        Node<E> traverNode = this.root;

        while (traverNode != null) {
            int cmp = this.compare(element, traverNode.element); // 进行比较后确定后续的遍历路线

            if (cmp > 0) traverNode = traverNode.right; // 元素比该结点数据大，继续查找右子树
            else if (cmp < 0) traverNode = traverNode.left; // 元素比该结点数据小，继续查找左子树
            else return traverNode; // cmp == 0 则说明数据相等，则该结点为所寻找的目标结点
        }

        return null;
    }

    /**
     * 比较结点数据时调用此方法
     *
     * @return 大于 0 表示 e1 > e2, 小于 0 表示 e1 < e2, 等于 0 表示 e1 == e2
     */
    private int compare(E e1, E e2) {
        // 结点数据在比较时可以有两种比较方式（为了更加灵活地指定比较规则）：
        // 第一种是构造二叉搜索树时传入一个 Comparator 比较器对象自定义比较规则
        // 第二种是结点的数据类通过实现 Comparable 接口的 compareTo 方法来自定义比较规则
        // 可以在构造每棵二叉搜索数实例时传入比较器对象自定义每棵二叉搜索树的比较规则(针对不同的二叉搜索树)
        // 可以让某个类实现 Comparable 接口的 compareTo 方法来自定义该类实例对象的比较规则(针对不同的类)
        // 所有基本数据类型的包装类默认都实现了 Comparable 接口
        if (this.comparator != null) // 优先使用比较器定义的规则进行比较（因为这种方式较灵活）
            return this.comparator.compare(e1, e2);

        return ((Comparable<E>) e1).compareTo(e2);
    }
}
