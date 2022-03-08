package com.lx.线性表.链表;

import com.lx.线性表.链表.src.Asserts;
import com.lx.线性表.链表.src.BidirectionalCircleLinkedList;
import com.lx.线性表.链表.src.List;

public class Main {
    static void testList(List<Integer> list) {
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);

        list.add(0, 55); // [55, 11, 22, 33, 44]
        list.add(2, 66); // [55, 11, 66, 22, 33, 44]
        list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]

        list.remove(0); // [11, 66, 22, 33, 44, 77]
        list.remove(2); // [11, 66, 33, 44, 77]
        list.remove(list.size() - 1); // [11, 66, 33, 44]

        Asserts.test(list.indexOf(44) == 3);
        Asserts.test(list.indexOf(22) == List.ELEMENT_NOT_FOUND);
        Asserts.test(list.contains(33));
        Asserts.test(list.get(0) == 11);
        Asserts.test(list.get(1) == 66);
        Asserts.test(list.get(list.size() - 1) == 44);

//        list.remove(list.size() - 1);
//        list.remove(list.size() - 1);
//        list.remove(list.size() - 1);
//
//        list.remove(list.size() - 1);
        list.traver();

        System.out.println(list);
    }

    /**
     * 解决约瑟夫问题
     *
     * @param n     元素总数
     * @param value 每轮循环需要数的数（数自己包括在内）（指针只需移动 value - 1）
     */
    static void josephus(int n, int value) {
        BidirectionalCircleLinkedList<Integer> bidirectionalCircleLinkedList = new BidirectionalCircleLinkedList<>();

        // 初始化元素总数
        for (int i = 1; i <= n; i++) bidirectionalCircleLinkedList.add(i);

        // 设置 current 指针，表示从第一个元素开始游戏
        bidirectionalCircleLinkedList.reset();

        // 每轮循环中 current 指针移动 value - 1 次
        while (!bidirectionalCircleLinkedList.isEmpty()) {
            for (int i = 0; i < value - 1; i++)
                bidirectionalCircleLinkedList.next();

            // 本轮被选到的元素退场
            System.out.print(bidirectionalCircleLinkedList.remove() + " ");
        }
    }

    public static void main(String[] args) {
//        LinkedList2<Integer> linkedList = new LinkedList2<>();
//        linkedList.add(10);
//        linkedList.add(11);
//        linkedList.add(12);
//        linkedList.add(13);
//        linkedList.add(14);
//        linkedList.add(15);

//        System.out.println(linkedList.size);
//        linkedList.add(2, 999);
//        System.out.println(linkedList.get(2));
//        linkedList.remove(2);
//        linkedList.set(2, 999);
//        System.out.println(linkedList.indexOf(13));
//        linkedList.traver();

//        testList(new MyArrayList<Integer>());
//        testList(new SingleLinkedList1<Integer>());
//        testList(new SingleLinkedList2<Integer>());
//        testList(new BidirectionalLinkedList<Integer>());
//        testList(new SingleCircleLinkedList<>());
//        testList(new BidirectionalCircleLinkedList<>());

        josephus(8, 3);
    }
}
