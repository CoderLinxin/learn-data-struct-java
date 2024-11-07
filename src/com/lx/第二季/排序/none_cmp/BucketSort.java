package com.lx.第二季.排序.none_cmp;

import com.lx.第二季.排序.cmp.Sort;

import java.util.LinkedList;
import java.util.List;

public class BucketSort extends Sort<Double> {
    /**
     * 桶排序(稳定排序)(非原地排序)
     * 最好:O(n+k) k = NlogN - Nlogm
     * 平均:O(n+k) k = NlogN - Nlogm
     * 最坏:O(n+k) k = NlogN - Nlogm
     * 空间复杂度:O(m+N) m 为桶数量
     * <p>
     * 由于桶排序针对不同的数据类型应设置不同的分配规则，本案例使用的待排序数据为
     * {0.34,0.47,0.29,0.84,0.46,0.38,0.35,0.76} // 元素范围都在 [0, 1)
     */
    @Override
    protected void sort() {
        // 1.准备 array.length个桶数组(这个数值是随意的,之后只要能根保证元素都能分配到桶中即可),由于每个桶不知道要装多少元素，因此后续每个桶定义成链表
        List<Double>[] buckets = new List[this.array.length];

        // 2.根据制定的规则将元素全部分配到桶中
        for (int i = 0; i < this.array.length; i++) {
            // 由于元素 ∈ [0,1),所以 [0,1)*array.length = [0, array.length) 恰好所有元素都可以分配到桶中
            int bucketIndex = (int) (this.array[i] * this.array.length);

            if (buckets[bucketIndex] == null)
                buckets[bucketIndex] = new LinkedList<>();

            buckets[bucketIndex].add(this.array[i]);
        }

        int index = 0;

        // 由于本案例中桶间元素是有序的,只需间桶内元素排好序接口,所以仅需一趟分配，一趟收集即可确保序列有序
        for (int i = 0; i < buckets.length; i++) {
            // 桶中无元素则继续下次循环
            if (buckets[i] == null) continue;

            // 3.对桶中的数据进行排序(假设其内部使用快速排序)
            buckets[i].sort(null);

            // 4.将桶中的元素收集起来
            for (int j = 0; j < buckets[i].size(); j++)
                this.array[index++] = buckets[i].get(j);
        }
    }
}
