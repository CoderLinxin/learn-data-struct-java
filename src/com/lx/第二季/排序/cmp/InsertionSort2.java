package com.lx.第二季.排序.cmp;

public class InsertionSort2<E extends Comparable<E>> extends Sort<E> {

    /**
     * 插入排序优化 1: (仍然是暴力查找)寻找待插入元素最终位置的过程中用'挪动'代替'交换'
     * 源数据逆序数越多，使用挪动代替交换的次数就越多，优化就越明显
     * 最好: O(N): 序列完全有序时仅迭代一次
     * 平均: O(N^2)
     * 最坏: O(N^2)
     *
     */
    @Override
    protected void sort() {
        for (int begin = 1; begin <= this.array.length - 1; begin++) {
            // 保存本次迭代中待插入的元素(相当于严蔚敏教材中的哨兵)
            E currentElement = this.array[begin];

            for (int traverIndex = begin; traverIndex >= 1; traverIndex--) {
                if (cmp(currentElement, this.array[traverIndex - 1]) < 0)
                    // 通过挪动元素逐步寻找待插入元素的最终位置
                    this.array[traverIndex] = this.array[traverIndex - 1];
                else {
                    // 寻找到待插入元素的最终位置并进行插入
                    this.array[traverIndex] = currentElement;
                    break;
                };
            }
        }
    }
}
