package com.lx.第二季.图.src;

import com.lx.第一季.堆.src.BinaryHeap;
import com.lx.第二季.并查集.src.GenerationUnionFind;

import java.util.*;
import java.util.Map.Entry;

/**
 * 有向网的邻接表实现
 * 也可以表示有向图(权值设为 null 即可)
 * 也可以表示无向网/无向图(添加 edge 时添加两个方向的 edge 即可)
 *
 * @param <V> 结点存储的数据类型
 * @param <E> 边的权重类型
 */
public class ListGraph<V, E> extends Graph<V, E> {
    public ListGraph() {
        super();
    }

    /**
     * 用户如果需要使用到最小生成树算法和最短路径算法就需要使用此构造函数定义权值操作相关规则
     *
     * @param weightManager 用于定义权值操作相关接口
     */
    public ListGraph(WeightManager<E> weightManager) {
        super(weightManager);
    }

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

    @Override
    public void breathFirstSearch(V begin, Visitor<V> visitor) {
        if (visitor == null) return;

        Vertex<V, E> beginVertex = this.vertices.get(begin);
        if (beginVertex == null) return;

        // 进行深度遍历
        this.breathFirstSearch(beginVertex, visitor, new HashSet<>());
    }

    /**
     * 图的广度优先遍历(队列实现)
     *
     * @param beginVertex 进行广度优先遍历的起始结点
     * @param visitor     开发给外界的遍历接口
     * @param visited     用于存储已经访问过的顶点的集合
     */
    private void breathFirstSearch(Vertex<V, E> beginVertex, Visitor<V> visitor, Set<Vertex<V, E>> visited) {
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

    @Override
    public void depthFirstSearchWithRecursion(V begin, Visitor<V> visitor) {
        if (visitor == null) return;

        Vertex<V, E> beginVertex = this.vertices.get(begin);
        if (beginVertex == null) return;

        // 进行深度遍历
        this.depthFirstSearchWithRecursion(beginVertex, visitor, new HashSet<>());
    }

    /**
     * 图的深度优先遍历(递归实现)
     *
     * @param beginVertex 进行深度优先遍历的起始顶点
     * @param visitor     顶点访问接口
     * @param visited     用于存储已经访问过的顶点的集合
     */
    private void depthFirstSearchWithRecursion(Vertex<V, E> beginVertex, Visitor<V> visitor, Set<Vertex<V, E>> visited) {
        visitor.visit(beginVertex.value); // 访问顶点
        visited.add(beginVertex); // 标记为已访问

        // 遍历 beginVertex 下一层的所有顶点(所有出度边的终点)
        for (Edge<V, E> outEdge : beginVertex.outEdges) {
            Vertex<V, E> nextVertex = outEdge.to;

            // 如果顶点没被访问过则对每个顶点进行一次深度遍历
            if (!visited.contains(nextVertex))
                depthFirstSearchWithRecursion(nextVertex, visitor, visited);
        }
    }

    /**
     * 图的深度优先遍历(栈实现)
     *
     * @param begin   进行深度优先遍历的起点
     * @param visitor 顶点访问接口
     */
    @Override
    public void depthFirstSearchWithStack(V begin, Visitor<V> visitor) {
        if (visitor == null) return;

        Vertex<V, E> beginVertex = this.vertices.get(begin);
        if (beginVertex == null) return;

        // 进行深度遍历
        this.depthFirstSearchWithStack(beginVertex, visitor, new HashSet<>());
    }

    /**
     * 图的深度优先遍历(栈实现)
     *
     * @param beginVertex 进行深度优先遍历的起始结点
     * @param visitor     顶点访问接口
     * @param visited     用于存储已经访问过的顶点的集合
     */
    public void depthFirstSearchWithStack(Vertex<V, E> beginVertex, Visitor<V> visitor, Set<Vertex<V, E>> visited) {
        Stack<Vertex<V, E>> stack = new Stack<>();

        /* 1.首先对起点进行访问并入栈 */
        stack.push(beginVertex);
        if (visitor.visit(beginVertex.value)) return; // 标记为访问过
        visited.add(beginVertex);

        Vertex<V, E> backOffVertex; // 标记深度遍历过程中回退所经过的顶点
        Vertex<V, E> forwardVertex; // 标记深度遍历过程中下一个目标顶点

        while (!stack.isEmpty()) {
            // 弹出栈顶元素
            backOffVertex = stack.pop();

            // 挑选符合条件的出度边
            for (Edge<V, E> edge : backOffVertex.outEdges) {
                forwardVertex = edge.to;

                // 选取一条未访问过的出度边
                if (!visited.contains(forwardVertex)) {
                    stack.push(edge.from); // 出度边的起点入栈
                    stack.push(forwardVertex); // 出度边的终点入栈
                    if (visitor.visit(forwardVertex.value)) return; // 访问
                    visited.add(forwardVertex); // 标记未已访问

                    // 一旦选取了一条出度边，就需要取消其他同级出度边的遍历(沿着一条路一直走下去)
                    break;
                }
            }
        }
    }

    /**
     * 有向无环图图的拓扑排序
     *
     * @return 拓扑排序序列
     */
    @Override
    public List<V> topologicalSort() {
        Queue<Vertex<V, E>> queue = new LinkedList<>(); // 存放每一轮拓扑排序过程中度为 0 的顶点
        Map<Vertex<V, E>, Integer> map = new HashMap<>(); // 记录图中各顶点的入度数
        List<V> list = new ArrayList<V>(); // 存放最终的拓扑排序序列

        this.vertices.forEach((V value, Vertex<V, E> vertex) -> {
            int inEdgeSize = vertex.inEdges.size();

            if (inEdgeSize == 0)
                queue.offer(vertex); // 入度边数为 0 的顶点入队
            else
                map.put(vertex, inEdgeSize); // 入度边数不为 0 的顶点使用 map 保存并记录其入度边的数量
        });

        Vertex<V, E> workVertex;

        while (!queue.isEmpty()) {
            workVertex = queue.poll(); // 出队一个元素
            list.add(workVertex.value); // 输出到拓扑排序序列

            // 遍历出队元素的所有出度边的终点(模拟在图中删除该出队元素)
            workVertex.outEdges.forEach((Edge<V, E> outEdge) -> {
                Vertex<V, E> toVertex = outEdge.to;  // 获取出度边的终点
                int inSize = map.get(toVertex); // 获取 map 中记录的终点的入度边个数

                if (--inSize == 0) // 因 '删除' 出队元素导致终点的入度边个数变为 0,则将该终点入队
                    queue.offer(toVertex);
                else // 否则仅更新终点对应的入度边数量
                    map.put(toVertex, inSize);
            });
        }

        return list;
    }

    /**
     * 普里姆算法构造最小生成树(前提:图结构是一个有向无环图)
     * 设 G = (V, E) 是有权的无向连通图，A 是最小生成树的边集
     * 算法从 S = { u0 } ((u0)∈V), A = {} 开始, 重复执行下述操作,直到 A.size() == 顶点数-1
     * 找到切分 C = (S, V-S) 的最小横切边 (u0, v0) (u0∈S, v0∈(V-S))并入集合 A, 同时将 v0 并入集合 A
     * 最坏时间复杂度(以本案例邻接表的实现而言的): O(ElogE),E 为图的边数
     * 普里姆算法的时间复杂度可能随着使用的堆的不同而不同
     *
     * @return 最小生成树的边集合
     */
    @Override
    public Set<EdgeInfo<V, E>> getMstWithPrim() {
        int maxEdgeSize = this.vertices.size() - 1; // 最小生成树应具有的边数
        if (maxEdgeSize <= 0) return null; // 只有一个顶点无法构成生成树

        Set<Vertex<V, E>> visitedVertices = new HashSet<>(); // 集合 S
        Set<EdgeInfo<V, E>> mstEdges = new HashSet<>(); // 存放最小生成树的边集

        // 首先随机选取一个顶点 u0 作为集合 S 的元素(表示从顶点 u0 开始构造最小生成树)
        Vertex<V, E> beginVertex = this.vertices.values().iterator().next();
        visitedVertices.add(beginVertex);

        // 将 beginVertex 的所有出度边(由于最小生成树针对的是无向图,因此操作出度边、入度边都可以)塞入一个最小堆
        // 后续利用最小堆来挑选权值最小的边(注意用户需要自定义 weightManager 中 compare 设置最小堆比较规则)
        BinaryHeap<Edge<V, E>> minHeap = new BinaryHeap<>(this.edgeComparator, beginVertex.outEdges);

        Edge<V, E> minEdge;
        Vertex<V, E> minEdgeTo;

        while (!minHeap.isEmpty() && mstEdges.size() < maxEdgeSize) { // 最坏情况下堆为空且图中所有边都加入了堆中, O(E)
            minEdge = minHeap.remove(); // 移除顶部元素(获取权值最小的边) O(logE)
            minEdgeTo = minEdge.to;

            // 注意1:判断该边是否指向集合 S 的外部(是否是横切边),此判断需要放在这里(在顶点移除的边加入最终的最小生成树边集合前进行)
            // 而不应该放在下面集合 S 新加入的顶点所有出度边放入最小堆中这个操作前(当前现在可以额外加上这个条件(不过没必要))
            if (!visitedVertices.contains(minEdgeTo)) {
                mstEdges.add(minEdge.getEdgeInfo()); // 将边信息添加到最小生成树边集合中,O(1)
                visitedVertices.add(minEdgeTo); // 将挑选的边的终点加入集合 S 中,O(1)

                // 注意2:这里仅需将新加入集合 S 的新顶点所有出度边加入最小堆中,不需要将集合 S 所有顶点的出度边都加入最小堆
                // (因为最小堆中仍然存在集合 S 中其他顶点的出度边)
                for (Edge<V, E> outEdge : minEdgeTo.outEdges)
                    // 这里也可以额外加上判断 outEdge 的终点是否在集合 S 中
                    minHeap.add(outEdge); // O(logE)
            }
        }

        return mstEdges;
    }

    /**
     * 克鲁斯卡尔算法构造最小生成树(前提:图结构是一个有向无环图)
     * 最坏时间复杂度(以本案例邻接表的实现而言的):O(E) + O(V) + O(ElogE) = O(ElogE), E为图的边数,V为顶点数
     * 由于 V 受 E 影响,因此可以去掉 O(V),(V 不算另一个单独的变量)
     *
     * @return 最小生成树的边集合
     */
    @Override
    public Set<EdgeInfo<V, E>> getMstWithKruskal() {
        int maxEdgeSize = this.vertices.size() - 1; // 最小生成树应具有的边数
        if (maxEdgeSize <= 0) return null; // 只有一个顶点无法构成生成树

        // 根据图的所有边建一个小根堆(用于取前 Top K 条边), 批量建堆的时间复杂度是 O(E)
        BinaryHeap<Edge<V, E>> heap = new BinaryHeap<>(this.edgeComparator, this.edges);
        // 存放最小生成树的边集
        Set<EdgeInfo<V, E>> mstEdges = new HashSet<>();

        // 使用并查集处理环问题
        GenerationUnionFind<Vertex<V, E>> unionFind = new GenerationUnionFind<>();
        // 图中所有顶点(使用顶点或顶点值构成集合都一样)初始化为并查集中的一个集合
        this.vertices.forEach((V value, Vertex<V, E> vertex) -> { // O(V)
            unionFind.makeSet(vertex);
        });

        Edge<V, E> minEdge;

        while (!heap.isEmpty() && mstEdges.size() < maxEdgeSize) { // 最坏情况下直到堆为空 O(E)
            minEdge = heap.remove(); // 取出权值最小的边 O(logE)

            // 如果 from、to 不属于同一个集合则可以选择该边
            if (!unionFind.isSame(minEdge.from, minEdge.to)) { // 由于并查集优化到 O(α(n)<5) = O(1)
                unionFind.union(minEdge.from, minEdge.to); // 将 from、to 所属的两个集合并起来
                mstEdges.add(minEdge.getEdgeInfo()); // 将边加入最小生成树边集中
            }
        }

        return mstEdges;
    }

    /**
     * 求单源最短路径的迪杰斯特拉算法(简单版)
     *
     * @param begin 需要求最短路径的源点的 value
     * @return {顶点1:源点到顶点1的最短路径的权值, 顶点2:源点到顶点2的最短路径的权值, ...}
     */
    @Override
    public Map<V, E> dijkstraSimpleVersion(V begin) {
        Vertex<V, E> beginVertex = this.vertices.get(begin); // 获取源点
        if (beginVertex == null) {
            System.out.println("源点不存在~");
            return null;
        }

        // 存储最终源点到其他顶点的正确的最短路径
        Map<V, E> selectedPaths = new HashMap<>();
        // 存储迭代过程中源点到其他顶点的最短路径 {顶点: 源点到顶点的最短路径权值}
        Map<Vertex<V, E>, E> unSelectedPaths = new HashMap<>();

        // 初始化 unSelectedPath，这里没有添加无法直接到达的顶点的最短路径，
        // 就相当于后续以该顶点为 key 拿到的是 null,在下面判断时以拿到 null 来表示 ∞
        beginVertex.outEdges.forEach((Edge<V, E> edge) -> {
            unSelectedPaths.put(edge.to, edge.weight);
        });

        Entry<Vertex<V, E>, E> minPath;
        Vertex<V, E> minEndVertex;
        E minWeight;

        Vertex<V, E> endVertex;
        E oldWeight;
        E newWeight;

        while (!unSelectedPaths.isEmpty()) {
            minPath = this.getMinPath1(unSelectedPaths); // 从 unSelectedPath 挑选一条最短路径
            minEndVertex = minPath.getKey(); // 获取挑选的最短路径的终点
            minWeight = minPath.getValue(); // 获取挑选的最短路径的权值

            selectedPaths.put(minEndVertex.value, minWeight); // selectedPath 中添加选择的最短路径
            unSelectedPaths.remove(minEndVertex); // unSelectedPath 中移除选择的最短路径

            // 对挑选的最短路径的终点的所有出度边执行松弛操作
            for (Edge<V, E> edge : minEndVertex.outEdges) {
                endVertex = edge.to; // 挑选的最短路径终点出度边的终点

                // 出度边终点指向 selectedPath 中已选择路径的终点或指向源点则跳过
                if (selectedPaths.containsKey(endVertex.value) || endVertex.equals(beginVertex))
                    continue;

                // unSelectedPath 获取源点到 endVertex 的旧的最短路径
                oldWeight = unSelectedPaths.get(endVertex);
                // 因 minEndVertex 的连通而得到源点到 endVertex 新的最短路径:
                // (源点到 minEndVertex 的最短路径 + 边 minEndVertex -> endVertex 的权值)
                newWeight = this.weightManager.add(minWeight, edge.weight);

                // 试图更新 unSelectedPath 中的最短路径
                // unSelectedPath.get(endVertex) == null 表示原先 unSelectedPath 中该最短路径的权值为 ∞,因此直接更新即可
                if (unSelectedPaths.get(endVertex) == null
                        || this.weightManager.compare(newWeight, oldWeight) < 0)
                    unSelectedPaths.put(endVertex, newWeight);
            }
        }

        return selectedPaths;
    }

    /**
     * 从 unSelectedPath 中选出最短路径
     * (不使用最小堆是因为最小堆采用的整数索引要与 map 的 key 关联起来比较麻烦(由于挑选最短路径的过程中需要通过终点作为 key 来索引最短路径,而小顶堆又只能根据整数来索引数据),要使用索引堆)
     *
     * @param unSelectedPaths {终点1: 最短路径权值1, 终点2: 最短路径权值2, ...}
     * @return {最短路径的终点: 最短路径的权值}
     */
    private Entry<Vertex<V, E>, E> getMinPath1(Map<Vertex<V, E>, E> unSelectedPaths) {
        Iterator<Entry<Vertex<V, E>, E>> it = unSelectedPaths.entrySet().iterator();
        Entry<Vertex<V, E>, E> minPath = it.next();

        Entry<Vertex<V, E>, E> workEntry;

        while (it.hasNext()) {
            workEntry = it.next();
            if (this.weightManager.compare(workEntry.getValue(), minPath.getValue()) < 0)
                minPath = workEntry;
        }

        return minPath;
    }

    /**
     * 求单源最短路径的迪杰斯特拉算法(完整版)
     *
     * @param begin 需要求最短路径的源点的 value
     * @return {顶点1:源点到顶点1的最短路径的完整信息(包含路径的总权值和路径上的所有边信息), 顶点2:源点到顶点2的最短路径的完整信息, ...}
     */
    @Override
    public Map<V, FullPathInfo<V, E>> dijkstraFullVersion(V begin) {
        Vertex<V, E> beginVertex = this.vertices.get(begin); // 获取源点
        if (beginVertex == null) {
            System.out.println("源点不存在~");
            return null;
        }

        // 存储最终源点到其他顶点的正确的最短路径
        Map<V, FullPathInfo<V, E>> selectedPaths = new HashMap<>();
        // 存储迭代过程中源点到其他顶点的最短路径 {顶点: 源点到顶点的最短路径的完整信息}
        Map<Vertex<V, E>, FullPathInfo<V, E>> unSelectedPaths = new HashMap<>();

        // 初始化源点到源点的路径,后续对源点的出度边也一视同仁的执行松弛操作(这里换另一种思路来统一逻辑)(相当于添加哨兵)
        unSelectedPaths.put(beginVertex, new FullPathInfo<>(this.weightManager.zero()));

        Entry<Vertex<V, E>, FullPathInfo<V, E>> minPath;
        Vertex<V, E> minEndVertex;
        FullPathInfo<V, E> minPathInfo;

        Vertex<V, E> endVertex;

        while (!unSelectedPaths.isEmpty()) {
            minPath = this.getMinPath2(unSelectedPaths); // 从 unSelectedPath 挑选一条最短路径
            minEndVertex = minPath.getKey(); // 获取挑选的最短路径的终点
            minPathInfo = minPath.getValue(); // 获取挑选的最短路径的完整信息

            selectedPaths.put(minEndVertex.value, minPathInfo); // selectedPath 中添加选择的最短路径
            unSelectedPaths.remove(minEndVertex); // unSelectedPath 中移除选择的最短路径

            // 迭代挑选的最短路径的终点的所有出度边
            for (Edge<V, E> edge : minEndVertex.outEdges) {
                endVertex = edge.to; // 挑选的最短路径终点出度边的终点

                // (如果是无向图)出度边终点指向 selectedPath 中已选择路径的终点则跳过
                if (selectedPaths.containsKey(endVertex.value))
                    continue;

                // 对 minPath 的终点 minEndVertex 的所有出度边 edge 执行松弛操作
                this.relaxToDijkstra(unSelectedPaths, minPathInfo, edge);
            }
        }

        selectedPaths.remove(beginVertex.value); // 将源点到源点的最短路径删除
        return selectedPaths;
    }

    /**
     * 对边 edge 执行松弛操作，试图更新源点到 edge.to 的最短路径
     *
     * @param unSelectedPaths 尚未确定的最短路径集
     * @param fromMinPathInfo 已经确定的源点到 edge.from 的最短路径信息
     * @param edge            需执行松弛操作的边
     */
    private void relaxToDijkstra(
            Map<Vertex<V, E>, FullPathInfo<V, E>> unSelectedPaths,
            FullPathInfo<V, E> fromMinPathInfo,
            Edge<V, E> edge
    ) {
        // unSelectedPath 获取源点到 edge.to 的旧的最短路径
        FullPathInfo<V, E> oldMinPathInfo = unSelectedPaths.get(edge.to);

        // 因 edge.from 的连通而得到源点到 edge.to 的新路径的权值:
        // (源点到 edge.from 的最短路径的权值 + 边 edge 的权值)
        E newWeight = this.weightManager.add(fromMinPathInfo.weight, edge.weight);

        /* 试图更新 unSelectedPath 中的最短路径 */

        // 如果旧路径存在且新路径比旧路径长或相等则跳过
        if (oldMinPathInfo != null && this.weightManager.compare(newWeight, oldMinPathInfo.weight) >= 0)
            return;

        // oldMinPath == null 表示原先 unSelectedPath 中源点到 edge.to 的最短路径没有找到,因此需要创建一条出来
        if (oldMinPathInfo == null) {
            oldMinPathInfo = new FullPathInfo<>();
            unSelectedPaths.put(edge.to, oldMinPathInfo);
        } else {
            // 将源点到 edge.to 的旧的最短路径清空
            oldMinPathInfo.edgeInfos.clear();
        }

        // 添加新找到的从源点到 edge.to 的最短路径
        oldMinPathInfo.edgeInfos.addAll(fromMinPathInfo.edgeInfos); // 首先添加源点到 edge.from 的最短路径
        oldMinPathInfo.edgeInfos.add(edge.getEdgeInfo()); // 接着添加路径: edge.from -> edge.to
        oldMinPathInfo.weight = newWeight; // 更新 源点 -> edge.to 路径的权值
    }

    /**
     * 从 unSelectedPath 中选出一条最短路径
     *
     * @param unSelectedPaths {终点1: 最短路径的完整信息, 终点2: 最短路径的完整信息, ...}
     * @return {最短路径的终点: 最短路径的完整信息}
     */
    private Entry<Vertex<V, E>, FullPathInfo<V, E>> getMinPath2(Map<Vertex<V, E>, FullPathInfo<V, E>> unSelectedPaths) {
        Iterator<Entry<Vertex<V, E>, FullPathInfo<V, E>>> it = unSelectedPaths.entrySet().iterator();
        Entry<Vertex<V, E>, FullPathInfo<V, E>> minPath = it.next();

        Entry<Vertex<V, E>, FullPathInfo<V, E>> workEntry;

        while (it.hasNext()) {
            workEntry = it.next();
            if (this.weightManager.compare(workEntry.getValue().weight, minPath.getValue().weight) < 0)
                minPath = workEntry;
        }

        return minPath;
    }

    /**
     * 求单源最短路径的 贝尔曼-福特 算法
     *
     * @param begin 需要求最短路径的源点的 value
     * @return 源点到其他所有顶点的最短路径的完整信息(总权值 + 路径上的所有边信息)
     */
    @Override
    public Map<V, FullPathInfo<V, E>> bellmanFord(V begin) {
        Vertex<V, E> beginVertex = this.vertices.get(begin); // 获取源点
        if (beginVertex == null) {
            System.out.println("源点不存在~");
            return null;
        }

        // 存储迭代过程中找到的源点到其他顶点的最短路径(对于本次迭代而言是最短路径)信息
        Map<V, FullPathInfo<V, E>> unselectedPaths = new HashMap<>();

        // 后面初始迭代时需要有源点到源点权值 '0' 的路径(如何表示权值 0 由用户定义),便于后面对源点的出度边执行松弛操作
        // 注意1.不需要设置 edgeInfos 为 begin -> begin
        unselectedPaths.put(begin, new FullPathInfo<>(this.weightManager.zero()));

        int count = this.vertices.size() - 1; // 计算需要松弛的最大次数
        boolean isComplete; // 标识所有顶点的最短路径是否都已经得到

        FullPathInfo<V, E> fromMinPathInfo;

        // 执行 v-1 次迭代
        for (int i = 0; i < count; i++) {
            isComplete = true; // 假设所有最短路径都已经确定

            // 对所有边执行一次松弛操作
            for (Edge<V, E> edge : this.edges) {
                fromMinPathInfo = unselectedPaths.get(edge.from.value); // 源点到 edge.from 的最短路径信息
                // 源点到 edge.from 的最短路径为 null,说明对 edge 进行松弛操作一定会失败,因此直接跳过
                if (fromMinPathInfo == null) continue;

                // 只要有一条边松弛成功就说明还没有确定所有的最短路径(就还需要进行下一轮的对所有边进行松弛操作)
                if (this.relaxToBellmanFord(unselectedPaths, fromMinPathInfo, edge))
                    isComplete = false;
            }

            // 如果本轮的对所有边执行的松弛操作都失败则说明所有的最短路径都已经得到,提前结束迭代
            if (isComplete) break;
        }

        for (Edge<V, E> edge : this.edges) {
            fromMinPathInfo = unselectedPaths.get(edge.from.value);
            if (fromMinPathInfo == null) continue;

            // 对所有边执行第 v 次松弛操作(如果还存在边松弛成功则说明图中有负权环)
            if (this.relaxToBellmanFord(unselectedPaths, fromMinPathInfo, edge)) {
                System.out.println("存在负权环,无法得到最短路径~");
                return null;
            }
        }

        unselectedPaths.remove(begin); // 移除源点到源点的路径
        return unselectedPaths;
    }

    /**
     * 对边 edge 执行松弛操作，试图更新源点到 edge.to 的最短路径
     *
     * @param unSelectedPaths 迭代中的最短路径集
     * @param fromMinPathInfo unSelectedPaths 中 源点到 edge.from 的最短路径信息
     * @param edge            需执行松弛操作的边
     */
    private boolean relaxToBellmanFord(
            Map<V, FullPathInfo<V, E>> unSelectedPaths,
            FullPathInfo<V, E> fromMinPathInfo,
            Edge<V, E> edge
    ) {
        // unSelectedPath 获取源点到 edge.to 的旧的最短路径
        FullPathInfo<V, E> oldMinPath = unSelectedPaths.get(edge.to.value);

        // 因 edge.from 的连通而得到源点到 edge.to 的新路径的权值:
        // (源点到 edge.from 的最短路径的权值 + 边 edge 的权值)
        E newWeight = this.weightManager.add(fromMinPathInfo.weight, edge.weight);

        /* 试图更新 unSelectedPath 中的最短路径 */

        // 如果旧路径存在且新路径比旧路径长则跳过
        if (oldMinPath != null && this.weightManager.compare(newWeight, oldMinPath.weight) >= 0)
            return false;

        // oldMinPath == null 表示原先 unSelectedPath 中源点到 edge.to 的最短路径没有找到,因此需要创建一条出来
        if (oldMinPath == null) {
            oldMinPath = new FullPathInfo<>();
            unSelectedPaths.put(edge.to.value, oldMinPath);
        } else {
            // 将源点到 edge.to 的旧的最短路径清空
            oldMinPath.edgeInfos.clear();
        }

        // 添加新找到的从源点到 edge.to 的最短路径
        oldMinPath.edgeInfos.addAll(fromMinPathInfo.edgeInfos); // 首先添加源点到 edge.from 的最短路径
        oldMinPath.edgeInfos.add(edge.getEdgeInfo()); // 接着添加路径: edge.from -> edge.to
        oldMinPath.weight = newWeight; // 更新 源点 -> edge.to 路径的权值

        return true;
    }

    /**
     * 求多源最短路径的佛洛伊德算法
     *
     * @return 以图中任一顶点为源点到其余所有顶点的最短路径: {
     * /             顶点1:{顶点2:顶点1到顶点2的最短路径, 顶点3:顶点1到顶点3的最短路径, ...},
     * /             顶点2:{顶点1:顶点2到顶点1的最短路径, 顶点3:顶点2到顶点3的最短路径, ...},
     * /             顶点3:{顶点1:顶点3到顶点1的最短路径, 顶点2:顶点3到顶点2的最短路径, ...},
     * /             ...
     * /         }
     */
    @Override
    public Map<V, Map<V, FullPathInfo<V, E>>> floyd() {
        Map<V, Map<V, FullPathInfo<V, E>>> allVertexPaths = new HashMap<>();

        // 初始时装入图中所有可直达的路径
        this.edges.forEach((Edge<V, E> edge) -> {
            // 获取以 edge.from 为源点到其他顶点的最短路径集
            Map<V, FullPathInfo<V, E>> path = allVertexPaths.get(edge.from.value);

            // 注意这里不能不判断就直接 new HashMap,因为存在一个顶点有多个出度边的可能。
            if (path == null) {
                path = new HashMap<>();
                allVertexPaths.put(edge.from.value, path);
            }

            // 装入 edge.from 的直达路径(出度边)信息
            FullPathInfo<V, E> pathInfo = new FullPathInfo<>(edge.weight);
            pathInfo.edgeInfos.add(edge.getEdgeInfo());
            path.put(edge.to.value, pathInfo);
        });

        // 任一顶点到其余所有顶点的最短路径中加入任一顶点 k 进行试探
        this.vertices.forEach((V k, Vertex<V, E> kVertex) -> { // O(v)
            // 试探图中任一顶点为源点的情况
            this.vertices.forEach((V i, Vertex<V, E> iVertex) -> { // O(v)
                // 试探以顶点 i 为源点到其余所有顶点的最短路径
                this.vertices.forEach((V j, Vertex<V, E> jVertex) -> { // O(v)
                    if (k == i || k == j || i == j) return;

                    // 获取 i->j 的最短路径
                    FullPathInfo<V, E> i2jPathInfo = this.getPathInfo(allVertexPaths, i, j);

                    // 获取 i->k 的最短路径
                    FullPathInfo<V, E> i2kPathInfo = this.getPathInfo(allVertexPaths, i, k);

                    // 获取 k->j 的最短路径
                    FullPathInfo<V, E> k2jPathInfo = this.getPathInfo(allVertexPaths, k, j);

                    // i->k 或 k->j 不存在则无需更新 i->j
                    if (i2kPathInfo == null || k2jPathInfo == null) return;

                    // 判断 i->k->j 的权值是否小于 i->j 的权值
                    E newWeight = this.weightManager.add(i2kPathInfo.weight, k2jPathInfo.weight);
                    if (i2jPathInfo != null && this.weightManager.compare(newWeight, i2jPathInfo.weight) >= 0) return;

                    /* i->j 为 null 或 i->k->j 的权值小于 i->j 的权值则将 i->j 替换为 i->k->j */

                    if (i2jPathInfo == null) { // 之前不存在 i->j 这条路径则创建一条
                        i2jPathInfo = new FullPathInfo<>();
                        allVertexPaths.get(i).put(j, i2jPathInfo);
                    } else { // 清空 i-j 的旧路径
                        i2jPathInfo.edgeInfos.clear();
                    }

                    // 将 i->j 的最短路径更新为 i->k->j
                    i2jPathInfo.edgeInfos.addAll(i2kPathInfo.edgeInfos);
                    i2jPathInfo.edgeInfos.addAll(k2jPathInfo.edgeInfos);
                    i2jPathInfo.weight = newWeight;
                });
            });
        });

        return allVertexPaths;
    }

    /**
     * 从 allVertexPaths 中获取从 begin 到 end 的最短路径
     *
     * @param allVertexPaths 存储了图中任一顶点到其余所有顶点的最短路径
     * @param begin          路径的源点
     * @param end            路径的终点
     */
    private FullPathInfo<V, E> getPathInfo(Map<V, Map<V, FullPathInfo<V, E>>> allVertexPaths, V begin, V end) {
        Map<V, FullPathInfo<V, E>> beginPaths = allVertexPaths.get(begin);

        if (beginPaths == null)
            beginPaths = new HashMap<>();

        return beginPaths.get(end);
    }

    /**
     * 边比较接口(用于最小生成树算法中比较两边的权值)
     */
    private final Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> edge1, Edge<V, E> edge2) -> {
        return -weightManager.compare(edge1.weight, edge2.weight);
    };

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

        /**
         * 用于获取提供给用户的 edgeInfo 实例
         *
         * @return edgeInfo 实例
         */
        private EdgeInfo<V, E> getEdgeInfo() {
            return new EdgeInfo<>(this.from.value, this.to.value, this.weight);
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
