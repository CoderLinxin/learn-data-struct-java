package com.lx.第二季.贪心.src;

import java.util.Arrays;

// 使用贪心策略解决零钱兑换问题
public class MoneyChange {
    public static void main(String[] args) {
        Integer[] denominations = new Integer[]{5, 25, 1, 10}; // 拥有的零钱面额
        Arrays.sort(denominations, (Integer d1, Integer d2) -> d2 - d1); // 零钱面额逆序排序

        int sum = 41; // 待找给用户的总金额

        int i = 0;
        int count = 0; // 记录找给客户的硬币数

        // 试图用最少的硬币数给用户
        while (i < denominations.length && sum > 0) {
            int denomination = denominations[i];

            if (denomination <= sum) { // 判断当前硬币面额是否合适
                System.out.println("一张面额为 " + denomination + " 的硬币~");
                sum -= denomination;
                count++;
            } else i++; // 寻找更小面额的硬币
        }

        System.out.println("找给用户的总硬币数为: " + count);
    }
}
