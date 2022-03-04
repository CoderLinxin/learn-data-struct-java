package com.lx.复杂度.src;

/**
 * 一些基本的复杂度计算
 */
public class Test {
    public static void test1(int n) {
        // 对于 n = 8：    4 2 1 满足条件
        // 对于 n = 16:   8 4 2 1 满足条件

        // 时间复杂度：O(logN)
        // 空间复杂度：O(1)
        while ((n = n / 2) > 0)
            System.out.println("test");
    }

    public static void test2(int n) {
        // 时间复杂度：O(N)
        // 空间复杂度：O(1)
        for (int i = 0; i < n; i++)
            System.out.println("test");
    }

    public static void test3(int n) {
        // 时间复杂度：O(N)
        // 空间夫再度：O(N)
        for (int i = 0, arr[] = new int[n]; i < arr.length; i++)
            System.out.println(arr[i]);
    }

    public static void test4(int n) {
        // 时间复杂度：O(N*logN)
        // 空间复杂度：O(1)
        for (int i = 0; i < n; i = i * 2) { // logN 次
            for (int j = 0; j < n; j++) {
                System.out.println("test");
            }
        }
    }
}