package com.lx.第一季.堆;

import com.lx.第一季.堆.src.BinaryHeap;
import com.lx.第一季.树.printer.BinaryTrees;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
//        Integer[] data = {64, 92, 20, 97, 83, 1, 8, 32, 11, 3, 94, 82};
//        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(data);

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

//        BinaryTrees.println(binaryHeap);

        // 获取 datas 中前 3 个最大的元素

        Integer[] datas = {64, 92, 20, 97, 83, 1, 8, 32, 11, 3, 94, 82};
        // 构造一个小顶堆
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        int K = 3;
        int data;
        for (int i = 0; i < datas.length; i++) { // N
            data = datas[i];

            // 先使用 K 个元素构造小顶堆
            if (binaryHeap.size() < K) binaryHeap.add(data); // logK
                // 当遍历到的 data 大于堆顶元素时直接执行 replace
                // 这样当遍历完 datas 后，小顶堆中的元素就是最大的那 K 个元素
                // else if 不需要换成 else（即 data >= binaryHeap.get()），因为堆顶元素符合条件不需要取代
            else if (data > binaryHeap.get()) binaryHeap.replace(data); // logK
        } // 时间复杂度：O(NlogK)

        BinaryTrees.println(binaryHeap);
    }
}
