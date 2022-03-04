package com.lx.链表.src;

/**
 * 实现一个链表
 *
 * @param <E> 链表结点数据类型
 */
public class LinkedList<E> extends AbstractList<E> {
    // 链表头指针
    Node<E> first;

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(E element) {
        return 0;
    }

    /**
     * 结点类
     *
     * @param <E> 结点数据类型
     */
    private static class Node<E> {
        E element;
        Node<E> next;
    }
}
