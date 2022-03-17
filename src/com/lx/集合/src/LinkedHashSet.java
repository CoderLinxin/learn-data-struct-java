package com.lx.集合.src;

import com.lx.哈希表.src.HashMap;
import com.lx.哈希表.src.LinkedHashMap;
import com.lx.映射.src.Map;

/**
 * 使用 LinkedHashMap 实现 LinkedHashSet
 *
 * @param <E> 集合元素类型
 */
public class LinkedHashSet<E> implements Set<E> {
    private LinkedHashMap<E, Object> map = new LinkedHashMap<>();

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
    public void traversal(Set.Visitor<E> visitor) {
        this.map.traversal(new Map.Visitor<E, Object>() {
            @Override
            public boolean visit(E key, Object value) {
                return visitor.visit(key);
            }
        });
    }
}
