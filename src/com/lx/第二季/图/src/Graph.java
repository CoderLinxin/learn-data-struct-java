package com.lx.第二季.图.src;

/**
 * 图的公共接口
 *
 * @param <V> 结点存储的数据类型
 * @param <E> 边的权值类型
 */
public interface Graph<V, E> {
    /**
     * 返回图的总边数
     *
     * @return 总边数
     */
    int edgeSize();

    /**
     * 返回图的总结点数量
     *
     * @return 结点数量
     */
    int verticesSize();

    /**
     * 添加一个结点
     *
     * @param value 顶点存储的数据
     */
    void addVertex(V value);

    /**
     * 添加一条边
     *
     * @param from 边的起点
     * @param to   边的终点
     */
    void addEdge(V from, V to);

    /**
     * 添加一条边
     *
     * @param from   边的起点
     * @param to     边的终点
     * @param weight 边的权值
     */
    void addEdge(V from, V to, E weight);

    /**
     * 由于图的结点不对外开放，且用户明确知道自己删除的是哪个 value，因此删除结点接口不返回被删除的顶点数据
     *
     * @param value 结点存储的数据类型
     */
    void removeVertex(V value);

    /**
     * 删除边
     *
     * @param from 边的起点
     * @param to   边的终点
     */
    void removeEdge(V from, V to);

    /**
     * 图的广度优先遍历
     *
     * @param begin   进行广度优先遍历的起点
     * @param visitor 顶点访问接口
     */
    void breathFirstSearch(V begin, Visitor<V> visitor);

    /**
     * 图的深度优先遍历
     *
     * @param begin   进行深度优先遍历的起点
     * @param visitor 顶点访问接口
     */
    void depthFirstSearch(V begin, Visitor<V> visitor);

    /**
     * 遍历接口
     *
     * @param <V> 顶点数据
     */
    interface Visitor<V> {
        boolean visit(V value); // 返回 true 表示终止遍历
    }
}
