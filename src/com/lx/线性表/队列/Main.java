package com.lx.线性表.队列;

import com.lx.线性表.队列.src.CircleDEQueue;
import com.lx.线性表.队列.src.CircleQueue;
import com.lx.线性表.队列.src.DEQueue;
import com.lx.线性表.队列.src.LinkedQueue;

public class Main {
    static void test2() {
        CircleQueue<Integer> queue = new CircleQueue<Integer>();
        // 0 1 2 3 4 5 6 7 8 9
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }

        // null null null null null 5 6 7 8 9
        for (int i = 0; i < 5; i++) {
            queue.deQueue();
        }

        // 15 16 17 18 19 5 6 7 8 9
        for (int i = 15; i < 40; i++) {
            queue.enQueue(i);
        }

        queue.traver();

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }

    static void test3() {
        CircleDEQueue<Integer> queue = new CircleDEQueue<>();
        // 头5 4 3 2 1  100 101 102 103 104 105 106 8 7 6 尾

        // 头 8 7 6  5 4 3 2 1  100 101 102 103 104 105 106 107 108 109 null null 10 9 尾
        for (int i = 0; i < 10; i++) {
            queue.enQueueFront(i + 1);
            queue.enQueueRear(i + 100);
        }

        // 头 null 7 6  5 4 3 2 1  100 101 102 103 104 105 106 null null null null null null null 尾
        for (int i = 0; i < 3; i++) {
            queue.deQueueFront();
            queue.deQueueRear();
        }

        // 头 11 7 6  5 4 3 2 1  100 101 102 103 104 105 106 null null null null null null 12 尾
        queue.enQueueFront(11);
        queue.enQueueFront(12);

        queue.traver();

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueueFront());
        }
    }

    public static void main(String[] args) {
//        LinkedQueue<Integer> linkedQueue = new LinkedQueue<>();
//        linkedQueue.enQueue(1);
//        linkedQueue.enQueue(2);
//        linkedQueue.enQueue(3);
//        linkedQueue.enQueue(4);
//        linkedQueue.enQueue(5);
//        int size = linkedQueue.size();
//
//        for (int i = 0; i < size; i++)
//            System.out.println(linkedQueue.deQueue());

//        DEQueue<Integer> deQueue = new DEQueue<>();
//        deQueue.enQueueFront(11);
//        deQueue.enQueueFront(22);
//        deQueue.enQueueRear(33);
//        deQueue.enQueueRear(44);
//
//        // 尾 44 33 11 22 头
//        while (!deQueue.isEmpty())
//            System.out.println(deQueue.deQueueRear());

//        test2();
        test3();
    }
}
