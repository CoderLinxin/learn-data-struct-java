package com.lx.第二季.排序.cmp;

public class MergeSort<E extends Comparable<E>> extends Sort<E> {
    private E[] leftArray; // 用于存放归并排序过程中备份的子序列

    /**
     * 归并排序(稳定排序)(非原地排序)
     * <p>
     * 最好: O(NlogN)
     * 平均: O(NlogN)
     * 最坏: O(NlogN)
     */
    @Override
    protected void sort() {
        // 初始化用于备份子序列的数组大小(所有合并操作统一使用 array.length/2 的大小即可，
        // 就不用在合并过程中根据不同的子序列频繁地创建各种大小的备份空间)
        // 这个操作决定了合并排序算法不是一个稳定的排序算法
        this.leftArray = (E[]) new Comparable[this.array.length >> 1];

        // 对 [0, array.length) 的序列进行归并排序
        this.sort(0, this.array.length);
    }

    /**
     * 对 [begin, end) 范围内的子序列进行归并排序
     *
     * @param begin 子序列起始区间索引(包含)
     * @param end   子序列尾区间索引(不包含)
     */
    protected void sort(int begin, int end) {
        if ((end - begin) <= 1) return; // 序列长度只有 1，则不需要归并

        int mid = (begin + end) >> 1;

        /* divide: 将 [begin, end) 划分为 [begin, mid) 和 [mid, end) 两个子序列 */
        this.sort(begin, mid); // 对 [begin, mid) 的子序列进行归并排序
        this.sort(mid, end); // 对 [mid, end) 的子序列进行归并排序

        /* merge: 对 [begin, mid) 和 [mid, end) 的子序列进行合并操作 */
        merge(begin, mid, end); // O(N)
    }

    /**
     * 对 [begin, mid) 和 [mid, end) 的子序列进行合并操作
     * <p>
     * <p>                 begin        mid         end
     * li          le       ai          ri          re
     * E1 E2 E3 E4          E1 E2 E3 E4 E5 E6 E7 E8
     * 备份出来的子序列                 源序列
     * leftArray                        rightArray
     */
    private void merge(int begin, int mid, int end) {
        int li = 0, le = mid - begin; // 初始化左子序列指针 [li, le)
        int ri = mid, re = end; // 初始化右子序列指针 [ri, re)
        int ai = begin; // 初始化工作指针

        // 备份左子序列
        for (int i = 0; i < le; i++)
            this.leftArray[i] = this.array[begin + i];

        // 进行合并操作(无论左边先结束还是右边先结束，结束迭代的条件都是左边结束)
        while (li < le) {
            // 右边先结束(ri == re), 也直接将左边搬到右边
            // 当 leftArray[i] == array[i] 时为了保证排序是稳定的，可以直接跳过,即 li++,ai++即可
            // 或者直接复用 leftArray[i] < array[i] 时的逻辑也一样
            if (ri == re || this.cmp(this.leftArray[li], this.array[ri]) <= 0)
                this.array[ai++] = this.leftArray[li++];
             else
                this.array[ai++] = this.array[ri++]; // 注意这里的右操作数是 array[ri]
        }
    }
}
