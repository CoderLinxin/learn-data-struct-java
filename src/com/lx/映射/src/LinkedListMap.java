package com.lx.映射.src;

import com.lx.线性表.链表.src.BidirectionalLinkedList;
import com.lx.线性表.链表.src.List;
import java.util.Comparator;

/**
 * 使用双向链表实现的 map(映射)
 *
 * @param <K> 键(可比较)
 * @param <V> 值(不可比较)
 */
public class LinkedListMap<K, V> implements Map<K, V> {
    private final BidirectionalLinkedList<Node<K, V>> list = new BidirectionalLinkedList<>();
    private final Comparator<K> comparator;

    public LinkedListMap() {
        this(null);
    }

    public LinkedListMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    /**
     * 返回映射的个数
     *
     * @return 映射的个数
     */
    public int size() {
        return this.list.size();
    }

    /**
     * 映射是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    /**
     * 清空映射表
     */
    public void clear() {
        this.list.clear();
    }

    /**
     * 根据 key 添加映射(如果原本映射的值存在则覆盖)
     *
     * @param key   键
     * @param value 值
     * @return 原来键映射的值(首次添加时返回 null)
     */
    public V put(K key, V value) {
        // 生成新映射
        Node<K, V> map = new Node<>(key, value);

        // 在映射表中查找映射是否存在
        int index = this.getIndex(key);
        V oldValue = null; // 旧的映射值

        if (index != List.ELEMENT_NOT_FOUND) { // 映射存在则进行更新
            oldValue = this.list.get(index).value; // 获取旧的 value
            this.list.set(index, map);
        } else { // 映射不存在则进行添加
            this.list.add(map);
        }

        return oldValue;
    }

    /**
     * 根据 key 获取对应映射的索引
     *
     * @param key 键
     * @return 索引
     */
    private int getIndex(K key) {
        return this.list.indexOf(new Node<>(key, null));
    }

    /**
     * 根据键获取值
     *
     * @param key 键
     * @return 键对应的值
     */
    public V get(K key) {
        int index = this.getIndex(key);

        return index != List.ELEMENT_NOT_FOUND
                ? this.list.get(index).value : null; // 映射不存在则返回 null
    }

    /**
     * 根据键删除映射
     *
     * @param key 删除映射对应的键
     * @return 删除映射中的值
     */
    public V remove(K key) {
        int index = this.getIndex(key);

        return index != List.ELEMENT_NOT_FOUND // 映射存在则删除
                ? this.list.remove(index).value : null;
    }

    /**
     * 判断映射中是否包含某个 key
     *
     * @param key 需要判断键
     * @return 是否包含 key
     */
    public boolean containsKey(K key) {
        return this.getIndex(key) != List.ELEMENT_NOT_FOUND;
    }

    /**
     * 判断映射中是否包含某个 value
     *
     * @param value 需要判断的 value
     * @return 是否包含某个 value
     */
    public boolean containsValue(V value) {
        for (int i = 0; i < this.list.size(); i++)
            if (this.list.get(i).value == value) return true;

        return false;
    }

    /**
     * 遍历映射
     *
     * @param visitor 遍历接口
     */
    public void traversal(Visitor<K, V> visitor) {
        for (int i = 0; i < this.list.size(); i++) {
            Node<K, V> map = this.list.get(i);
            visitor.visit(map.key, map.value);
        }
    }

    /**
     * 映射的类型(每条映射体现为一个结点)
     *
     * @param <K> 键
     * @param <V> 值
     */
    private static class Node<K, V> {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 自定义比较规则
         *
         * @param o 进行比较的映射结点
         * @return 是否相等
         */
        public boolean equals(Object o) {
            return this.key == ((Node<K, V>) o).key; // 键相等则认为是相同的映射
        }
    }
}

