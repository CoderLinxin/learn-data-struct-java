package com.lx.第一季.集合.src;

import com.lx.第一季.树.src.BinaryTree;
import com.lx.第一季.树.src.RedBlackTree;

import java.util.Comparator;

/**
 * 使用红黑树实现一个集合
 *
 * @param <E> 集合元素类型
 */
public class TreeSet<E> implements Set<E> {
    private final RedBlackTree<E> tree;

    public TreeSet() {
        this(null);
    }

    public TreeSet(Comparator<E> comparator) {
        this.tree = new RedBlackTree<>(comparator);
    }

    @Override
    public int size() {
        return this.tree.size();
    }

    @Override
    public boolean isEmpty() {
        return this.tree.isEmpty();
    }

    @Override
    public void clear() {
        this.tree.clear();
    }

    @Override
    public boolean contains(E element) {
        return this.tree.contains(element);
    }

    @Override
    public void add(E element) {
        this.tree.add(element); // 实现红黑树的添加包括了去重
    }

    @Override
    public void remove(E element) {
        this.tree.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        this.tree.inorderTraversalNoRecursion(new BinaryTree.Visitor<E>() {
            @Override
            public void visit(E element) {
                visitor.visit(element);
            }
        });
    }
}
