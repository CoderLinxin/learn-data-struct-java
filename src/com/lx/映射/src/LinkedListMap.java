package com.lx.映射.src;

import com.lx.线性表.链表.src.BidirectionalLinkedList;

import java.util.Comparator;

/**
 * 使用双向链表实现的 map(映射)
 *
 * @param <K> 键(可比较)
 * @param <V> 值(不可比较)
 */
public class LinkedListMap<K, V> {
    private final BidirectionalLinkedList<Node<K, V>> list = new BidirectionalLinkedList<>();
    private final Comparator<K> comparator;

    LinkedListMap() {
        this(null);
    }

    LinkedListMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public int size() {
    }

    public boolean isEmpty() {
    }

    public void clear() {
    }

    public V put(K key, V value) {
    }

    public V get(K key) {
    }

    public V remove(K key) {
    }

    public boolean containsKey(K key) {
    }

    public boolean containsValue(V value) {
    }

    public static abstract class Visitor<K, V> {
        boolean stop;

        public abstract boolean visit(K key, V value);
    }

    private static class Node<K, V> {
        K key;
        V value;
    }
}

