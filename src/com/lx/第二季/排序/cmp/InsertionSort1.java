package com.lx.第二季.排序.cmp;

public class InsertionSort1<E extends Comparable<E>> extends Sort<E> {
    /**
     * 插入排序(稳定排序)(原地排序)
     * 最好: O(N): 序列完全有序时仅迭代一次
     * 平均: O(N^2)
     * 最坏: O(N^2)
     *
     */
    @Override
    protected void sort() {
        for (int begin = 1; begin <= this.array.length - 1; begin++) { // 从索引 1 开始调整元素
            // 每轮插入排序从 begin 开始向前迭代
            for (int traverIndex = begin; traverIndex >= 1; traverIndex--) {
                // 通过交换元素位置逐步将待插入元素移动到最终位置(为了实现稳定排序，这里的比较条件不包含 =)
                if (this.cmp(traverIndex, traverIndex - 1) < 0)
                    swap(traverIndex, traverIndex - 1);
                else{ // 由于前面序列已经有序(这里相当于比前面有序序列的最大值还大)，因此直接 break
                    break;
                }
            }
        }
    }
}












