package com.lx.第二季.贪心.src;

import java.util.Arrays;

// 使用贪心策略解决最优装载问题
public class OptimalLoading {
    public static void main(String[] args) {
        int[] antiques = new int[]{3, 5, 4, 10, 7, 14, 2, 11}; // 待装载的古董
        Arrays.sort(antiques); // 根据古董重量进行排序

        int count = 0; // 已装载的古董数量
        int loadWeight = 0; // 已装载的古董重量
        int maxCapacity = 30; // 海盗船的最大载货量

        // 尝试装载最多数量的古董
        for (int i = 0; i < antiques.length; i++) {
            int antique = antiques[i];
            int surplusCapacity = maxCapacity - loadWeight - antique;

            if (surplusCapacity >= 0) { // 每次选择重量最小的古董尝试装载
                System.out.println("装载了重量为 " + antiques[i] + " 的古董,剩余容量还有 " + surplusCapacity + "~");
                loadWeight += antique;
                count++;
            } else {
                break;
            }
        }

        System.out.println("装载的古董总数为: " + count);
        System.out.println("装载的古董总重量为: " + loadWeight);
    }
}
