package com.lx.哈希表.src;

import com.lx.映射.src.Map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 实现一个哈希表(冲突解决使用链地址法且使用红黑树挂载冲突的结点)
 *
 * @param <K> 键
 * @param <V> 值
 */
public class HashMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private static final int DEFAULT_CAPACITY = 1 << 4; // 数组默认容量(2^n，这里取 4)
    private Node<K, V>[] table; // 承载哈希表的数组(每个数组元素是一棵红黑树的根结点)
    private int size; // 哈希表总元素个数(包括所有冲突和非冲突的元素)
    private static final float LOADING_FACTOR = 0.75f; // 哈希表扩容时的装填因子

    public HashMap() {
        this.table = new Node[DEFAULT_CAPACITY];
    }

    /**
     * 获取哈希表的总元素数
     *
     * @return 总元素个数
     */
    public int size() {
        return this.size;
    }

    /**
     * 哈希表是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * 清空哈希表
     */
    public void clear() {
        if (this.size == 0) return;
        this.size = 0;

        for (int i = 0; i < this.table.length; i++)
            this.table[i] = null;
    }

    /**
     * 向哈希表中添加映射
     * 哈希表存储的结点(这里存储的是映射)不具备可比较性，但是由于使用的是红黑树实现哈希表。
     * 红黑树插入，查找和删除结点时根据的是结点的可比较性
     * 因此我们在查找过程中通过任何可比较的途径(制定一系列比较规则)(因为这样能够充分发挥二叉搜索树的性能)
     * 来确定目标结点的方向(如何继续走下去)(否则一上来就直接扫描会浪费很多性能),而通过 equals 是否等于 0，判断是否是目标结点
     * 最后实在不得已(连扫描都扫描不到时)的比较策略是,选择计算内存地址差值的方式来得到插入结点的左右子树方向
     * (这样至少有依据能够添加该结点到红黑树上，其实只要能挂到树上就行了，例如让 cmp 一直为 1，一直往右放也行，
     * 甚至随便搞个不等于 0 的随机数都行，因为 findNode 搜索时也不会按照最后的这条规则走(条件太苛刻)，而是会进行扫描。
     * 但还是建议按照一定的规则确定方向(有时比较好调试。))
     *
     * @param k1    添加映射的键
     * @param value 值
     * @return 旧映射的值(旧映射不存在则返回 null)
     */
    public V put(K k1, V value) {
        this.resize(); // 添加结点前判断是否需要扩容

        int index = this.getIndex(k1); // 计算 key 对应的索引

        Node<K, V> root = this.table[index]; // index 对应的 table 中的红黑树根结点

        if (root == null) { // 没有产生哈希冲突
            // 种一棵红黑树(这里添加的是红黑树的根结点，因为红黑树对象多出来的一些成员变量对于哈希表并没有用)
            table[index] = root = this.createNode(k1, value, null);
            this.size++;
            fixAfterPut(root); // 修复红黑树性质
            return null;
        }

        /* 产生哈希冲突(需要查找红黑树，并将映射的结点挂载/更新到红黑树上) */

        int h1 = this.getHash(k1);
        int cmp = 0; // 存储比较结果
        Node<K, V> node = root;
        Node<K, V> parent = root; // 记录遍历过程的父节点
        K k2;
        int h2;
        boolean searched = false; // 标记是否已经扫描完整棵树

        while (node != null) {
            parent = node;
            k2 = node.key;
            h2 = node.hash;

            /* 先比较哈希值(确定方向) */
            if (h1 > h2) cmp = 1;
            else if (h1 < h2) cmp = -1;

                /* 哈希值相同(无法确定接下来的方向)则判断是否 equals */
            else if (Objects.equals(k1, k2)) { // 相等则更新结点
                cmp = 0;
            } else if (k1 != null && k2 != null // 是否为可比较对象(第二种方式确定方向)
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) { // compareTo 的结果为 0 说明无法确定方向
            } else if (searched) { // 扫描完整棵树后仍未发现目标结点说明需要插入新结点，接下来一直前往内存地址差值得出的方向即可
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            } else {
                Node<K, V> target = null;
                // 上述途径都无法比较成功时(已经不知道要往哪个方向查找),对根结点左右子树进行扫描
                if (node.left != null && (target = this.findNode(node.left, k1)) != null
                        || node.right != null && (target = this.findNode(node.right, k1)) != null) {
                    node = target; // 发现存在 key 对应的结点
                    cmp = 0; // 标记为此次操作为覆盖操作
                } else { // 扫描失败，红黑树上找不到该结点，表明本次操作是插入新结点
                    // 通过内存地址计算插入结点的左右子树方向(通过这种方式插入的结点以后一般都需要通过扫描的方式才能查找到(在没有重新散列的情况下))
                    // 内存地址都是正的，相减必然不会溢出
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                    searched = true;
                }
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 更新结点
                V oldValue = node.value;
                node.key = k1;
                node.value = value;
                return oldValue;
            }
        }

        // 插入新结点的情况
        Node<K, V> newNode = this.createNode(k1, value, parent);
        if (cmp < 0) parent.left = newNode;
        else parent.right = newNode;
        this.size++;

        fixAfterPut(newNode); // 修复红黑树性质
        return null;
    }


    /**
     * 创建结点(可被子类重写)
     *
     * @param key    键
     * @param value  值
     * @param parent 父结点指针
     * @return 创建的结点
     */
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    private void resize() {
        if (((this.size + 0.0) / this.table.length) <= LOADING_FACTOR) return; // 判断是否符合扩容界限

        Node<K, V>[] oldTable = this.table; // 保存旧的 table 准备搬迁
        this.table = new Node[this.table.length << 1]; // 对 table 进行扩容

        Queue<Node<K, V>> queue = new LinkedList<>();
        Node<K, V> node;

        // 遍历整个 table
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] == null) continue;
            queue.offer(oldTable[i]);

            // 每个桶的红黑树搬家
            while (!queue.isEmpty()) {
                node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);

                this.moveNode(node); // 每个结点搬家
            }
        }
    }

    /**
     * 将传递进来的旧哈希表中的结点移动到当前新的 table 中
     *
     * @param node 待移动的结点
     */
    private void moveNode(Node<K, V> node) {
        this.reset(node); // 重置结点
        int index = this.getIndex(node); // 重新计算索引
        Node<K, V> root = this.table[index]; // 获取对应桶中的红黑树根结点

        /* 未产生哈希冲突 */

        if (root == null) {
            table[index] = node; // 直接成为根结点
            fixAfterPut(node); // 修复红黑树性质
            return;
        }

        /* 产生哈希冲突 */

        K k1 = node.key;
        int h1 = node.hash;
        K k2;
        int h2;
        int cmp = 0; // 存储比较结果
        Node<K, V> traverNode = root; // 用于遍历的结点
        Node<K, V> parent = root; // 记录遍历过程的父节点

        // 由于新 table 必不可能存在旧 table 中还未搬迁过来的结点(equals 必不等于 true，因此不必考虑 equals 的情况)
        while (traverNode != null) {
            parent = traverNode;
            k2 = traverNode.key;
            h2 = traverNode.hash;

            // 比较哈希值(确定方向)
            if (h1 > h2) cmp = 1;
            else if (h1 < h2) cmp = -1;
            else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) ;
            else cmp = System.identityHashCode(k1) - System.identityHashCode(k2);

            // 继续向下查找
            if (cmp > 0) traverNode = traverNode.right;
            else traverNode = traverNode.left;
        }

        // 插入结点
        if (cmp < 0) parent.left = node;
        else parent.right = node;
        node.parent = parent;
        fixAfterPut(node); // 修复红黑树性质
    }

    /**
     * 对结点进行重置
     *
     * @param node 待重置的结点
     */
    private void reset(Node<K, V> node) {
        node.left = null;
        node.parent = null;
        node.right = null;
        node.color = RED;
    }

    /**
     * 添加结点后修复红黑树性质
     *
     * @param node 添加的结点
     */
    private void fixAfterPut(Node<K, V> node) { // 添加映射后修复红黑树性质
        Node<K, V> parent = node.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;

        // 叔父节点
        Node<K, V> uncle = parent.sibling();
        // 祖父节点
        Node<K, V> grand = red(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
            black(parent);
            black(uncle);
            // 把祖父节点当做是新添加的节点
            fixAfterPut(grand);
            return;
        }

        // 叔父节点不是红色
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        // 让parent成为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { // grand 为根结点
            // 更新(桶数组中)红黑树的根结点(实际上由于 grand、parent、child 都在同一棵树上，
            // 因此它们的 key 对应的哈希值计算出来的桶索引都是一样的，用哪个都能在 table 中索引到根结点)
            this.table[this.getIndex(grand)] = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;
    }

    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * 对 key 的 hash 进行扰动计算
     *
     * @param key 键
     * @return 扰动计算后的 hash
     */
    private int getHash(K key) {
        if (key == null) return 0; // key 为 null 的映射统一放到索引为 0 处
        int hash = key.hashCode();
        return (hash ^ (hash >>> 16)); // 重新对高 16 位和低 16 位混合计算哈希值(高16位与 00..00 异或结果仍为高16位原本数值不变)
    }

    /**
     * 根据 key 的哈希值(经过了扰动计算)计算出索引
     *
     * @param key 键
     * @return 索引
     */
    private int getIndex(K key) {
        return getHash(key) & (this.table.length - 1);
    }

    /**
     * 根据已挂载结点中 key 对应的 hashCode 计算出索引
     *
     * @param node 已挂载到红黑树上的结点
     * @return 索引
     */
    private int getIndex(Node<K, V> node) {
        return node.hash & (this.table.length - 1); // 根据哈希值计算得到索引
    }

    /**
     * 根据 key 获取对应的 value
     *
     * @param key 键
     * @return 键对应的 value
     */
    public V get(K key) {
        Node<K, V> node = this.findNode(key);
        return node != null ? node.value : null;
    }

    /**
     * 根据 key 删除对应的结点(映射
     * )
     *
     * @param key 键
     * @return 删除的 key 对应的 value
     */
    public V remove(K key) {
        return this.remove(this.findNode(key));
    }

    /**
     * 根据传入的结点删除该结点
     *
     * @param node 要删除的结点
     * @return 返回删除的结点中的 value
     */
    private V remove(Node<K, V> node) {
        if (node == null) return null;

        size--;

        V oldValue = node.value;
        Node<K, V> willRemoveNode = node;
        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<K, V> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.hash = s.hash;
            node.key = s.key;
            node.value = s.value;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = this.getIndex(node); // 获取 node 所在红黑树的桶数组索引
        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                this.table[index] = replacement; // 更新红黑树的根结点
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            // 删除节点之后的处理
            fixAfterRemove(replacement);
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            this.table[index] = null; // 更新红黑树的根结点
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }

            fixAfterRemove(node); // 删除结点后修复红黑树性质
            afterRemove(willRemoveNode, node); // 删除结点后的一些处理
        }

        return oldValue;
    }

    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（right.left.left.left....）
        Node<K, V> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    /**
     * 删除结点后的一些处理
     *
     * @param willRemoveNode 想要删除的结点
     * @param removeNode     实际被删除的结点
     */
    protected void afterRemove(Node<K, V> willRemoveNode, Node<K, V> removeNode) {
    }

    /**
     * 删除结点后修复红黑树性质
     *
     * @param node 实际被删除的结点或替代结点
     */
    private void fixAfterRemove(Node<K, V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        if (parent == null) return;

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    fixAfterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    fixAfterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    /**
     * 判断哈希表中是否包含 key
     *
     * @param key 待判断的 key
     * @return 是否包含 key
     */
    public boolean containsKey(K key) {
        return this.findNode(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (this.size == 0) return false;

        Node<K, V> node = null; // 用于遍历的结点
        Queue<Node<K, V>> queue = new LinkedList<>();

        // 因为 value 不能比较，因此只能遍历整个哈希表
        for (int i = 0; i < this.table.length; i++) { // 遍历桶数组
            node = this.table[i];
            if (node == null) continue;

            queue.offer(node);
            // 遍历每个桶对应的红黑树
            while (!queue.isEmpty()) {
                node = queue.poll();
                if (Objects.equals(node.value, value)) return true;

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }

        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (this.size == 0 || visitor == null) return;

        Node<K, V> node = null; // 用于遍历的结点
        Queue<Node<K, V>> queue = new LinkedList<>();

        for (int i = 0; i < this.table.length; i++) { // 遍历桶数组
            node = this.table[i];
            if (node == null) continue;

            queue.offer(node);
            // 层序遍历每个桶对应的红黑树
            while (!queue.isEmpty()) {
                node = queue.poll();
                if (visitor.visit(node.key, node.value)) return;

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
    }

    /**
     * 根据 key 找到对应的(映射)结点
     *
     * @param key 键
     * @return 键对应的结点
     */
    private Node<K, V> findNode(K key) {
        int index = this.getIndex(key); // 获取对应的桶数组索引
        Node<K, V> root = this.table[index]; // 获取目标桶中红黑树的根结点

        return root != null ? this.findNode(root, key) : null; // 开始查找
    }

    /**
     * 在以 node 为根结点的红黑树上查找 k1 所在的结点
     * 在不得已情况下使用扫描的方式查找结点(由于哈希表元素数量较多时会进行扩容优化，
     * 每个桶的红黑树的结点数量不超过 10 个，因此递归扫描也不会消耗太多性能)
     *
     * @param node 子树的根结点
     * @param k1   待查找结点的 key
     * @return 查找到的目标结点
     */
    private Node<K, V> findNode(Node<K, V> node, K k1) {
        int h1 = this.getHash(k1); // null 的哈希值默认设为 0

        // 开始查找
        K k2;
        int h2;
        int cmp;
        while (node != null) {
            k2 = node.key;
            h2 = node.hash;

            /* 比较哈希值(不要使用减法，因为可能会溢出):哈希值不同,对应的 key 必然不同(想想逆否命题)，直接跳过当前结点继续向下遍历 */
            if (h1 > h2) node = node.right;
            else if (h1 < h2) node = node.left;

                /* 哈希值相等(key 仍然可能不同)则判断是否 equals */
            else if (Objects.equals(k2, k1)) return node;

                /* 是否为可比较对象:继续试探方向 */
            else if (k2 != null && k1 != null
                    && k2.getClass() == k1.getClass()
                    && k2 instanceof Comparable
                    && (cmp = ((Comparable) k2).compareTo(k1)) != 0) { // compareTo 为 0 说明无法确定方向
                node = cmp > 0 ? node.right : node.left;
            } else {
                /* 哈希值相等
                k1 k2 类型不同
                k1 k2 类型相同但不具有可比较性
                k1，k2 其中一个为空
                此时已经没有足够的依据应该往那边查找，最好的办法就是对左右子树进行扫描(这样可以避免漏掉每一个可能的结点)
                这里不能通过比较 key 的内存地址来查找(除非能拿到与添加该结点时所使用的相同内存地址的 key，但是这种情况在开发中特别少见) */

                Node<K, V> target = null;
                if (node.left != null && (target = this.findNode(node.left, k1)) != null) { // 向左子树扫描
                    return target;
                } else node = node.right; // 向右子树扫描
            }
        }
        return null;
    }

    /**
     * 红黑树结点(每个映射对应一个红黑树结点)
     *
     * @param <K> 可比较的键
     * @param <V> 值
     */
    protected static class Node<K, V> {
        // key 对应的 hashCode (放在这里做到一个缓存的作用，不用每次比较 key 旧计算一个 hashCode)
        // 只要映射第一次被添加到哈希表上(生成结点)后，hashCode 就会被缓存起来
        int hash;
        K key;
        V value;
        boolean color = RED; // 默认添加结点为红色
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            int hashCode = key == null ? 0 : key.hashCode(); // key 为 null，哈希值固定设为 0
            this.hash = hashCode ^ (hashCode >>> 16); // 进行扰动计算后并缓存(高低16位混合运算)
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }
    }
}
