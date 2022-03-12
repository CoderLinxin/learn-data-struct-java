package com.lx.树.src;

import java.util.Comparator;

/**
 * 实现一棵 AVL 树
 *
 * @param <E> 结点数据类型
 */
public class AVLTree<E> extends BinaryBalancedSearchTree<E> {
    /**
     * 创建一个 AVL 树结点
     *
     * @param element 结点数据
     * @param parent  父结点
     * @return 创建的新结点
     */
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode(element, parent);
    }

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 每次添加结点后的回调
     *
     * @param node 添加的结点（必然是叶子结点）
     */
    @Override
    protected void afterAdd(Node<E> node) {
        // 可进入下面的循环说明新添加的结点存在祖先结点

        // 新添加的结点必然是叶子结点，且叶子结点的高度已经初始设置为 1，又因为新结点不会影响其兄弟结点的高度，仅会影响祖先结点(包括父结点)的高度
        // 即插入新结点后仅需要更新高度的只有新结点的所有祖先结点(包括父结点)，因此从低到高更新祖先结点的高度时总能更新正确（因为新结点和其兄弟结点，非祖先结点的高度都是正确的）
        // 这里分为两种情况：
        // 1.向上遍历过程中没有失衡结点，因此可以将新结点到根结点路径上的所有结点的高度全部更新正确
        // g                      p
        //  \                   /   \
        //   p         ->      g     n
        //    \
        //     n                            （n 是新插入的结点）
        // 2.向上遍历过程中存在失衡结点 g，高度更新截止到 g 的子结点 p 处，此时通过对 g 进行调衡，调衡完毕后对 g 和 p 的高度进行更新，
        //  仅通过对 g 和 p 的高度值的更新，就可以让新子树 p 的所有祖先结点(原本是 g 的祖先结点)的高度值都回归正确(从而调衡完成后并不需要再向上遍历更新祖先结点的高度值)。
        //  为什么？
        //  一切都来源于平衡因子的性质，在失衡前所有结点的高度值都是正确的，假设失衡前 g 的高度为 x，g 失衡后，（由于是添加结点操作）
        //  必然导致 g 子树的高度差(平衡因子)的绝对值 + 1，也即必然导致较高子树的高度 + 1,即 g 的高度必然变成了 x + 1，进行调衡后子树的根结点变成了 p，
        //  调衡后新子树 p 的高度必然比失衡时 g 的高度小 1，即 x+1-1，即新子树 p 的高度值为 x，这就与原来失衡前 g 的高度值一致，
        //  故而基于失衡前 g 的高度值 x 所计算的 g 的所有祖先结点的高度值在调衡后仍然是正确的，即整棵树结点的高度都是正确的。
        //  故而调衡后高度值有误的结点仅有 p 和 g，故而仅需对 p 和 g 的高度进行更新。
        //  并且由于失衡前 g 的高度与调衡后 p 的高度一样，所以解决了 g 的失衡问题，祖先结点的失衡问题也不复存在，因此仅需将 g 调衡完毕整棵树也归于平衡。

        // 向上遍历新添加结点的祖先结点
        while ((node = node.parent) != null) {
            if (this.isBalanced(node)) {
                // 结点是平衡的则更新该结点高度，因为满足平衡条件会一直向上遍历，需要保证遍历到的祖先结点的平衡因子（计算结点的平衡因子依靠的是结点子树的高度差）都能计算正确
                this.updateHeight(node); // 遍历过程中自底向上更新结点的高度
            } else {
                // 找到新添加结点最近的失衡祖先结点进行调节
                this.rebalanced(node); // 该结点重新平衡后整棵树都重新归于平衡，因此直接跳出循环
//                this.rebalanced2(node);
                break;
            }
        }
    }

    /**
     * 每次删除结点后的回调
     *
     * @param node 实际被删除的结点
     * @param replacement 被删除结点的替代结点（红黑树用到）
     */
    @Override
    protected void afterRemove(Node<E> node, Node<E> replacement) {
        // 从被删除结点处开始向上遍历
        while ((node = node.parent) != null) {
            if (this.isBalanced(node)) { // 遍历到的是平衡的结点
                this.updateHeight(node); // 更新高度
            } else { // 遍历到的是失衡结点
                this.rebalanced(node); // 调衡完毕后继续向上遍历
                node = node.parent; // 调衡完毕后 node 的父节点变成了子树的根结点（跳过 node），从这里继续向上遍历
            }
        }
    }

    /**
     * 对传入的失衡结点进行平衡化处理
     *
     * @param grand 失衡结点
     */
    private void rebalanced(Node<E> grand) {
        Node<E> parent = ((AVLNode) grand).getTallerChild(); // 获取 grand 的较高子树结点得到 parent
        Node<E> node = ((AVLNode) parent).getTallerChild(); // 获取 parent 的较高子树结点得到 node

        if (parent == grand.left) { // L
            if (node == parent.left) { // LL
                //       g
                //      /
                //     p            ->         p
                //    /                       / \
                //  n                        n   g
                this.rotateRight(grand); // 对 grand 进行右旋转
            } else { // LR
                //      g                      g
                //     /                      /
                //    p          ->          n            ->         n
                //     \                   /                        / \
                //      n                 p                        p   g
                this.rotateLeft(parent); // 先对 p 进行左旋转
                this.rotateRight(grand); // 再对 g 进行右旋转
            }
        } else { // R
            if (node == parent.right) { // RR
                // g
                //  \
                //   p           ->           p
                //    \                      / \
                //     n                    g   n
                this.rotateLeft(grand); // 对 g 进行左旋转
            } else { // RL
                //        g                  g
                //         \                  \
                //          p      ->          n        ->        n
                //         /                    \                / \
                //        n                      p              g   p
                this.rotateRight(parent); // 先对 p 进行右旋
                this.rotateLeft(grand); // 再对 g 进行左旋
            }
        }
    }

    /**
     * 对传入的失衡结点进行平衡化处理（这里采用统一旋转的代码）
     *
     * @param grand 失衡结点
     */
    private void rebalanced2(Node<E> grand) {
        Node<E> parent = ((AVLNode) grand).getTallerChild(); // 获取 grand 的较高子树结点得到 parent
        Node<E> node = ((AVLNode) parent).getTallerChild(); // 获取 parent 的较高子树结点得到 node

        if (parent == grand.left) { // L
            if (node == parent.left) // LL
                this.rotate(grand, node.left, node, node.right, parent, parent.right, grand, grand.right);
            else // LR
                this.rotate(grand, parent.left, parent, node.left, node, node.right, grand, grand.right);
        } else { // R
            if (node == parent.right)  // RR
                this.rotate(grand, grand.left, grand, parent.left, parent, node.left, node, node.right);
            else // RL
                this.rotate(grand, grand.left, grand, node.left, node, node.right, parent, parent.right);
        }
    }

    @Override
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);

        // 先后更新 grand 和 parent 的高度（先更新 grand，再更新 parent
        // （因为 parent 的高度计算依靠 grand 的高度））
        // parent，grand 的高度更新完毕代表其所有祖先结点的高度都是正确的了
        this.updateHeight(grand);
        this.updateHeight(parent);
    }

    @Override
    protected void rotate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
        super.rotate(r, a, b, c, d, e, f, g);

        this.updateHeight(b); // b 的子树发生变化，更新 b 的高度
        this.updateHeight(f); // f 的子树发生变化，更新 f 的高度
        this.updateHeight(d); // d 的子树发生变化，更新 d 的高度
    }

    /**
     * 判断结点是否平衡
     *
     * @param node 需要判断的结点
     * @return 是否平衡
     */
    private boolean isBalanced(Node<E> node) {
        return Math.abs(((AVLNode) node).balancedFactor()) <= 1;
    }

    /**
     * 更新节点的高度
     *
     * @param node 需要更新高度的结点
     */
    private void updateHeight(Node<E> node) {
        ((AVLNode) node).updateHeight();
    }

    /**
     * AVL 树的结点类型
     */
    private class AVLNode extends Node<E> {
        // 结点所在子树的高度（默认高度为1，表示的是叶子结点），并且高度总是从下往上进行更新的
        // 该属性的更新策略是在添加/删除结点后调节失衡的自底向上遍历过程中进行更新
        private int height = 1;

        AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        /**
         * 更新当前结点的高度
         */
        public void updateHeight() {
            int leftHeight = this.left != null ? ((AVLNode) this.left).height : 0;
            int rightHeight = this.right != null ? ((AVLNode) this.right).height : 0;
            this.height = Math.max(leftHeight, rightHeight) + 1;
        }

        /**
         * 计算当前结点的平衡因子
         *
         * @return 平衡因子
         */
        public int balancedFactor() {
            int leftHeight = this.left != null ? ((AVLNode) this.left).height : 0;
            int rightHeight = this.right != null ? ((AVLNode) this.right).height : 0;

            return leftHeight - rightHeight; // 计算平衡因子
        }

        /**
         * 获取当前结点的较高子树结点
         *
         * @return 较高的子树结点
         */
        public Node<E> getTallerChild() {
            int leftHeight = this.left != null ? ((AVLNode) this.left).height : 0;
            int rightHeight = this.right != null ? ((AVLNode) this.right).height : 0;

            if (leftHeight > rightHeight) return this.left;
            else return this.right;
        }

        @Override
        public String toString() {
            Object str = this.element;
            Node<E> parent = this.parent;
            Object parentStr = "null";
            if (parent != null)
                parentStr = parent.element;
            return str + "_parent(" + parentStr + ")" + "height: " + this.height;
        }
    }
}
