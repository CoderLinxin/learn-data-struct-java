package com.lx.第一季.集合.src;

import com.lx.第一季.映射.src.LinkedListMap;

import java.util.Comparator;

/**
 * 使用映射实现集合
 *
 * @param <E> 集合元素类型
 */
public class MapSet<E> implements Set<E> {
    // 集合元素对应 map 的各个 key (因为 map 的 key 是去重的)
    private final LinkedListMap<E, Object> map;

    Comparator<E> comparator;

    public MapSet() {
        this(null);
    }

    public MapSet(Comparator<E> comparator) {
        this.map = new LinkedListMap<>(comparator);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public boolean contains(E element) {
        return this.map.containsKey(element);
    }

    @Override
    public void add(E element) {
        this.map.put(element, null);
    }

    @Override
    public void remove(E element) {
        this.map.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        this.map.traversal(new LinkedListMap.Visitor<>() {
            @Override
            public boolean visit(E key, Object value) {
                return visitor.visit(key);
            }
        });
    }
}
