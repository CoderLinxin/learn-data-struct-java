package com.lx.第二季.排序.cmp;

import java.util.ArrayList;
import java.util.List;

public class ShellSort<E extends Comparable<E>> extends Sort<E> {
    /**
     * 希尔排序(非稳定排序)(原地排序)
     * 最好: O(N)
     * 平均: 不确定(取决于选取的步长序列)
     * 最坏: O(N^(4/3) ~ O(N^2)), 分别为 Robert Sedgewick 给出的步长序列和希尔给出的步长序列
     */
    @Override
    protected void sort() {
        List<Integer> shellSequence = this.getShellSequence(); // 获取步长序列

        // 根据步长序列逐个步长进行排序
        for (int i = 0; i < shellSequence.size(); i++)
            this.sort(shellSequence.get(i));
    }

    /**
     * 根据传入的步长对 array 的每列进行插入排序(这里为了方便理解，使用的是最简单的一种插入排序)
     *
     * @param step 步长
     */
    protected void sort(int step) {
        for (int col = 0; col < step; col++) { // 对 [0, step) 所有列进行插入排序

            // 从每列的第二个元素开始进行插入排序(步长为 step)
            int secondRow= col + step;

            for (int begin = secondRow; begin <= this.array.length - 1; begin += step) {

                for (int traverIndex = begin; traverIndex >= secondRow; traverIndex -= step) {
                    if (this.cmp(traverIndex, traverIndex - step) < 0)
                        swap(traverIndex, traverIndex - step);
                    else break;
                }

            }

        }
    }

    /**
     * 根据 array 获取一个(希尔本人给出的)步长序列
     *
     * @return 步长序列 [N/(2^k), N/(2^(k-1)), ... , 4, 2, 1]
     */
    private List<Integer> getShellSequence() {
        List<Integer> shellSequence = new ArrayList<>();

        int n = this.array.length; // 数据规模

        while ((n >>= 1) > 0) // (n/=2) > 0
            shellSequence.add(n);

        return shellSequence;
    }
}
