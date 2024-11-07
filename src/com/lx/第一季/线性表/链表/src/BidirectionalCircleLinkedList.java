package com.lx.第一季.线性表.链表.src;

/**
 * 实现一个双向循环链表:（没有什么虚拟头结点，虚拟尾结点）
 *
 * @param <E> 链表结点数据类型
 */
public class BidirectionalCircleLinkedList<E> extends BidirectionalLinkedList<E> {
    private Node<E> current; // 约瑟夫环问题中每轮开始时指向的目标元素

    @Override
    public void add(int index, E element) {
        if (this.isIndexOutOfRangeForAdd(index)) this.outOfRangeTips(index);

        // 添加尾结点：仅需设置三个指针
        // （由于 findNode 找不到第 size 个结点，因此只能判断
        // this.size == index 而不能判断 next == null）
        // 这里相当于已经考虑了当链表为空时添加的元素实际上也属于尾结点的情况
        if (this.size == index) {
            Node<E> oldLast = this.last; // 旧的尾结点
            Node<E> newLast = new Node<E>(oldLast, element, this.first); // 新的尾结点

            // 原来链表为空的情况
            if (oldLast == null) {
                this.last = this.first = newLast;
                newLast.prev = newLast.next = newLast;
            } else { // 原来链表不为空的情况
                this.last = oldLast.next = newLast;
                this.first.prev = newLast;
            }
        } else {
            Node<E> next = this.findNode(index); // 待成为新添加结点的后继结点
            Node<E> prev = next.prev; // 待成为新添加结点的前驱结点
            Node<E> newNode = new Node<E>(prev, element, next); // 设置新添加结点的 prev，next 指针
            next.prev = newNode;
            prev.next = newNode;

            // 添加首结点（这里的链表必定不为空）
            // 当插入 0 号位置时且链表只有一个元素 A（尾结点）时，
            // 前面的 prev 和 next 都是 A , 所以 A 的 next 和 prev 在前面也都指向了新的首结点
            if (index == 0) this.first = newNode;
        }

        this.size++;
    }

    @Override
    public E remove(int index) {
        return this.remove(this.findNode(index));
    }

    public int indexOf(E element) {
        int targetIndex = 0;
        Node<E> tarver = this.first;
        Node<E> target = null;

        if (element == null)
            // 欲查找 null 时
            for (targetIndex = 0; targetIndex < this.size; targetIndex++) {
                if (tarver.element == null) {
                    target = tarver;
                    break;
                }

                tarver = tarver.next;
            }
        else
            // 欲查找非 null 的数据时
            for (targetIndex = 0; targetIndex < this.size; targetIndex++) {
                if (tarver.element.equals(element)) {
                    target = tarver;
                    break;
                }

                tarver = tarver.next;
            }

        targetIndex = target == null ? ELEMENT_NOT_FOUND : targetIndex;
        return targetIndex;
    }

    /**
     * 让 current 指针指向首结点
     */
    public void reset() {
        this.current = this.first;
    }

    /**
     * current 指针指向下一个结点（当存在下一个结点时）
     *
     * @return current 指针下一个结点的值
     */
    public E next() {
        if (this.current == null) {
            return null;
        } else {
            this.current = this.current.next;
            return this.current.element;
        }
    }

    /**
     * 根据结点元素的引用删除该结点
     *
     * @param removeNode 需要删除的结点
     * @return 被删除结点的数据
     */
    public E remove(Node<E> removeNode) {
        if (this.size == 1) { // 链表只有一个结点时进行删除时
            this.first = null;
            this.last = null;
        } else {
            Node<E> prev = removeNode.prev; // 待删除结点的前驱
            Node<E> next = removeNode.next; // 待删除结点的后继
            prev.next = next;
            next.prev = prev;

            // 删除的是首结点时，更新 first 指针
            if (removeNode == this.first) first = next;

            // 删除的是尾结点时，更新 last 指针
            if (removeNode == this.last) last = prev;
        }

        this.size--;
        return removeNode.element;
    }

    /**
     * 删除 current 指针指向的结点，并且 current 指向下一个结点
     *
     * @return 被删除的元素值
     */
    public E remove() {
        if (this.current == null) {
            return null;
        } else {
            Node<E> next = this.current.next; // 保存 current 下一个结点的引用
            E removeNode = this.remove(this.current); // 删除 current 指向的结点
            this.current = next; // current 指向下一个结点

            return removeNode;
        }
    }

    /**
     * 遍历链表
     */
    public void traver() {
        System.out.print("[ ");

        Node<E> traverNode = first;
        for (int i = 0; i < this.size; i++) {
            if (i != this.size - 1) System.out.print(traverNode + ", ");
            else System.out.print(traverNode + " ");

            traverNode = traverNode.next;
        }

        System.out.println("]");
    }
}
