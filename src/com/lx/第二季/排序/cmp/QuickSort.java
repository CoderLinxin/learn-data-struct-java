package com.lx.第二季.排序.cmp;

public class QuickSort<E extends Comparable<E>> extends Sort<E> {
    /**
     * 快速排序(不稳定排序)(非原地排序)
     * <p>
     * 最好: O(NlogN)
     * 平均: O(NlogN)
     * 最坏: O(N^2)
     */
    @Override
    protected void sort() {
        this.sort(0, this.array.length); // 对 [0, array.length) 中的元素进行快速排序
    }

    /**
     * 对 [begin, end) 中的元素进行快速排序
     *
     * @param begin 左区间端点(包含)
     * @param end   右区间端点(不包含)
     */
    private void sort(int begin, int end) {
        if (end - begin <= 1) return; // 只有一个元素的序列不需要快速排序

        // 获取 [begin, end) 序列的轴点元素索引
        int pivot = this.getPivotIndex(begin, end); // O(N)

        this.sort(begin, pivot); // 继续对 [begin, pivot) 中的元素进行一趟快排
        this.sort(pivot + 1, end); // 继续对 [pivot+1, end) 中的元素进行一趟快排
    }

    /**
     * 默认选取 begin 位置上的元素作为轴点元素
     * 调整 [begin, end) 序列并将轴点元素放入正确位置上并返回轴点元素的最终位置
     *
     * @param begin 左区间端点(包含)
     * @param end   右区间端点(不包含)
     * @return 轴点元素的正确索引
     */
    private int getPivotIndex(int begin, int end) {
        // 随机选取轴点元素:将随机选取的轴点元素与 begin 位置上的元素进行交换即可
        this.swap(begin, begin + (int) (Math.random() * (end - begin))); // [begin, begin + random[0, 1) * (end - begin)) = [begin, random[begin, end))

        // 首先备份被选取为轴点的元素
        E pivotElement = this.array[begin];
        end--; // end 指针指向最右边的有效元素

        // 调整序列以便确定轴点元素的最终位置
        while (begin < end) {
            // 首先从右边开始调整
            while (begin < end) {
                if (this.cmp(this.array[end], pivotElement) <= 0) { // array[end] <= pivotElement
                    this.array[begin++] = this.array[end];
                    break; // 调换方向
                } else end--;
            }

            // 开始调整左边
            while (begin < end) {
                if (this.cmp(this.array[begin], pivotElement) >= 0) { // array[begin] >= pivotElement
                    this.array[end--] = this.array[begin];
                    break; // 调换方向
                } else begin++;
            }
        }

        /* 由于上面 begin、end 交替加减 1, 因此在此处 begin 必然等于 end,
           begin/end 即轴点元素的正确位置, 随意返回一个即可 */

        this.array[begin] = pivotElement; // 轴点元素放入正确位置
        return begin;  // 返回轴点元素的正确位置
    }
}
