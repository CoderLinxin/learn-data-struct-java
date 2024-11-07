package com.lx.第一季.树;

import com.lx.第一季.树.printer.BinaryTrees;
import com.lx.第一季.树.src.AVLTree;
import com.lx.第一季.树.src.BinarySearchTree;
import com.lx.第一季.树.src.BinaryTree;
import com.lx.第一季.树.src.RedBlackTree;

import java.util.Comparator;
import java.util.Date;

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
//        binarySearchTree1.remove(4);
//        BinaryTrees.println(binarySearchTree1);

//        System.out.println(binarySearchTree1.isComplete());
//
//        System.out.println(binarySearchTree1);
//        System.out.println(binarySearchTree1.getHeightWithLevelTraversal());
//        System.out.println(binarySearchTree1.getHeightWithRecursion());

        System.out.println("打印遍历序列--------------------------------------------------------------");
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

        System.out.println("后序递归遍历~");
        binarySearchTree1.postOrderTraversal(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println("\n后序递归遍历~");

        System.out.println("后序非递归遍历1~");
        binarySearchTree1.postOrderTraversalNoRecursion(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println("\n后序非递归遍历1~");

        System.out.println("后序非递归遍历2~");
        binarySearchTree1.postOrderTraversalNoRecursion2(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println("\n后序非递归遍历2~");

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

//        for (int i = 0; i < data.length; i++)
//            avlTree.add(data[i]);
//
//        BinaryTrees.println(avlTree);

        for (int i = 0; i < data.length; i++)
            avlTree.add(data[i]);
        for (int i = 0; i < data.length; i++) {
            System.out.println("[ " + data[i] + " ]");
            avlTree.remove(data[i]);
            BinaryTrees.println(avlTree);
            System.out.println("-------------------------------------------------------------------\n");
        }
    }

    public static void test5(int num) {
        int[] data = new int[num];
        for (int i = 0; i < num; i++)
            data[i] = (int) Math.random() * num;

        AVLTree<Integer> avlTree = new AVLTree<>();
        long start1 = new Date().getTime();
        for (int i = 0; i < num; i++)
            avlTree.add(data[i]);
        for (int i = 0; i < num; i++)
            avlTree.contains(data[i]);
        for (int i = 0; i < num; i++)
            avlTree.remove(data[i]);
        long end1 = new Date().getTime();
        System.out.println("AVL 执行时间：" + (end1 - start1));

        BinarySearchTree<Integer> bST = new BinarySearchTree<>();
        long start2 = new Date().getTime();
        for (int i = 0; i < num; i++)
            bST.add(data[i]);
        for (int i = 0; i < num; i++)
            bST.contains(data[i]);
        for (int i = 0; i < num; i++)
            bST.remove(data[i]);
        long end2 = new Date().getTime();
        System.out.println("bST 执行时间：" + (end2 - start2));
    }

    public static void test6(int[] data) {
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<Integer>();

        for (int i = 0; i < data.length; i++)
            redBlackTree.add(data[i]);
        BinaryTrees.println(redBlackTree);

        for (int i = 0; i < data.length; i++) {
            redBlackTree.remove(data[i]);
        }
//        BinaryTrees.println(redBlackTree);
    }

    public static void main(String[] args) {
        int[] data = new int[]{
                7, 4, 2, 5, 3, 9, 8, 11, 12
        };

        test1(data);
//        test4(data);
//        test5(10000000);
//        test6(data);

    }
}
