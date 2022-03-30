package com.lx.第二季.并查集;

import com.lx.第二季.并查集.src.*;
import com.lx.第二季.排序.tools.Asserts;
import com.lx.第二季.排序.tools.Times;

public class Main {
    private static void test1() {
        UnionFind unionFind = new QuickFind(12);

        unionFind.union(0, 1);
        unionFind.union(0, 4);
        unionFind.union(0, 3);
        unionFind.union(3, 2);
        unionFind.union(3, 5);

        unionFind.union(6, 7);

        unionFind.union(8, 9);
        unionFind.union(8, 10);
        unionFind.union(9, 11);

        System.out.println(unionFind.isSame(6, 0));
        unionFind.union(2, 6);
        System.out.println(unionFind.isSame(6, 0));
    }

    private static void test2() {
        UnionFind unionFind = new QuickUnion(12);

        unionFind.union(0, 1);
        unionFind.union(0, 4);
        unionFind.union(0, 3);
        unionFind.union(3, 2);
        unionFind.union(3, 5);

        unionFind.union(6, 7);

        unionFind.union(8, 9);
        unionFind.union(8, 10);
        unionFind.union(9, 11);

        System.out.println(unionFind.isSame(6, 0));
        unionFind.union(2, 6);
        Asserts.test(unionFind.isSame(6, 0));
        System.out.println(unionFind.isSame(6, 0));
    }

    private static final int count = 100000;

    private static void test3(UnionFind unionFind) {
        Times.test(unionFind.getClass().getSimpleName(), () -> {
            for (int i = 0; i < count; i++)
                unionFind.union((int) (Math.random() * count), (int) (Math.random() * count));

            for (int i = 0; i < count; i++)
                unionFind.isSame((int) (Math.random() * count), (int) (Math.random() * count));

            unionFind.union(0, 1);
            unionFind.union(0, 4);
            unionFind.union(0, 3);
            unionFind.union(3, 2);
            unionFind.union(3, 5);

            unionFind.union(6, 7);

            unionFind.union(8, 9);
            unionFind.union(8, 10);
            unionFind.union(9, 11);

            unionFind.union(2, 6);
            Asserts.test(unionFind.isSame(6, 0));
        });
    }

    public static void test4(GenerationUnionFind unionFind) {
        Times.test(unionFind.getClass().getSimpleName(), () -> {
            for (int i = 0; i < count; i++)
                unionFind.makeSet((int) (Math.random() * count));

            for (int i = 0; i < count; i++)
                unionFind.union((int) (Math.random() * count), (int) (Math.random() * count));

            for (int i = 0; i < count; i++)
                unionFind.isSame((int) (Math.random() * count), (int) (Math.random() * count));

            for (int i = 0; i <= 11; i++)
                unionFind.makeSet(i);

            unionFind.union(0, 1);
            unionFind.union(0, 4);
            unionFind.union(0, 3);
            unionFind.union(3, 2);
            unionFind.union(3, 5);

            unionFind.union(6, 7);

            unionFind.union(8, 9);
            unionFind.union(8, 10);
            unionFind.union(9, 11);

            unionFind.union(2, 6);
            Asserts.test(unionFind.isSame(6, 0));
        });
    }

    public static void main(String[] args) {
//        test1();
//        test2();

//        test3(new QuickFind(count));
//        test3(new QuickUnion(count));
        test3(new QuickUnion_Rank(count));
        test3(new QuickUnion_Size(count));
        test3(new QuickUnion_Rank_Path_Compression(count));
        test3(new QuickUnion_Rank_Path_Spliting(count));
        test3(new QuickUnion_Rank_Path_Halving(count));
        test4(new GenerationUnionFind<Integer>());


    }
}
