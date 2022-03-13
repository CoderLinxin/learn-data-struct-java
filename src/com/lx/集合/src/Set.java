package com.lx.集合.src;

/**
 * 集合的公共接口
 *
 * @param <E> 集合元素类型
 */
public interface Set<E> {
    /**
     * 返回集合元素个数
     *
     * @return 元素个数
     */
    int size();

    /**
     * 集合是否为空
     *
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 清空集合
     */
    void clear();

    /**
     * 判断集合中是否包含某元素
     *
     * @param element 需要判断的元素怒
     * @return 是否包含元素
     */
    boolean contains(E element);

    /**
     * 集合中添加一个元素
     *
     * @param element 添加的元素
     */
    void add(E element);

    /**
     * 集合中删除一个元素
     *
     * @param element 删除的元素
     */
    void remove(E element);

    /**
     * 遍历集合
     *
     * @param visitor 遍历接口
     */
    void traversal(Visitor<E> visitor);

    /**
     * 遍历集合的接口
     *
     * @param <E> 集合元素类型
     */
    abstract class Visitor<E> {
        boolean stop; // true 代表停止遍历

        public abstract boolean visit(E element);
    }
}
