package com.lx.第二季.图.src;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 图的公共接口
 *
 * @param <V> 结点存储的数据类型
 * @param <E> 边的权值类型
 */
public abstract class Graph<V, E> {
    // 用户可通过传递此实例来自定义权值操作的相关接口
    public WeightManager<E> weightManager;

    public Graph() {
        this(null);
    }

    public Graph(WeightManager<E> weightManager) {
        this.weightManager = weightManager;
    }

    /**
     * 返回图的总边数
     *
     * @return 总边数
     */
    public abstract int edgeSize();

    /**
     * 返回图的总结点数量
     *
     * @return 结点数量
     */
    public abstract int verticesSize();

    /**
     * 添加一个结点
     *
     * @param value 顶点存储的数据
     */
    public abstract void addVertex(V value);

    /**
     * 添加一条边
     *
     * @param from 边的起点
     * @param to   边的终点
     */
    public abstract void addEdge(V from, V to);

    /**
     * 添加一条边
     *
     * @param from   边的起点
     * @param to     边的终点
     * @param weight 边的权值
     */
    public abstract void addEdge(V from, V to, E weight);

    /**
     * 由于图的结点不对外开放，且用户明确知道自己删除的是哪个 value，因此删除结点接口不返回被删除的顶点数据
     *
     * @param value 结点存储的数据类型
     */
    public abstract void removeVertex(V value);

    /**
     * 删除边
     *
     * @param from 边的起点
     * @param to   边的终点
     */
    public abstract void removeEdge(V from, V to);

    /**
     * 图的广度优先遍历
     *
     * @param begin   进行广度优先遍历的起点
     * @param visitor 顶点访问接口
     */
    public abstract void breathFirstSearch(V begin, Visitor<V> visitor);

    /**
     * 图的深度优先遍历(递归实现)
     *
     * @param begin   进行深度优先遍历的起点
     * @param visitor 顶点访问接口
     */
    public abstract void depthFirstSearchWithRecursion(V begin, Visitor<V> visitor);

    /**
     * 图的深度优先遍历(栈实现)
     *
     * @param begin   进行深度优先遍历的起点
     * @param visitor 顶点访问接口
     */
    public abstract void depthFirstSearchWithStack(V begin, Visitor<V> visitor);

    /**
     * 图的拓扑排序(前提:图必须是一个有向无环图)
     *
     * @return 拓扑排序序列
     */
    public abstract List<V> topologicalSort();

    /**
     * 普里姆算法构造最小生成树
     *
     * @return 最小生成树边的集合
     */
    public abstract Set<EdgeInfo<V, E>> getMstWithPrim();

    /**
     * 克鲁斯卡尔算法构造最小生成树
     *
     * @return 最小生成树边的集合
     */
    public abstract Set<EdgeInfo<V, E>> getMstWithKruskal();

    /**
     * 求单源最短路径的 迪杰斯特拉 算法(简单版)
     *
     * @param begin 需要求最短路径的源点的 value
     * @return 源点到其他所有顶点的最短路径的权值
     */
    public abstract Map<V, E> dijkstraSimpleVersion(V begin);

    /**
     * 求单源最短路径的 迪杰斯特拉 算法(完整版)
     *
     * @param begin 需要求最短路径的源点的 value
     * @return 源点到其他所有顶点的最短路径的权值和最短路径所经过的结点信息
     */
    public abstract Map<V, FullPathInfo<V, E>> dijkstraFullVersion(V begin);

    /**
     * 求单源最短路径的 贝尔曼-福特 算法
     *
     * @param begin 需要求最短路径的源点的 value
     * @return 源点到其他所有顶点的最短路径的完整信息(总权值 + 路径上的所有边信息)
     */
    public abstract Map<V, FullPathInfo<V, E>> bellmanFord(V begin);

    /**
     * 求多源最短路径的佛洛伊德算法
     *
     * @return 以图中任一顶点为源点到其余所有顶点的最短路径
     */
    public abstract Map<V, Map<V, FullPathInfo<V, E>>> floyd();

    /**
     * 最短路径算法中返回的完整路径信息类型
     *
     * @param <V> 顶点的 value 类型
     * @param <E> 边的权值类型
     */
    public static class FullPathInfo<V, E> {
        // 路径的总权重
        public E weight;
        // 路径包含的所有边信息的列表(由于这些边是有序的且数量不确定,因此使用 LinkedList)
        List<EdgeInfo<V, E>> edgeInfos = new LinkedList<>();

        public FullPathInfo() {
        }

        public FullPathInfo(E weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "FullPathInfo{" +
                    "weight=" + weight +
                    ", edgeInfos=" + edgeInfos +
                    '}';
        }
    }

    /**
     * 遍历接口
     *
     * @param <V> 顶点数据
     */
    public interface Visitor<V> {
        boolean visit(V value); // 返回 true 表示终止遍历
    }

    /**
     * 用于暴露给用户的边的数据类型(用于构造最小生成树中提供给用户的边)
     *
     * @param <V> 顶点数据
     * @param <E> 权重
     */
    public static class EdgeInfo<V, E> {
        private V from;
        private V to;
        private E weight;

        public EdgeInfo(V from, V to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public V getFrom() {
            return from;
        }

        public void setFrom(V from) {
            this.from = from;
        }

        public V getTo() {
            return to;
        }

        public void setTo(V to) {
            this.to = to;
        }

        public E getWeight() {
            return weight;
        }

        public void setWeight(E weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "EdgeInfo{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }

    /**
     * 用于定义一系列操作权值方法的类
     *
     * @param <E> 权值类型
     */
    public interface WeightManager<E> {
        // 用于最小生成树算法中挑选最小权值的边时用于比较权值时用到
        // >0: w1>w2
        // <0: w1<w2
        // =0: w1==w2
        int compare(E w1, E w2);

        // 用于最小路径算法中计算路径权值之和时用到
        E add(E w1, E w2);

        // 用户需要描述路径权值为 '0' (源点到源点)时的 weight
        // BellmanFord 算法需要用到
        E zero();
    }
}
