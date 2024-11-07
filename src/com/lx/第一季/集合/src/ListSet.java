package com.lx.第一季.集合.src;

import com.lx.第一季.线性表.链表.src.BidirectionalLinkedList;
import com.lx.第一季.线性表.链表.src.List;

/**
 * 使用双向链表实现的集合
 *
 * @param <E> 集合元素类型
 */
public class ListSet<E> implements Set<E> {
    private final List<E> list = new BidirectionalLinkedList<>();

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public boolean contains(E element) {
        return this.list.contains(element);
    }

    @Override
    public void add(E element) {
        int index = this.list.indexOf(element);

        if (index != List.ELEMENT_NOT_FOUND) // 元素存在则覆盖
            this.list.set(index, element);
        else // 元素不存在则添加
            this.list.add(element);
    }

    @Override
    public void remove(E element) {
        this.list.remove(this.list.indexOf(element));
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null) return;

        for (int i = 0; i < this.size(); i++)
            if (visitor.visit(this.list.get(i))) return;
    }
}
