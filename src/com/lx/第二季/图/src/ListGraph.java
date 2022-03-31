package com.lx.第二季.图.src;

import java.util.*;

/**
 * 有向网的邻接表实现
 * 也可以表示有向图(权值设为 null 即可)
 * 也可以表示无向网/无向图(添加 edge 时添加两个方向的 edge 即可)
 *
 * @param <V> 结点存储的数据类型
 * @param <E> 边的权重类型
 */
public class ListGraph<V, E> implements Graph<V, E> {
    // 记录图的所有顶点
    // 为什么使用 map?
    //  1.因为 Vertex 不对外开放,因此外界肯定传入的是顶点的 value,我们需要根据该 value 找到其(映射)对应的 Vertex
    //  2.HashMap 查找的速度较快(O(nlogn))，如果使用传统顶点数组的方式存储所有顶点,那么需要对整个数组都进行遍历(O(n))才能找到目标顶点
    private final Map<V, Vertex<V, E>> vertices = new HashMap<>();
    // 记录图的所有边(仅用于统计边数)
    private final Set<Edge<V, E>> edges = new HashSet<>();

    /**
     * 返回图的总边数
     *
     * @return 总边数
     */
    @Override
    public int edgeSize() {
        return this.edges.size();
    }

    /**
     * 返回图的总顶点数
     *
     * @return 总顶点数
     */
    @Override
    public int verticesSize() {
        return this.vertices.size();
    }

    /**
     * 向图中添加一个顶点
     *
     * @param value 顶点存储的数据
     */
    @Override
    public void addVertex(V value) {
        // 注意虽然 hashSet 不会添加重复的元素,但是仍然需要调用 containsKey 来判断顶点是否包含在图中而不能直接用新顶点来覆盖图中的顶点
        // 因为图中的顶点 Vertex 还包含了 inEdge、outEdge 等额外信息，
        // 因此不能单纯地不经判断就调用 put 使用 new Vertex<>(value)(inEdge、outEdge 信息都是空的) 来覆盖图中的顶点
        if (!vertices.containsKey(value))
            this.vertices.put(value, new Vertex<>(value));
    }

    /**
     * 向图中添加一条有向无权边
     *
     * @param from 边的起点
     * @param to   边的终点
     */
    @Override
    public void addEdge(V from, V to) {
        this.addEdge(from, to, null);
    }

    /**
     * 向图中添加一条有向有权边
     * 由于图的内部操作针对 Vertex,要求 from、to 都有对应的 Vertex
     * 因此下述实现采用如果 from, to 不在图的顶点 map 中则添加到顶点'映射数组'中的策略
     * 保证后续添加边的操作能够进行
     *
     * @param from   边的起点
     * @param to     边的终点
     * @param weight 边的权值
     */
    @Override
    public void addEdge(V from, V to, E weight) {
        // 确保 from、to 对应的顶点都在图中
        this.addVertex(from);
        this.addVertex(to);

        // 获取 from、to 在图中对应的顶点
        Vertex<V, E> fromVertex = this.vertices.get(from);
        Vertex<V, E> toVertex = this.vertices.get(to);

        // 根据 from,to 创建一条边
        Edge<V, E> newEdge = new Edge<>(fromVertex, toVertex, weight);

        /* 由于新边包含了图中旧边的所有信息(from、to、weight)(权重不同则以新边的为主)，
        因此无论边是否存在于图中，直接调用 add 覆盖掉旧边即可(这里就区别于 addVertex 那里的逻辑),平均需查找一次 map
         就不需要再进行额外的判断了，因为如果加了判断条件:
            判断条件通过则还需要执行添加边的代码,平均需要查找两次 map
            判断条件不通过则返回,平均需要查找一次 map */

        // 将新边添加进 edges 中
        this.edges.add(newEdge);

        // 添加到 fromVertex(起点) 的出度边和 toVertex(终点) 的入度边中
        fromVertex.outEdges.add(newEdge);
        toVertex.inEdges.add(newEdge);
    }

    /**
     * 从图中删除一个顶点
     *
     * @param value 结点存储的数据类型
     */
    @Override
    public void removeVertex(V value) {
        /* 1.获取被删除的结点 */
        Vertex<V, E> removeVertex = this.vertices.remove(value);
        if (removeVertex == null) return;

        /* 由于涉及到遍历集合的过程中删除集合中的元素,这里使用 java 提供的迭代器确保能够准确删除 */

        /* 2.释放被删除顶点的所有出度边 */
        for (Iterator<Edge<V, E>> iterator = removeVertex.outEdges.iterator(); iterator.hasNext(); ) {
            Edge<V, E> outEdge = iterator.next(); // 获取当前遍历的出度边
            outEdge.to.inEdges.remove(outEdge); // 从出度边终点的入度边集合中删除
            iterator.remove(); // 删除当前遍历的出度边

            this.edges.remove(outEdge); // 从 edges 集合中删除
        }

        /* 2.释放被删除顶点的所有入度边 */
        for (Iterator<Edge<V, E>> iterator = removeVertex.inEdges.iterator(); iterator.hasNext(); ) {
            Edge<V, E> inEdge = iterator.next(); // 获取当前遍历的入度边
            inEdge.from.outEdges.remove(inEdge); // 从入度边起点的出度边集合中删除
            iterator.remove(); // 删除当前遍历的入度边

            this.edges.remove(inEdge); // 从 edges 集合中删除
        }
    }

    /**
     * 从图中删除一条有向边
     *
     * @param from 边的起点
     * @param to   边的终点
     */
    @Override
    public void removeEdge(V from, V to) {
        /* 1.如果顶点不存在(更不可能存在边)直接返回 */
        Vertex<V, E> fromVertex = this.vertices.get(from);
        if (fromVertex == null) return;
        Vertex<V, E> toVertex = this.vertices.get(to);
        if (toVertex == null) return;
        ;

        Edge<V, E> removeEdge = new Edge<>(fromVertex, toVertex, null);

        /* 2.如果删除成功则才需继续删除(利于性能优化) */
        if (fromVertex.outEdges.remove(removeEdge)) { // 删除起点的出度边
            toVertex.inEdges.remove(removeEdge); // 删除终点的入度边
            this.edges.remove(removeEdge);
        }
    }

    /**
     * 图的广度优先遍历(队列实现)
     *
     * @param begin   进行广度优先遍历的起点
     * @param visitor 开发给外界的遍历接口
     */
    @Override
    public void breathFirstSearch(V begin, Visitor<V> visitor) {
        Vertex<V, E> beginVertex = this.vertices.get(begin);
        if (beginVertex == null) return;

        // 存放入过队(访问过或将被访问)结点的集合
        Set<Vertex<V, E>> visited = new HashSet<>();

        Queue<Vertex<V, E>> queue = new LinkedList<>();
        queue.offer(beginVertex); // 首先将起点入队
        visited.add(beginVertex); // 标记为访问过

        Vertex<V, E> traverVertex;

        while (!queue.isEmpty()) {
            traverVertex = queue.poll(); // 出队一个结点

            if (visitor.visit(traverVertex.value)) return; // 对结点进行访问

            // 遍历出队结点的下一层结点(出队结点的所有出度边的终点)
            for (Edge<V, E> outEdge : traverVertex.outEdges) {
                Vertex<V, E> nextVertex = outEdge.to; // 出度边的终点(下一层结点)

                // 结点未被访问过则入队
                if (!visited.contains(nextVertex)) {
                    queue.offer(nextVertex);
                    visited.add(nextVertex); // 标记为访问过
                }
            }
        }
    }

    /**
     * 图的深度优先遍历
     *
     * @param begin   进行深度优先遍历的起点
     * @param visitor 顶点访问接口
     */
    @Override
    public void depthFirstSearch(V begin, Visitor<V> visitor) {
        Vertex<V, E> beginVertex = this.vertices.get(begin);
        if (beginVertex == null) return;

        // 进行深度遍历
        this.depthFirstSearch(beginVertex, visitor, new HashSet<>());
    }

    /**
     * 图的深度优先遍历
     *
     * @param beginVertex 进行深度优先遍历的起始顶点
     * @param visitor     顶点访问接口
     * @param visited     用于存储已经访问过的顶点的集合
     */
    private void depthFirstSearch(Vertex<V, E> beginVertex, Visitor<V> visitor, Set<Vertex<V, E>> visited) {
        visitor.visit(beginVertex.value); // 访问顶点
        visited.add(beginVertex); // 标记为已访问

        // 遍历 beginVertex 下一层的所有顶点(所有出度边的终点)
        for (Edge<V, E> outEdge : beginVertex.outEdges) {
            Vertex<V, E> nextVertex = outEdge.to;

            // 如果顶点没被访问过则对每个顶点进行一次深度遍历
            if (!visited.contains(nextVertex))
                depthFirstSearch(nextVertex, visitor, visited);
        }
    }

    /**
     * 图的顶点数据类型(不对外开放)
     *
     * @param <V> 顶点存储的数据(注意由于所有顶点采用 map 来存储,用户传入的 value 为自定义对象时建议重写 equals 和 hashCode 方法)
     * @param <E> 边的权值类型
     */
    private static class Vertex<V, E> {
        // 顶点存储的数据
        V value;
        // 由于顶点的边的存储无顺序之分,因此采用 Set 来存储边
        Set<Edge<V, E>> inEdges = new HashSet<>(); // 结点的所有入度边
        Set<Edge<V, E>> outEdges = new HashSet<>(); // 结点的所有出度边

        public Vertex(V value) {
            this.value = value;
        }

        /* 之后向 edges(集合) 中添加边需要重写 equals 和 hashCode 方法,避免添加重复的顶点 */

        // 只要 value 相同就认定为是同一个顶点
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex<?, ?> vertex = (Vertex<?, ?>) o;
            return Objects.equals(value, vertex.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        /* 测试相关 */

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    /**
     * 图的边数据类型
     *
     * @param <V> 顶点存储的数据类型
     * @param <E> 边的权值类型
     */
    private static class Edge<V, E> {
        Vertex<V, E> from; // 边的起点
        Vertex<V, E> to; // 边的终点
        E weight; // 边的权重

        public Edge(Vertex<V, E> from, Vertex<V, E> to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        /* 之后向 edges(集合) 中添加边需要重写 equals 和 hashCode 方法,避免添加重复的边 */

        // 只要边的起点和终点都相同就认为是同一条边(而权值不同说明需要更新这条边的权值)
        // 根据 HashSet 对于添加相同(equals)的元素的策略是覆盖,这样可以成功更新边的权值
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?, ?> edge = (Edge<?, ?>) o;
            return Objects.equals(from, edge.from) &&
                    Objects.equals(to, edge.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        /* 测试相关 */

        public String toString() {
            return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
        }
    }

    /* 测试相关 */

    public void print() {
        System.out.println("[顶点]-------------------");
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            System.out.println(v);
            System.out.println("out-----------");
            System.out.println(vertex.outEdges);
            System.out.println("in-----------");
            System.out.println(vertex.inEdges);
            System.out.println();
        });

        System.out.println("[边]-------------------");
        edges.forEach(System.out::println);
    }
}
