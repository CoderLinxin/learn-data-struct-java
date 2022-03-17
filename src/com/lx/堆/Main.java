package com.lx.堆;

import com.lx.堆.src.BinaryHeap;
import com.lx.树.printer.BinaryTrees;

public class Main {
    public static void main(String[] args) {
        Integer[] data = {64, 92, 20, 97, 83, 1, 8, 32, 11, 3, 94, 82};
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(data);

//        binaryHeap.add(15);
//        binaryHeap.add(115);
//        binaryHeap.add(1);
//        binaryHeap.add(12);
//        binaryHeap.add(115);
//        binaryHeap.add(87);
//        binaryHeap.add(94);
//        binaryHeap.add(22);
//        binaryHeap.remove();
//        binaryHeap.remove();
//        binaryHeap.replace(50);

        BinaryTrees.println(binaryHeap);
    }
}
