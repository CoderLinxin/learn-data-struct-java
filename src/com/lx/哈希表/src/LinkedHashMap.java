package com.lx.哈希表.src;

/**
 * 实现一个哈希表，使得该哈希表元素遍历的顺序遵从于结点的添加顺序
 * 这里在哈希表的基础上使用双向链表将元素按照添加顺序串起来
 *
 * @param <K>
 * @param <V>
 */
public class LinkedHashMap<K, V> extends HashMap<K, V> {
    private LinkedNode<K, V> first; // 双向链表的首指针
    private LinkedNode<K, V> last; // 双向链表的尾指针

    /**
     * 清空哈希表
     */
    public void clear() {
        super.clear();
        this.first = null;
        this.last = null;
    }

    /**
     * 删除结点后更新双向链表 TODO:bug 待解决
     *
     * @param willRemoveNode 想要删除的结点
     * @param removeNode     实际被删除的结点
     */
    protected void afterRemove(Node<K, V> willRemoveNode, Node<K, V> removeNode) {
        LinkedNode<K, V> node1 = (LinkedNode<K, V>) willRemoveNode;
        LinkedNode<K, V> node2 = (LinkedNode<K, V>) removeNode;

        // removeNode 不等于 willRemoveNode 说明删除的是度为 2 的结点则需要交换 node1、node2 在双向链表中的位置
        if (willRemoveNode != removeNode) {
            LinkedNode<K, V> node1Prev = node1.prev;
            LinkedNode<K, V> node1Next = node1.next;
            LinkedNode<K, V> node2Prev = node2.prev;
            LinkedNode<K, V> node2Next = node2.next;

            // node1 是根结点
            if (node1Prev == null) this.first = node2;
            else node1Prev.next = node2;

            // node1 是尾结点
            if (node1Next == null) this.last = node2;
            else node1Next.prev = node2;

            // node2 是根结点
            if (node2Prev == null) this.first = node1;
            else node2Prev.next = node1;

            // node2 是尾结点
            if (node2Next == null) this.last = node1;
            else node2Next.prev = node1;

            node1.prev = node2Prev;
            node1.next = node2Next;
            node2.prev = node1Prev;
            node2.next = node1Next;
        }

        /* 双向链表中删除 node2 结点 */

        LinkedNode<K, V> prev = node2.prev;
        LinkedNode<K, V> next = node2.next;

        // node2 为根结点
        if (prev == null) this.first = next;
        else prev.next = next;

        // node2 为尾结点
        if (next == null) this.last = prev;
        else next.prev = prev;
    }

    /**
     * (根据结点添加的顺序)遍历哈希表
     *
     * @param visitor 遍历接口
     */
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;

        LinkedNode<K, V> traverNode = this.first;
        while (traverNode != null) {
            if (visitor.visit(traverNode.key, traverNode.value)) return;
            traverNode = traverNode.next;
        }
    }

    /**
     * 创建 LinkedNode 结点
     *
     * @param key    键
     * @param value  值
     * @param parent 父结点指针
     * @return 创建的新结点
     */
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode<K, V> newNode = new LinkedNode<K, V>(key, value, parent);

        // 双向链表初始为空则设置首尾指针
        if (this.first == null) this.first = this.last = newNode;

        if (this.last != null) { // 尾插法添加结点到双向链表中
            this.last.next = newNode;
            newNode.prev = this.last;
            this.last = newNode;
        }

        return newNode;
    }

    @Override
    public boolean containsValue(V value) {
        LinkedNode<K, V> traverNode = this.first;
        while (traverNode != null) {
            if (value == traverNode.value) return true;
            traverNode = traverNode.next;
        }

        return false;
    }

    @Override
    public boolean containsKey(K key) {
        LinkedNode<K, V> traverNode = this.first;
        while (traverNode != null) {
            if (key == traverNode.key) return true;
            traverNode = traverNode.next;
        }

        return false;
    }

    private class LinkedNode<K, V> extends Node<K, V> {
        LinkedNode<K, V> prev; // 指向上一个添加的结点
        LinkedNode<K, V> next; // 指向下一个添加的结点

        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }
}
