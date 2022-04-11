package com.lx.第二季.布隆过滤器.src;

/**
 * 布隆过滤器
 *
 * @param <T> 布隆过滤器中存储的元素类型
 */
public class BloomFilter<T> {
    private final int bitSize; // 数据规模(二进制向量的长度)
    long[] bits; // 存放二进制向量,每个数组元素相当于 64 位二进制向量(1 个二进制位的值代表某个元素是否存在)
    long hashSize; // 哈希函数的个数

    /**
     * 布隆过滤器初始化
     *
     * @param n 数据规模
     * @param p 误判率
     */
    public BloomFilter(int n, double p) {
        if (n <= 0 || p <= 0 || p >= 1)
            throw new IllegalArgumentException("n must be >0, p must be 0<p<1");

        double ln2 = Math.log(2); // log 函数用于求 lnx 的对数

        // 根据误判率计算二进制的位数 bitSize: bitSize = -(nlnp/(ln2)^2)
        this.bitSize = -(int) ((n * Math.log(p)) / (ln2 * ln2));
        // 根据误判率哈希函数的个数 hashSize: hashSize = (bitSize*ln2)/n
        this.hashSize = (int) (this.bitSize * ln2 / n);

        // 根据所需的二进制位数计算 long 数组的大小(利用整型除法需向上取整时的技巧)
        this.bits = new long[(this.bitSize + Long.SIZE - 1) / Long.SIZE];
    }

    /**
     * 添加一个元素到布隆过滤器中
     *
     * @param value 待添加的元素
     */
    public void put(T value) {
        nullCheck(value);

        locating(value, (int index) -> {

        });
    }

    /**
     * 判断一个元素是否存在(有一定的误判率)
     *
     * @param value 待判断的元素
     * @return 是否存在
     */
    public boolean contains(T value) {
        nullCheck(value);

        locating(value, (int index) -> {

        });
    }

    /**
     * 根据元素定位其在二进制向量中的索引
     *
     * @param value 待定位的元素
     */
    private void locating(T value, AfterLocated afterLocated) {
        // 利用 value 生成 2 个整数(这里是随意选择的方法不需纠结为什么这么做)
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        // 根据 hash1、hash2 生成 hashSize 个哈希值
        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0) { // 哈希值小于 0 取反即可
                combinedHash = ~combinedHash;
            }

            // 根据哈希值计算出指定二进制位的索引
            int index = combinedHash % this.bitSize;
            afterLocated.afterLocated(index);
        }
    }

    private void nullCheck(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null.");
        }
    }

    /**
     * 定位到指定二进制位后所作的操作
     */
    private interface AfterLocated {
        /**
         * @param index 指定二进制位的索引
         */
        void afterLocated(int index);
    }
}
