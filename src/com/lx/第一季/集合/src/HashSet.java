package com.lx.第一季.集合.src;

import com.lx.第一季.哈希表.src.HashMap;
import com.lx.第一季.映射.src.Map;

/**
 * 使用 HashMap 实现 HashSet
 *
 * @param <E> 集合元素类型
 */
public class HashSet<E> implements Set<E> {
    private HashMap<E, Object> map = new HashMap<>();

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
        this.map.traversal(new Map.Visitor<E, Object>() {
            @Override
            public boolean visit(E key, Object value) {
                return visitor.visit(key);
            }
        });
    }
}
