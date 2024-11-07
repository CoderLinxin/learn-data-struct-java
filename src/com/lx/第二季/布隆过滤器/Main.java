package com.lx.第二季.布隆过滤器;

import com.lx.第二季.布隆过滤器.src.BloomFilter;

public class Main {
    public static void main(String[] args) {
        BloomFilter<Integer> bloomFilter = new BloomFilter<>(100_00000, 0.000000001);

        for (int i = 1; i <= 100_00000; i++)
            bloomFilter.put(i);

        int count = 0;
        for (int i = 100_00001; i <= 200_00000; i++)
            if (bloomFilter.contains(i)) count++;
        System.out.println(count);
    }
}
