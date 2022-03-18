package com.lx.第一季.线性表.链表.src;

/**
 * 实现一个双向链表:（没有什么虚拟头结点，虚拟尾结点）
 *
 * @param <E> 链表结点数据类型
 */
public class BidirectionalLinkedList<E> extends AbstractList<E> {
    protected Node<E> first; // 链表首结点
    protected Node<E> last; // 链表尾结点

    @Override
    public void clear() {
        // 对于 java 语言而言，只有 gc root 对象才属于强引用对象，典型的 gc root 对象就是栈中变量指向的对象
        // 因此清空双向链表只需要将 first 和 last 指针置 null 即可
        // （其他结点的引用都属于弱引用不会影响对象被 gc 回收）
        this.size = 0;
        this.first = null;
        this.last = null;
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

        // 添加尾结点：仅需设置三个指针
        // （由于 findNode 找不到第 size 个结点，因此只能判断
        // this.size == index 而不能判断 next == null）
        if (this.size == index) {
            Node<E> oldLast = this.last; // 旧的尾结点
            Node<E> newLast = new Node<E>(oldLast, element, null); // 新的尾结点

            // 原来链表为空的情况
            if (oldLast == null) this.last = this.first = newLast;
            // 原来链表不为空的情况
            else this.last = oldLast.next = newLast;
        } else {
            Node<E> next = this.findNode(index); // 待成为新添加结点的后继结点
            Node<E> prev = next.prev; // 待成为新添加结点的前驱结点
            Node<E> newNode = new Node<E>(prev, element, next); // 设置新添加结点的 prev，next 指针
            next.prev = newNode;

            // prev == null 等价于新结点添加首结点
            if (prev == null) this.first = newNode;
            else prev.next = newNode;
        }

        this.size++;
    }

    @Override
    public E remove(int index) {
        if (this.isIndexOutOfRange(index)) this.outOfRangeTips(index);

        Node<E> removeNode = this.findNode(index); // 找到待删除的结点
        Node<E> prev = removeNode.prev; // 待删除结点的前驱
        Node<E> next = removeNode.next; // 待删除结点的后继

        // prev == null 等价于删除首结点
        if (prev == null) first = next;
        else prev.next = next;

        // next == null 等价于删除尾结点
        if (next == null) last = prev;
        else next.prev = prev;

        this.size--;
        return removeNode.element;
    }

    @Override
    public int indexOf(E element) {
        int targetIndex = 0;
        Node<E> target = this.first;

        if (element == null)
            // 欲查找 null 时
            while (target != null) {
                if (target.element == null) break;

                target = target.next;
                targetIndex++;
            }
        else
            // 欲查找非 null 的数据时
            while (target != null) {
                if (target.element.equals(element)) break;

                target = target.next;
                targetIndex++;
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

        Node<E> target = null;

        // index <= size/2 则从前面遍历比较快
        // eg: 0 1 2 3 4：
        // find index = 2，
        // size(5)/2 = 2，
        // 0 -> 1 -> 2
        if (index <= this.size / 2) { // 从首结点开始遍历
            target = this.first;
            for (int currentIndex = 0; currentIndex < index; currentIndex++)
                target = target.next;
        } else { // 从尾部结点开始遍历
            target = this.last;
            for (int currentIndex = size - 1; currentIndex > index; currentIndex--)
                target = target.prev;
        }

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
                System.out.print(traverNode + ", ");
            else
                System.out.print(traverNode + " ");
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
        E element; // 结点数据
        Node<E> next; // 结点前驱指针
        Node<E> prev; // 结点后继指针

        Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            if (this.prev != null) sb.append(this.prev.element);
            else sb.append("null");

            sb.append("<-").append(this.element).append("->");

            if (this.next != null) sb.append(this.next.element);
            else sb.append("null");

            return sb.toString();
        }
    }
}
