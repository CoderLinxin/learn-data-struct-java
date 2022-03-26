package com.lx.第二季.排序;

import com.lx.第二季.排序.cmp.*;
import com.lx.第二季.排序.tools.Asserts;
import com.lx.第二季.排序.tools.Integers;

import java.util.Arrays;

public class Main {
    public static void test(Integer[] array, Sort... sorts) {
        for (Sort sort : sorts) {
            Integer[] newArray = Integers.copy(array);
            sort.sort(newArray);
            Asserts.test(Integers.isAscOrder(newArray));
        }

        Arrays.sort(sorts);

        for (Sort sort : sorts)
            System.out.println(sort);
    }

    public static void main(String[] args) {
        Integer[] array = Integers.random(10000, 1, 10000);
//        test(array,
////                new BubbleSort1<Integer>(),
////                new BubbleSort2<Integer>(),
//                new BubbleSort3<Integer>(),
//                new SelectionSort<Integer>(),
//                new HeapSort<Integer>(),
//                new InsertionSort1<Integer>(),
//                new InsertionSort2<Integer>(),
//                new InsertionSort3<Integer>()
//        );

//        int[] datas = {2, 3, 5, 8, 10, 15};
//        Asserts.test(new BinarySearch().indexOf(datas, 2) == 0);
//        Asserts.test(new BinarySearch().indexOf(datas, 15) == 5);
//        Asserts.test(new BinarySearch().indexOf(datas, 16) == -1);

        int[] data = {1, 2, 3, 4, 5};
        int i = 2;

        data[i++] = data[i - 1];
        for (int j = 0; j < data.length; j++)
            System.out.println(data[j]);
    }
}
