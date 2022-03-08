package com.lx.线性表.链表.src;

/**
 * 实现一个单向循环链表：没有虚拟头结点
 *
 * @param <E> 链表结点数据类型
 */
public class SingleCircleLinkedList<E> extends SingleLinkedList1<E> {
    @Override
    public void add(int index, E element) {
        if (this.isIndexOutOfRangeForAdd(index)) this.outOfRangeTips(index);

        // 单向循环链表对比单向链表需要修改插入首结点时的操作
        if (index == 0) {
            Node<E> newNode = new Node<E>(element, this.first);

            // 链表不为空时，需要首先获取链表原先的尾结点（当 size 为 0 时新插入的结点成为尾结点）
            Node<E> last = this.size == 0 ? newNode : this.findNode(this.size - 1);
            last.next = this.first = newNode;
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

        // 单向循环链表对比单向链表只需在删除第一个结点处做额外操作
        if (index == 0) {
            if (this.size == 1) {
                // 链表仅剩一个结点时进行删除只需将 first 置 null
                this.first = null;
            } else {
                // 找到链表最后一个结点
                Node<E> last = this.findNode(this.size - 1);

                Node<E> newNext = removeNode.next;
                last.next = this.first = newNext; // 更新 first 之外还需更新最后一个结点的 next 指针
            }
        } else {
            // 查找 index-1 位置上的结点
            Node<E> preNode = this.findNode(index - 1);
            removeNode = preNode.next;
            preNode.next = removeNode.next;
        }

        this.size--;
        return removeNode.element;
    }

    /**
     * 遍历链表
     */
    public void traver() {
        System.out.print("[ ");

        Node<E> traverNode = first;
        for (int i = 0; i < this.size; i++) {
            if (i == size - 1)
                System.out.print(traverNode.element + "->" + traverNode.next.element);
            else
                System.out.print(traverNode.element + "->" + traverNode.next.element + ", ");

            traverNode = traverNode.next;
        }

        System.out.println("]");
    }
}
