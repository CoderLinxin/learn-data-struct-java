package com.lx.第二季.并查集.src;

import com.lx.第一季.哈希表.src.HashMap;
import com.lx.第一季.映射.src.Map;

import java.util.Objects;

/**
 * 元素可以是自定义对象的并查集
 * 基于 quick union 的 rank + 路径减半 优化
 *
 * @param <V> 元素类型
 */
public class GenerationUnionFind<V> {
    // 哈希表用于存储所有结点(外界输入一个自定义对象 V 需要能找到其对应的结点)
    // 由于哈希表的特点,用户在使用并查集存储自定义对象 V 时，最好重写 V 的 equals 和 hashCode 方法
    Map<V, Node<V>> map = new HashMap<>();

    /**
     * 查找 v 所属的集合
     *
     * @param v 自定义对象 v
     * @return v 的根结点中的 value
     */
    public V find(V v) {
        Node<V> root = this.findNode(v);
        return root == null ? null : root.value;
    }

    /**
     * 查找 value 所属的集合
     *
     * @param value 自定义对象
     * @return value 的根结点
     */
    private Node<V> findNode(V value) {
        Node<V> node = this.map.get(value);
        if (node == null) return null;

        // 路径减半
        while (!Objects.equals(node.parent.value, node.value))
            node = node.parent = node.parent.parent;

        return node;
    }

    /**
     * 判断 v1、v2 是否属于同一个集合
     *
     * @param v1 自定义对象1
     * @param v2 自定义对象2
     * @return 是否属于同一个结合
     */
    public boolean isSame(V v1, V v2) {
        return Objects.equals(this.find(v1), this.find(v2));
    }

    /**
     * 合并 v1、v2 所属集合(前提是: v1、v2 已经调用了 makeSet 方法)
     *
     * @param v1 自定义对象1
     * @param v2 自定义对2
     */
    public void union(V v1, V v2) {
        Node<V> root1 = this.findNode(v1);
        Node<V> root2 = this.findNode(v2);

        if (root1 == null || root2 == null) return;
        if (Objects.equals(root1.value, root2.value)) return;

        // rank 优化
        if (root1.rank < root2.rank) {
            root1.parent = root2;
        } else if (root2.rank < root1.rank) {
            root2.parent = root1;
        } else {
            root1.parent = root2;
            root2.rank++;
        }
    }

    /**
     * 注意用户使用并查集处理自定义对象时,需要先调用此方法先将自定义对象加入集合中(相当于初始化操作，初始时单个自定义对象形成一个集合)(之后并查集才能对其进行管理)
     * 这里的操作相当于前面实现的用于处理整型数据的并查集的初始化操作
     *
     * @param value 自定义对象
     */
    public void makeSet(V value) {
        if (this.map.containsKey(value)) return;

        map.put(value, new Node<>(value));
    }

    /**
     * 结点类封装了并查集相关操作所需的数据类型(不暴露给外界)
     * 自定义对象之间的集合关系由并查集内部管理,用户无需关心,用户面向并查集的操作仅针对自定义对象 value
     *
     * @param <V> 结点中存储的数据
     */
    private static class Node<V> {
        // 数据
        V value;
        // 父结点指针(集合初始时仅有一个结点,parent 指向本身)
        Node<V> parent = this;
        // 存储树高(用于 rank 优化),集合初始时仅有一个结点,树高为 1
        int rank = 1;

        Node(V value) {
            this.value = value;
        }
    }
}
