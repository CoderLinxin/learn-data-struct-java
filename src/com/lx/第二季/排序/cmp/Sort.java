package com.lx.第二季.排序.cmp;

import com.lx.第二季.排序.Student;

import java.text.DecimalFormat;

/**
 * 排序算法统一接口
 *
 * @param <E> 排序的元素类型
 */
public abstract class Sort<E extends Comparable<E>> implements Comparable<Sort<E>> {
    protected E[] array;
    private long time = 0; // 计算排序算法所耗时间
    private int swapCount = 0; // 记录元素交换次数
    private int cmpCount = 0; // 记录比较次数

    /**
     * 暴露给外界的排序接口
     *
     * @param array 待排序的数据
     */
    public void sort(E[] array) {
        // 过滤不需要排序的情况
        if (array == null || array.length <= 1)
            return;

        long start = System.currentTimeMillis();

        // 将待排序数据赋值给 array 以便子类的排序算法对其操作
        this.array = array;
        this.sort(); // 调用子类排序算法对 array 进行排序

        this.time = System.currentTimeMillis() - start;
    }

    /**
     * 交换 array[i1] 和 array[i2]
     *
     * @param i1 索引1
     * @param i2 索引2
     */
    protected void swap(int i1, int i2) {
        this.swapCount++;

        E temp = this.array[i1];
        this.array[i1] = this.array[i2];
        this.array[i2] = temp;
    }

    /**
     * 比较 array[i1] 和 array[i2] 的大小关系
     *
     * @param i1 索引 1
     * @param i2 索引 2
     * @return > 0: array[i1] > array[i2], < 0: array[i1] < array[i2], = 0: array[i1] = array[i2]
     */
    protected int cmp(int i1, int i2) {
        this.cmpCount++;
        return this.array[i1].compareTo(this.array[i2]);
    }

    /**
     * 比较 v1、v2 的大小关系
     *
     * @param v1 数组元素 1
     * @param v2 数组元素 2
     * @return > 0: v1 > v2, < 0: v1 < v2, = 0: v1 = v2
     */
    protected int cmp(E v1, E v2) {
        this.cmpCount++;
        return v1.compareTo(v2);
    }

    /**
     * 用于子类实现的具体的排序算法
     */
    protected abstract void sort();

    // 比较排序算法之间优劣性的接口
    @Override
    public int compareTo(Sort o) {
        long result = this.time - o.time;
        result = result == 0 ? this.swapCount - o.swapCount : result;
        result = result == 0 ? this.cmpCount - o.swapCount : result;
        return (int) result;
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(cmpCount);
        String swapCountStr = "交换：" + numberString(swapCount);
        String stableStr = "稳定性：" + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
                + stableStr + "\t\t"
                + timeStr + " \t\t"
                + compareCountStr + "\t\t "
                + swapCountStr + "\n"
                + "------------------------------------------------------------------";
    }

    // 评价排序算法是否稳定(仅需判断针对 Student 的排序是否稳定即可)
    private boolean isStable() {
        if (this instanceof ShellSort) return false; // 希尔排序稳定性不适用于下面的方法判断(因为希尔排序是逐列排序的)

        Student[] students = new Student[20];

        for (int i = 0; i < students.length; i++) {
            students[i] = new Student();
            students[i].setAge(10);
            students[i].setScore(i);
        }

        this.sort((E[]) students);

        for (int i = 1; i < students.length; i++)
            if (!(students[i - 1].getScore() + 1 == students[i].getScore())) return false;

        return true;
    }

    private DecimalFormat fmt = new DecimalFormat("#.00");

    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }
}
