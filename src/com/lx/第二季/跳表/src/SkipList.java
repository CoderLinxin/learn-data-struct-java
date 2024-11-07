package com.lx.第二季.跳表.src;

import java.util.Comparator;

/**
 * 实现一个跳表,每个结点用于存储 key-value
 *
 * @param <K> 键类型:必须具备可比较性
 * @param <V> 值类型
 */
public class SkipList<K, V> {
    private final Comparator<K> comparator;
    private final Node<K, V> head; // 跳表的头指针(仅用于定位该跳表所有首结点，不存储数据)
    private int size; // 总结点数量
    private int layers; // 跳表的有效层数
    private static final int MAX_LAYERS = 32; // 模仿 Redis 中设置的跳表最大层数
    private static final float P = 0.25f; // 新增一层的概率 p

    public SkipList() {
        this(null);
    }

    public SkipList(Comparator<K> comparator) {
        this.comparator = comparator;
        this.head = new Node<>(null, null, MAX_LAYERS);
    }

    /**
     * 获取总结点数量
     *
     * @return 总结点数量
     */
    public int size() {
        return this.size;
    }

    /**
     * 跳表是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * 添加一个元素到跳表中
     *
     * @param key   待添加的键
     * @param value 待添加的值
     * @return key 对应的旧值
     */
    public V put(K key, V value) {
        this.keyNotNullCheck(key);

        Node<K, V> workNode = this.head; // 记录遍历过程中的工作指针
        // 记录待插入结点在所有线路上的前驱结点(当前有效线路有 n 条必然能找到 n 个前驱)
        Node<K, V>[] pres = new Node[this.layers];

        // 优先从最高速线路开始查找
        for (int i = this.layers - 1; i >= 0; i--) { // i 用于标识某条线路
            int cmp = -1;

            // 由于可能需要进行回溯，因此先与下一个结点的 key 进行比较
            while (workNode.nexts[i] != null &&
                    (cmp = this.compare(workNode.nexts[i].key, key)) < 0) {
                // 下一结点的 key 比待插入的 key 小则继续往下遍历
                workNode = workNode.nexts[i];
            }

            // 更新结点
            if (cmp == 0) {
                V oldValue = workNode.nexts[i].value;
                workNode.nexts[i].value = value;
                return oldValue;
            }

            // 待插入结点在当前线路上查找不到则记录其前驱结点
            pres[i] = workNode;
        }

        /* 插入结点 */

        int newLayers = this.getNewLayers(); // 获取新结点的层数
        Node<K, V> newNode = new Node<>(key, value, newLayers); // 创建结点

        // 维护新结点的 nexts 和前驱结点的 nexts
        // newLayers 比 this.layers 小或等于，则仅更新 newLayers 的有效层
        // newLayers 比 this.layers 大，则也可以更新多出来的新层数
        for (int i = 0; i <= newLayers - 1; i++) {
            if (i <= this.layers - 1) {
                // 维护现有的层(线路)(插入结点)
                newNode.nexts[i] = pres[i].nexts[i];
                pres[i].nexts[i] = newNode;
            } else {
                // 维护新层(线路)
                this.head.nexts[i] = newNode;
            }
        }

        this.layers = Math.max(this.layers, newLayers); // 更新跳表的有效层数
        this.size++;

        return null;
    }

    /**
     * 获取新结点的层数
     *
     * @return 新层数
     */
    private int getNewLayers() {
        int layers = 1;

        // 每次根据概率 P 试图增加层数
        while (Math.random() <= P && layers < MAX_LAYERS)
            layers++;

        return layers;
    }

    /**
     * 根据键获取对应的值
     *
     * @param key 用于查询的键
     * @return key 对应的值
     */
    public V get(K key) {
        this.keyNotNullCheck(key);

        Node<K, V> workNode = this.head; // 记录遍历过程中的工作指针

        // 优先从最高速线路开始查找
        for (int i = this.layers - 1; i >= 0; i--) { // i 用于标识某条线路
            int cmp = -1; // 记录本条线路的查找结果

            // 由于可能需要进行回溯，因此先与下一个结点的 key 进行比较(而不要急着将下一个结点的指针赋值给 workNode)
            while (workNode.nexts[i] != null &&
                    (cmp = this.compare(workNode.nexts[i].key, key)) < 0) {
                // 下一个结点的 key 比待查询的 key 小则继续往下遍历
                workNode = workNode.nexts[i];
            }

            // workNode.nexts[i].key > key:换乘线路继续向下查找(继续执行 for 循环)
            // workNode.nexts[i].key == key:找到对应结点
            if (cmp == 0)
                return workNode.nexts[i].value;
        }

        return null;
    }

    /**
     * 根据键删除对应的结点
     *
     * @param key 待查询的键
     * @return 被删除的结点中的值
     */
    public V remove(K key) {
        this.keyNotNullCheck(key);

        Node<K, V> workNode = this.head;
        ; // 记录遍历过程中的工作指针
        Node<K, V> removeNode = null; // 记录被删除的结点

        // 优先从最高速线路开始查找
        for (int i = this.layers - 1; i >= 0; i--) { // i 用于标识某条线路
            int cmp = -1; // 记录本条线路的查找结果

            // 由于可能需要进行回溯，因此先与下一个结点的 key 进行比较
            while (workNode.nexts[i] != null &&
                    (cmp = this.compare(workNode.nexts[i].key, key)) < 0) {
                // 下一个结点的 key 比待查询的 key 小则继续往下遍历
                workNode = workNode.nexts[i];
            }

            // 在某一线路上查找到待删除结点，此时的 workNode 就是待删除结点的前驱结点
            if (cmp == 0) {
                if (removeNode == null) removeNode = workNode.nexts[i]; // 保存待删除结点

                // 前驱指向后继(删除当前线路上的结点)
                workNode.nexts[i] = removeNode.nexts[i];
            }
        }

        if (removeNode == null) return null; // 待删除结点不存在

        // 如果被删除结点清除后导致有效层数减少则需要清除高层的连线(实际上不用清除仅需更新 this.layers)
        for (int i = removeNode.nexts.length - 1; i >= 0; i--) { // 从较高层开始遍历
            if (this.head.nexts[i] != null) break;
            this.layers--; // this.head.nexts[i] == null 说明此层因删除结点而变成了空层
        }

        this.size--;

        return removeNode.value;
    }

    /**
     * 用于比较两个键的大小
     *
     * @param k1 键 1
     * @param k2 键 2
     * @return <0:键1<键2, >0:键1>键2, ==0:键1==键2
     */
    private int compare(K k1, K k2) {
        // 优先使用 comparator，其次 k1、k2 必须具备可比较性
        return this.comparator != null
                ? this.comparator.compare(k1, k2)
                : ((Comparable<K>) k1).compareTo(k2);
    }

    private void keyNotNullCheck(K key) {
        if (key == null)
            throw new IllegalArgumentException("key must not be null~");
    }

    /**
     * 结点类
     *
     * @param <K> 键
     * @param <V> 值
     */
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V>[] nexts; // 指向下一结点的 nexts 指针数组(索引值从高-低分别对应高速-低速线路)

        /**
         * 创建一个新结点
         *
         * @param key    结点的 key
         * @param value  结点的 value
         * @param layers 结点的层数
         */
        public Node(K key, V value, int layers) {
            this.key = key;
            this.value = value;
            this.nexts = new Node[layers];
        }

        @Override
        public String toString() {
            return key + ":" + value + "_" + nexts.length;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("一共" + this.layers + "层").append("\n");
        for (int i = this.layers - 1; i >= 0; i--) {
            Node<K, V> node = this.head;
            while (node.nexts[i] != null) {
                sb.append(node.nexts[i]);
                sb.append(" ");
                node = node.nexts[i];
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
