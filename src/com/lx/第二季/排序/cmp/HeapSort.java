package com.lx.第二季.排序.cmp;

public class HeapSort<E extends Comparable<E>> extends Sort<E> {
    private int heapSize; // 记录堆的大小

    /**
     * 堆排序(不稳定排序)(原地排序)
     * 最好: O(NlogN): 每次迭代的下滤操作仍是 O(logN)
     * 平均: O(NlogN)
     * 最坏: O(NlogN)
     *
     */
    @Override
    protected void sort() {
        this.heapSize = this.array.length;

        this.downTopHeapify(); // 首先对 array 进行原地建堆

        while (this.heapSize >= 2) {
            this.swap(0, --heapSize); // 交换堆顶元素和堆尾元素(并更新 heapSize)
            this.shiftDown(0); // 对堆顶位置进行下滤操作(O(logN))
        }
    }

    /**
     * 自下而上的下滤建堆
     */
    private void downTopHeapify() {
        // 从最后非叶子结点开始下滤
        for (int i = (this.heapSize >> 1) - 1; i >= 0; i--)
            this.shiftDown(i);
    }

    /**
     * index 位置上的元素下滤
     *
     * @param index 索引
     */
    private void shiftDown(int index) {
        E element = this.array[index]; // 获取待下滤的元素

        int lastNotLeafIndex = (this.heapSize >> 1) - 1; // 获取最后一个非叶子结点的索引(n/2-1)
        int childIndex;
        E childElement;

        // 开始迭代(直到叶子结点停止迭代)
        while (index <= lastNotLeafIndex) {
            childIndex = (index << 1) + 1; // 获取左子结点的索引(2*i+1)(非叶子结点必有左子结点)
            childElement = this.array[childIndex];

            // 对比右子结点找出较大者
            int rightIndex = childIndex + 1;
            if (rightIndex <= this.heapSize - 1
                    && this.cmp(this.array[rightIndex], childElement) > 0)
                childElement = this.array[childIndex = rightIndex];

            if (this.cmp(childElement, element) <= 0) break;

            // 继续迭代
            this.array[index] = childElement;
            index = childIndex;
        }

        this.array[index] = element; // 确定元素的最终位置并赋值
    }
}
