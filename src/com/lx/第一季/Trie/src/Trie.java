package com.lx.第一季.Trie.src;

import com.lx.第一季.哈希表.src.HashMap;

/**
 * 实现一个单词查找树(每个单词又可作为 key 对应一个 value)
 *
 * @param <V> 每个单词对应的 value 的类型
 */
public class Trie<V> {
    private int size; // 存储的单词总数
    private final Node<V> root = new Node<>(null, null); // 根结点

    /**
     * 返回存储的单词数
     *
     * @return 存储的单词数
     */
    public int size() {
        return this.size;
    }

    /**
     * 判断单词查找树是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return this.root.getChildren().isEmpty();
    }

    /**
     * 清空单词查找树(仅需清空根结点的所有出度边)
     */
    public void clear() {
        this.size = 0;
        this.root.getChildren().clear();
    }

    /**
     * 根据单词作为 key 获取其对应的 value
     *
     * @param word 作为 key 的单词
     * @return 单词映射的 value
     */
    public V get(String word) {
        Node<V> target = this.findNode(word);
        return target != null && target.isWord ? target.value : null;
    }

    /**
     * 判断单词查找树中是否包含了某个单词
     *
     * @param word 需要判断拿到单词
     * @return 是否包含某单词
     */
    public boolean contains(String word) {
        Node<V> target = this.findNode(word);
        return target != null && target.isWord;
    }

    /**
     * 向单词查找树中添加一个单词
     *
     * @param word  待添加的单词
     * @param value 单词作为 key 时对应的 value(如果有)
     * @return 添加成功所覆盖的旧的 value
     */
    public V add(String word, V value) {
        this.wordCheck(word);

        int len = word.length();
        char c; // 遍历过程中的字符
        Node<V> traverNode = this.root; // 遍历过程中经过的结点
        HashMap<Character, Node<V>> children; // 遍历过程中结点的所有出度边
        Node<V> childNode; // 遍历过程中欲经过的下一个结点

        // 根据单词中的字符进行遍历
        for (int i = 0; i < len; i++) {
            c = word.charAt(i);
            children = traverNode.getChildren();
            childNode = children.get(c);

            if (childNode == null) { // 判断代表当前遍历字符的结点是否存在(不存在则添加)
                childNode = new Node<>(traverNode, c);
                children.put(c, childNode);
            }

            traverNode = childNode; // 继续向下查找
        }

        // 结尾字符结点存在
        if (traverNode.isWord) {
            V oldValue = traverNode.value;
            traverNode.value = value;
            return oldValue;
        }

        // 结尾字符结点不存在
        traverNode.isWord = true;
        traverNode.value = value;
        this.size++;
        return null;
    }

    /**
     * 单词查找树中删除某个单词
     *
     * @param word 待删除的单词
     * @return 删除的单词对应的 value(如果有)
     */
    public V remove(String word) {
        Node<V> tail = this.findNode(word); // 获取单词的最后一个字符结点

        // 单词树中不存在该单词
        if (tail == null || !tail.isWord) return null;

        /* 单词树中存在该单词 */
        V oldValue = tail.value;
        this.size--;

        // tail 存在子字符结点时(说明 word 是某个单词的前缀)
        // 仅需将 tail.isWord 置 false
        if (!tail.getChildren().isEmpty()) {
            tail.isWord = false;
            tail.value = null;
            return oldValue;
        }

        Node<V> parent;
        HashMap<Character, Node<V>> parentChildren;

        // tail 不存在子字符结点时，需要沿 tail 结点往前删除字符结点
        while ((parent = tail.parent) != null) {
            parentChildren = parent.getChildren(); // 父结点获取所有出度边
            parentChildren.remove(tail.character); // 父结点删除子字符对应的出度边

            // 父结点删除子字符结点引用时，如果：
            // 父结点还存在其他子字符结点的引用( children 不为空)或
            // 父结点本身就是某个单词的尾字符结点则停止向上删除
            if (!parentChildren.isEmpty() || parent.isWord) break;

            tail = parent; // 继续向上遍历
        }

        return oldValue;
    }

    /**
     * 查找单词查找树中的单词是否包含某个前缀
     *
     * @param prefix 待判断的单词前缀
     * @return 是否包含前缀
     */
    public boolean startWith(String prefix) {
        return this.findNode(prefix) != null;
    }

    /**
     * 根据传递的单词在单词查找树中查找(单词的各字符组成了一段路径)，
     * 返回查找路径上的最后一个结点(是否是代表单词结尾字符的结点不可知)
     *
     * @param word 待查找的单词
     * @return 查找路径上的最后一个结点
     */
    private Node<V> findNode(String word) {
        this.wordCheck(word);

        int len = word.length();
        Node<V> traverNode = this.root;

        for (int i = 0; i < len; i++) { // 根据单词中的每个字符进行查找
            traverNode = traverNode.getChildren().get(word.charAt(i)); // 获取代表下一个字符的结点
            if (traverNode == null) return null;
        }

        return traverNode;
    }

    private void wordCheck(String word) {
        if (word == null || word.length() == 0)
            throw new IllegalArgumentException("word must not be empty or null~");
    }

    /**
     * 代表一个字符结点
     *
     * @param <V> 单词对应的 value
     */
    private static class Node<V> {
        private boolean isWord; // 是否代表单词的结尾字符结点
        private V value; // 单词对应的 value（仅代表了单词的结尾字符结点存储了该值）
        private HashMap<Character, Node<V>> children; // 存储结点的所有出度边(每个出度边代表一个字符(Character 支持中文字符))
        private Character character; // 存储一个字符
        private Node<V> parent; // 父结点指针

        public Node(Node<V> parent, Character character) {
            this.parent = parent;
            this.character = character;
        }

        public HashMap<Character, Node<V>> getChildren() {
            return this.children == null ?
                    (this.children = new HashMap<>())
                    : this.children;
        }
    }
}
