package com.lx.第二季.图;

import com.lx.第二季.图.model.Data;
import com.lx.第二季.图.src.Graph;
import com.lx.第二季.图.src.Graph.WeightManager;
import com.lx.第二季.图.src.ListGraph;

import java.util.List;
import java.util.Set;

public class Main {
    static WeightManager<Double> weightManager = new WeightManager<Double>() {
        public int compare(Double w1, Double w2) {
            return w1.compareTo(w2);
        }

        public Double add(Double w1, Double w2) {
            return w1 + w2;
        }
    };

    static void testBfs() {
        Graph<Object, Double> graph = directedGraph(Data.BFS_04);
        graph.breathFirstSearch(2, (Object v) -> {
            System.out.println(v);
            return false;
        });
    }

    static void testDfs1() {
        Graph<Object, Double> graph = directedGraph(Data.DFS_02);
        graph.depthFirstSearchWithRecursion("a", (Object v) -> {
            System.out.println(v);
            return false;
        });
    }

    static void testDfs2() {
        Graph<Object, Double> graph = directedGraph(Data.DFS_02);
        graph.depthFirstSearchWithStack("a", (Object v) -> {
            System.out.println(v);
            return false;
        });
    }

    static void testMst1() {
        Graph<Object, Double> graph = undirectedGraph(Data.MST_01);
        Set<Graph.EdgeInfo<Object, Double>> infos = graph.mstWithPrim();
        for (Graph.EdgeInfo<Object, Double> info : infos) {
            System.out.println(info);
        }
    }

    static void testMst2() {
        Graph<Object, Double> graph = undirectedGraph(Data.MST_01);
        Set<Graph.EdgeInfo<Object, Double>> infos = graph.mstWithKruskal();
        for (Graph.EdgeInfo<Object, Double> info : infos) {
            System.out.println(info);
        }
    }

    static void testTopo() {
        Graph<Object, Double> graph = directedGraph(Data.TOPO);
        List<Object> list = graph.topologicalSort();
        System.out.println(list);
    }

    static void test() {
        ListGraph<String, Integer> listGraph = new ListGraph<>();
        listGraph.addEdge("v0", "v4", 6);
        listGraph.addEdge("v1", "v0", 9);
        listGraph.addEdge("v2", "v0", 2);
        listGraph.addEdge("v1", "v2", 3);
        listGraph.addEdge("v2", "v3", 5);
        listGraph.addEdge("v3", "v4", 1);

        listGraph.removeVertex("v0");
        listGraph.removeEdge("v0", "v4");
        listGraph.removeEdge("v0", "v4");

        listGraph.print();
    }

    public static void main(String[] args) {
//        testBfs();
//        System.out.println();
//        testDfs1();
//        System.out.println();
//        testDfs2();
//        testTopo();
        testMst1();
        System.out.println();
        testMst2();
    }

    /**
     * 有向图
     */
    private static Graph<Object, Double> directedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
            }
        }
        return graph;
    }

    /**
     * 无向图
     */
    private static Graph<Object, Double> undirectedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
                graph.addEdge(edge[1], edge[0]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
                graph.addEdge(edge[1], edge[0], weight);
            }
        }
        return graph;
    }
}
