package com.lx.第二季.图.src;

import com.lx.第一季.集合.src.HashSet;
import com.lx.第一季.集合.src.Set;

/**
 * 图的邻接表实现
 *
 * @param <V> 结点存储的数据类型
 * @param <E> 边的权重类型
 */
public class ListGraph<V, E> implements Graph<V, E> {
    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int verticesSize() {
        return 0;
    }

    @Override
    public void addVertex(V value) {

    }

    @Override
    public void addEdge(V from, V to) {

    }

    @Override
    public void addEdge(V from, V to, E weight) {

    }

    @Override
    public void removeVertex(V value) {

    }

    @Override
    public void removeEdge(V from, V to) {

    }

    /**
     * 图的结点数据类型(不对外开放)
     *
     * @param <V> 结点存储的数据
     * @param <E> 边的权值类型
     */
    private static class Vertex<V, E> {
        V value; // 结点存储的数据
        Set<Edge<V, E>> inEdges = new HashSet<>(); // 结点的所有入度边
        Set<Edge<V, E>> outEdges = new HashSet<>(); // 结点的所有出度边
    }

    /**
     * 图的边数据类型
     *
     * @param <V> 结点存储的数据类型
     * @param <E> 边的权值类型
     */
    private static class Edge<V, E> {
        Vertex<V, E> from; // 边的起点
        Vertex<V, E> to; // 边的终点
        E weight; // 边的权重
    }
}
