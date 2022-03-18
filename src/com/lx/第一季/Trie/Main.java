package com.lx.第一季.Trie;

import com.lx.第一季.Trie.src.Trie;
import com.lx.第一季.哈希表.test.Asserts;

public class Main {
    static void test1() {
        Trie<Integer> trie = new Trie<>();
        trie.add("cat", 1);
        trie.add("dog", 2);
        trie.add("catalog", 3);
        trie.add("cast", 4);
        trie.add("小码哥", 5);
        Asserts.test(trie.size() == 5);
        Asserts.test(trie.startWith("do"));
        Asserts.test(trie.startWith("c"));
        Asserts.test(trie.startWith("ca"));
        Asserts.test(trie.startWith("cat"));
        Asserts.test(trie.startWith("cata"));
        Asserts.test(!trie.startWith("hehe"));
        Asserts.test(trie.get("小码哥") == 5);
        Asserts.test(trie.remove("cat") == 1);
        Asserts.test(trie.remove("catalog") == 3);
        Asserts.test(trie.remove("cast") == 4);
        Asserts.test(trie.size() == 2);
        Asserts.test(trie.startWith("小"));
        Asserts.test(trie.startWith("do"));
        Asserts.test(!trie.startWith("c"));
    }

    public static void main(String[] args) {
        Trie<Integer> trie = new Trie<>();

        trie.add("dog", 99);
        trie.add("张三", 50);
        trie.add("cat", 70);
        trie.add("456", 33);
        trie.add("dog", 22);

        System.out.println(trie.size());
//        System.out.println(trie.startWith("张"));
//        System.out.println(trie.startWith("张三"));
//        System.out.println(trie.startWith("张三丰"));
        System.out.println(trie.startWith("d"));
        System.out.println(trie.startWith("do"));
        System.out.println(trie.startWith("dog"));
        System.out.println(trie.startWith("dogs"));

        char a = '张';

        test1();
    }
}
