package com.lx.集合;

import com.lx.集合.src.*;
import com.lx.集合.src.Set.Visitor;

public class Main {
    public static void tets1() {
        HashSet<Integer> listSet = new HashSet<>();

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
        HashSet<Integer> treeSet = new HashSet<>();

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

    public static void tets3() {
        LinkedHashSet<Integer> mapSet = new LinkedHashSet<>();

        mapSet.add(12);
        mapSet.add(11);
        mapSet.add(11);
        mapSet.add(15);
        mapSet.add(11);

        mapSet.traversal(new Visitor<>() { // 匿名类（继承了 Visitor 抽象类）
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });

    }


    public static void main(String[] args) {
//        tets2();
        tets3();

        System.out.println(Float.floatToRawIntBits(1.6f));
        System.out.println(Float.floatToIntBits(1.6f));
    }
}
