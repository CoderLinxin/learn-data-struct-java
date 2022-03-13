package com.lx.集合;

import com.lx.集合.src.ListSet;
import com.lx.集合.src.Set;
import com.lx.集合.src.Set.Visitor;
import com.lx.集合.src.TreeSet;

public class Main {
    public static void tets1() {
        Set<Integer> listSet = new ListSet<>();

        listSet.add(11);
        listSet.add(12);
        listSet.add(11);
        listSet.add(11);
        listSet.add(15);

        listSet.traversal(new Visitor<>() { // 匿名类（继承了 Visitor 抽象类）
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });

    }

    public static void tets2() {
        TreeSet<Integer> treeSet = new TreeSet<>();

        treeSet.add(11);
        treeSet.add(12);
        treeSet.add(11);
        treeSet.add(11);
        treeSet.add(15);

        treeSet.traversal(new Visitor<>() { // 匿名类（继承了 Visitor 抽象类）
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });

    }

    public static void main(String[] args) {
        tets2();
    }
}
