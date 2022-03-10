package com.lx.树;

import com.lx.树.printer.BinaryTrees;
import com.lx.树.src.AVLTree;
import com.lx.树.src.BinarySearchTree;
import com.lx.树.src.BinaryTree;

import java.util.Comparator;

public class Main {
    static class Person implements Comparable {
        private int age;
        private String name;

        Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public int compareTo(Object o) {
            return this.age - ((Person) o).age;
        }

        @Override
        public String toString() {
            return "(" + this.age + "," + this.name + ")";
        }
    }

    public static void test1(int[] data) {
        BinarySearchTree<Integer> binarySearchTree1 = new BinarySearchTree<>(); // Integer 默认实现了 Comparable 接口

        // 前序: 7 4 2 3 5 9 8 11 12
        // 中序: 2 3 4 5 7 8 9 11 12
        // 后序: 3 2 5 4 8 12 11 9 7
        // 层序: 7 4 9 2 5 8 11 3 12
        for (int i = 0; i < data.length; i++)
            binarySearchTree1.add(data[i]);
        BinaryTrees.println(binarySearchTree1);
        binarySearchTree1.remove(4);
        BinaryTrees.println(binarySearchTree1);

        System.out.println(binarySearchTree1.isComplete());

        System.out.println(binarySearchTree1);
        System.out.println(binarySearchTree1.getHeightWithLevelTraversal());
        System.out.println(binarySearchTree1.getHeightWithRecursion());

        binarySearchTree1.preorderTraversal(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println("");

        binarySearchTree1.preorderTraversalNoRecursion1(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println("");

        binarySearchTree1.preorderTraversalNoRecursion2(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println("");

        binarySearchTree1.inorderTraversal(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println("");

        binarySearchTree1.inorderTraversalNoRecursion(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println("");

        binarySearchTree1.postOrderTraversal(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println("");

        binarySearchTree1.postOrderTraversalNoRecursion(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println("");

        binarySearchTree1.levelTraversal(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println("");
    }

    public static <E> void test2(E[] data) {
        BinarySearchTree<Person> binarySearchTree2 = new BinarySearchTree<>(); // 使用 Person 实现的 Comparable 的 compareTo 方法进行比较
        for (int i = 0; i < data.length; i++)
            binarySearchTree2.add(new Person((Integer) data[i], "person" + i));
        BinaryTrees.println(binarySearchTree2);
    }

    public static <E> void test3(E[] data) {
        BinarySearchTree<Person> binarySearchTree3 = new BinarySearchTree<>(new Comparator<Person>() { // 使用传入的比较器对象的 compare 方法进行比较
            @Override
            public int compare(Person o1, Person o2) {
                return o2.age - o1.age;
            }
        });
        for (int i = 0; i < data.length; i++)
            binarySearchTree3.add(new Person((Integer) data[i], "person" + i));

        binarySearchTree3.add(new Person(9, "john"));
        BinaryTrees.println(binarySearchTree3);

    }

    public static void test4(int[] data) {
        AVLTree<Integer> avlTree = new AVLTree<Integer>();

        for (int i = 0; i < data.length; i++)
            avlTree.add(data[i]);

        BinaryTrees.println(avlTree);
    }

    public static void main(String[] args) {
        int[] data = new int[]{
                59, 51, 24, 83, 86, 57, 84, 18, 88, 85, 68, 95, 80, 49, 56, 26, 70, 36
        };

//        test1(data);
        test4(data);
    }
}
