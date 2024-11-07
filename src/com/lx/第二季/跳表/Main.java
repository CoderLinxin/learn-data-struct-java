package com.lx.第二季.跳表;

import com.lx.第二季.跳表.src.SkipList;
import com.lx.第二季.跳表.test.Asserts;
import com.lx.第二季.跳表.tools.Times;

import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        SkipList<Integer, Integer> list = new SkipList<>();
        TreeMap<Integer, Integer> map = new TreeMap<>();
//        test(list, 100_0000, 10);
//        test(map, 100000, 10);

        time();
    }

    private static void time() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        SkipList<Integer, Integer> list = new SkipList<>();
        int count = 10_000000;
        int delta = 10;

        Times.test("SkipList", () -> {
            test(list, count, delta);
        });

        Times.test("TreeMap", () -> {
            test(map, count, delta);
        });
    }

    private static void test(SkipList<Integer, Integer> list, int count, int delta) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            list.put(i, i + delta);
        }
        long end = System.currentTimeMillis();
        System.out.println("添加耗时: " + (end - begin) / 1000.0);

//		System.out.println(list);
        long begin2 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Asserts.test(list.get(i) == i + delta);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("查询耗时: " + (end2- begin2) / 1000.0);

        Asserts.test(list.size() == count);

        long begin3 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Asserts.test(list.remove(i) == i + delta);
        }
        long end3 = System.currentTimeMillis();
        System.out.println("删除耗时: " + (end3- begin3) / 1000.0);
        Asserts.test(list.size() == 0);
    }

    private static void test(TreeMap<Integer, Integer> map, int count, int delta) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            map.put(i, i + delta);
        }
        long end = System.currentTimeMillis();
        System.out.println("添加耗时: " + (end - begin) / 1000.0);

        long begin2 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Asserts.test(map.get(i) == i + delta);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("查询耗时: " + (end2- begin2) / 1000.0);

        long begin3 = System.currentTimeMillis();
        Asserts.test(map.size() == count);
        for (int i = 0; i < count; i++) {
            Asserts.test(map.remove(i) == i + delta);
        }
        Asserts.test(map.size() == 0);
        long end3 = System.currentTimeMillis();
        System.out.println("删除耗时: " + (end3- begin3) / 1000.0);
    }
}
