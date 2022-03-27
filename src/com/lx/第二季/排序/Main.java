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
        Integer[] array = Integers.random(20000, 1, 20000);
        test(array,
                new BubbleSort3<Integer>(),
                new SelectionSort<Integer>(),
                new HeapSort<Integer>(),
                new InsertionSort3<Integer>(),
                new MergeSort<Integer>(),
                new QuickSort<Integer>(),
                new ShellSort<Integer>()
        );
    }
}
