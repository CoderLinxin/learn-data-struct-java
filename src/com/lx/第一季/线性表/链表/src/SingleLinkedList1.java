package com.lx.第一季.线性表.链表.src;

/**
 * 实现一个单向链表：不设置虚拟头结点
 *
 * @param <E> 链表结点数据类型
 */
public class SingleLinkedList1<E> extends AbstractList<E> {
    // 链表首结点指针
    protected Node<E> first;

    @Override
    public void clear() {
        this.size = 0;
        this.first = null;
    }

    @Override
    public E get(int index) {
        return this.findNode(index).element;
    }

    @Override
    public E set(int index, E element) {
        // 查找到目标元素
        Node<E> target = this.findNode(index);

        E oldElement = target.element;
        target.element = element;

        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        if (this.isIndexOutOfRangeForAdd(index)) this.outOfRangeTips(index);

        // 插入结点到链表
        if (index == 0) {
            // 插入的是首结点
            this.first = new Node<E>(element, this.first);
        } else {
            // 查找 index-1 位置上的结点
            Node<E> preNode = this.findNode(index - 1);
            preNode.next = new Node<E>(element, preNode.next);
        }

        this.size++;
    }

    @Override
    public E remove(int index) {
        if (this.isIndexOutOfRange(index)) this.outOfRangeTips(index);

        Node<E> removeNode = this.first;

        if (index == 0) {
            this.first = removeNode.next;
        } else {
            // 查找 index-1 位置上的结点
            Node<E> preNode = this.findNode(index - 1);
            removeNode = preNode.next;
            preNode.next = removeNode.next;
        }

        this.size--;
        return removeNode.element;
    }

    @Override
    public int indexOf(E element) {
        int targetIndex = 0;
        Node<E> traver = this.first;
        Node<E> target = null;

        if (element == null)
            // 欲查找 null 时
            for (targetIndex = 0; targetIndex < this.size; targetIndex++) {
                if (traver.element == null) {
                    target = traver;
                    break;
                }

                traver = traver.next;
                targetIndex++;
            }
        else
            // 欲查找非 null 的数据时
            for (targetIndex = 0; targetIndex < this.size; targetIndex++) {
                if (traver.element.equals(element)) {
                    target = traver;
                    break;
                }

                traver = traver.next;
            }

        targetIndex = target == null ? ELEMENT_NOT_FOUND : targetIndex;
        return targetIndex;
    }

    /**
     * 根据索引查找对应的链表结点
     *
     * @param index 欲查找结点的索引
     * @return 查找到的链表结点
     */
    protected Node<E> findNode(int index) {
        if (this.isIndexOutOfRange(index))
            this.outOfRangeTips(index);

        // 获取首结点
        Node<E> target = this.first;
        int currentIndex = 0;

        while (currentIndex++ < index) target = target.next;

        return target;
    }

    /**
     * 遍历链表
     */
    public void traver() {
        System.out.print("[ ");

        Node<E> traverNode = first;
        while (traverNode != null) {
            if (traverNode.next != null)
                System.out.print(traverNode.element + ", ");
            else
                System.out.print(traverNode.element + " ");
            traverNode = traverNode.next;
        }

        System.out.println("]");
    }

    /**
     * 链表结点类
     *
     * @param <E> 结点数据类型
     */
    protected static class Node<E> {
        E element;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }
}
