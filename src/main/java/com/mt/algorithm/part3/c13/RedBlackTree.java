package com.mt.algorithm.part3.c13;

import java.util.NoSuchElementException;

/**
 * @author mt
 * @param <K>
 * @param <V>
 * 红黑树的简单实现
 * 红黑树性质(首先红黑树是一颗二叉搜索树)
 *   1.每个节点或是黑色的,或是红色的
 *   2.根节点是黑色的
 *   3.每个叶子节点都是黑色的(所有的null节点)
 *   4.如果一个节点是红色的,那么它的两个子节点都必须是黑色的
 *   5.对每个节点,从该节点到期所有的后代叶子节点的简单路径上,均包含数目相同的黑色节点
 */
public class RedBlackTree<K,V> {

    private static final boolean BLACK = true;

    private static final boolean RED = false;

    private Entry<K,V> root;

    /**
     * 以p为支点左旋
     * @param p
     */
    private void rotateLeft(Entry<K,V> p) {
        if (p == null) {
            return;
        }
        Entry<K,V> r = p.right;
        p.right = r.left;
        if (r.left != null) {
            r.left.parent = p;
        }
        r.parent = p.parent;
        if (p.parent == null) {
            root = r;
        } else if (p == p.parent.left) {
            p.parent.left = r;
        } else {
            p.parent.right = r;
        }
        r.left = p;
        p.parent = r;

    }

    /**
     * 以p为支点右旋
     * @param p
     */
    private void rotateRight(Entry<K,V> p) {
        if (p == null) {
            return;
        }
        Entry<K,V> l = p.left;
        p.left = l.right;
        if (l.right != null) {
            l.right.parent = p;
        }
        l.parent = p.parent;
        if (p.parent == null) {
            root = l;
        } else if (p == p.parent.left) {
            p.parent.left = l;
        } else {
            p.parent.right = l;
        }
        l.right = p;
        p.parent = l;
    }


    public void insert(K key, V value) {
        // check if the key is null
        checkKey(key);
        rbInsert(key, value);
    }

    private void rbInsert(K key, V value) {
        Entry<K,V> t = root;
        if (t == null) {
            // if root is null
            root = new Entry<K, V>(key, value, null);
            return;
        }
        // 1.insert a new entry just like a normal search binary tree
        Entry<K,V> z = new Entry<K, V>(key, value, null);
        // the new entry is red
        z.color = RED;
        Entry<K,V> y = null;
        while (t != null) {
            y = t;
            if (compare(key, t.key) < 0) {
                t = t.left;
            } else {
                t = t.right;
            }
        }
        z.parent = y;
        if (compare(key, y.key) < 0) {
            y.left = z;
        } else {
            y.right = z;
        }
        // 2. fix the red black tree
        fixUpAfterInsert(z);
    }

    /**
     * 新增节点后原来的红黑树可能会遭到破坏,所有需要根据情况来修理一下树
     * 让其能重新成为一颗红黑树
     * 整体思路: 将红色节点上移,最后将根节点着黑色
     * @param z
     */
    private void fixUpAfterInsert(Entry<K,V> z)  {
        // 1.将红色节点上移
        while (z != null && z != root && z.parent.color == RED) {
            // z的父节点是z的祖父节点的左孩子
            if (parentOf(z) == leftOf(parentOf(parentOf(z)))) {
                // y 是z的叔叔节点
                Entry<K,V> y = rightOf(parentOf(parentOf(z)));
                if (colorOf(y) == RED) {
                    // z的叔叔节点是红色
                    // 此时的状态, z是红色的,z的父节点是红色的,z的叔叔节点是红色的
                    // z的祖父节点是黑色的
                    // 策略: 将z的父节点和叔叔节点着黑色,将z的祖父节点着红色,
                    // 将z的祖父节点变为当前节点z,继续进行判断
                    setColor(parentOf(z), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    z = parentOf(parentOf(z));
                } else {
                    // z的叔叔节点是黑色
                    if (z == rightOf(z.parent)) {
                        // z是其父节点的右孩子
                        // 策略: 将当前节点z变为其父节点,然后左旋z
                        z = parentOf(z);
                        rotateLeft(z);
                    }
                    // z是其父节点的左孩子
                    // 策略 将z的父节点着黑色,z的祖父节点着红色,然后以z的祖父节点右旋
                    setColor(parentOf(z), BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    rotateRight(parentOf(parentOf(z)));
                }
            } else {
                // z的父节点是z的祖父节点的右孩子
                // y 是z的叔叔节点
                Entry<K,V> y = leftOf(parentOf(parentOf(z)));
                if (colorOf(y) == RED) {
                    // z的叔叔节点是红色
                    // 此时的状态, z是红色的,z的父节点是红色的,z的叔叔节点是红色的
                    // z的祖父节点是黑色的
                    // 策略: 将z的父节点和叔叔节点着黑色,将z的祖父节点着红色,
                    // 将z的祖父节点变为当前节点z,继续进行判断
                    setColor(parentOf(z), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    z = parentOf(parentOf(z));
                } else {
                    // z的叔叔节点是黑色
                    if (z == leftOf(z.parent)) {
                        // z是其父节点的左孩子
                        // 策略: 将当前节点z变为其父节点,然后右旋z
                        z = parentOf(z);
                        rotateRight(z);
                    }
                    // z是其父节点的左孩子
                    // 策略 将z的父节点着黑色,z的祖父节点着红色,然后以z的祖父节点右旋
                    setColor(parentOf(z), BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    rotateLeft(parentOf(parentOf(z)));
                }
            }
        }
        // 2.将根节点着黑色
        root.color = BLACK;
    }

    public void delete(K key) {
        Entry<K,V> deleted = searchEntry(root, key);
        rbDelete(deleted);
    }

    private void rbDelete(Entry<K,V> p) {
        if (p.left != null && p.right != null) {
            Entry<K,V> s = findSuccessor(p);
            p.key = s.key;
            p.value = s.value;
            p = s;
        }
        Entry<K,V> child = p.left != null ? p.left : p.right;
        if (child != null) {

        } else if (p.parent == null) {
            root = null;
        } else {
            if (colorOf(p) == BLACK) {
                fixUpAfterDelete();
            }
            if (p == p.parent.left) {
                p.parent.left = null;
            } else if (p == p.parent.right){
                p.parent.right = null;
            }
            p.parent = null;
        }
    }

    private void fixUpAfterDelete() {

    }

    public V search(K key) {
        checkKey(key);
        return searchEntry(root, key).value;
    }

    public V successor(K k) {
        checkKey(k);
        Entry<K,V> curr = searchEntry(root, k);
        return curr == null ? null : findSuccessor(curr).value;
    }

    private Entry<K,V> findSuccessor(Entry<K,V> curr) {
        if (curr == null) {
            return null;
        }
        if (curr.right != null) {
            // 当前节点存在右子树,后继节点就是右子树中的最小关键字节点
            return minKeyEntry(curr.right);
        } else {
            Entry<K,V> p = curr.parent;
            while (p != null && p.right == curr) {
                curr = p;
                p = curr.parent;
            }
            return p;
        }
    }

    public V predecessor(K key) {
        checkKey(key);
        Entry<K,V> curr = searchEntry(root, key);
        return curr == null ? null : findPredecessor(curr).value;
    }

    private Entry<K,V> findPredecessor(Entry<K,V> curr) {
        if (curr == null) {
            return null;
        }
        if (curr.left != null) {
            return maxKeyEntry(curr.left);
        } else {
            Entry<K,V> p = curr.parent;
            while (p != null && p.left == curr) {
                curr = p;
                p = curr.parent;
            }
            return p;
        }


    }


    public K minKey() {
        return minKeyEntry(root).key;
    }

    public V minKeyValue() {
        return minKeyEntry(root).value;
    }

    /**
     * 查询以 p 为根节点的树中的最小关键字节点
     * @param p
     * @return
     */
    private Entry<K,V> minKeyEntry(Entry<K,V> p) {
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }

    public K maxKey() {
        return maxKeyEntry(root).key;
    }

    public V maxKeyValue() {
        return maxKeyEntry(root).value;
    }

    /**
     * 查询以 p 为根节点的树中的最大关键字节点
     * @param p
     * @return
     */
    private Entry<K,V> maxKeyEntry(Entry<K,V> p) {
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }

    private void checkEntry(Entry<K,V> entry) {
        if (entry == null) {
            throw new NoSuchElementException();
        }
    }

    /**
     * 从以t为根节点的树中查找 entry which key == key
     * @param t
     * @param key
     * @return
     */
    private Entry<K,V> searchEntry(Entry<K,V> t, K key) {
        while (t != null && compare(t.key, key) != 0) {
            if (compare(t.key, key) > 0) {
                t = t.left;
            } else {
                t = t.right;
            }
        }
        return t;
    }

    private static <K,V> boolean colorOf(Entry<K,V> z) {
        return z == null ? BLACK : z.color;
    }

    private static <K,V> void setColor(Entry<K,V> z, boolean c){
        if (z != null) {
            z.color = c;
        }
    }

    private static <K,V> Entry<K,V> parentOf(Entry<K,V> z) {
        return (z == null ? null: z.parent);
    }

    private static <K,V> Entry<K,V> leftOf(Entry<K,V> z) {
        return z == null ? null : z.left;
    }

    private static <K,V> Entry<K,V> rightOf(Entry<K,V> z) {
        return z == null ? null : z.right;
    }

    /**
     * check if the key is null
     * @param key
     */
    private void checkKey(K key) {
        if (key == null) {
            throw new NullPointerException("The key of RedBlack cant't be null");
        }
    }

    private int compare(Object k1, Object k2) {
        return ((Comparable<? super K>)k1).compareTo((K)k2);
    }

    private static final class Entry<K,V> {
        K key;
        V value;
        Entry<K,V> left;
        Entry<K,V> right;
        Entry<K,V> parent;
        boolean color = BLACK;

        Entry (K key, V value, Entry<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V oldVlue = this.value;
            this.value = value;
            return oldVlue;
        }
    }

    public static void main(String[] args) {
        RedBlackTree<Integer,String> rbt = new RedBlackTree<Integer, String>();
        System.out.println("begin insert");
        rbt.insert(6,"6");
        rbt.insert(1,"1");
        rbt.insert(2,"2");
        rbt.insert(5,"5");
        rbt.insert(7,"7");
        rbt.insert(12,"12");
        rbt.insert(35,"35");
        rbt.insert(8,"8");
        rbt.insert(37,"37");
        System.out.println("after insert");
        System.out.println("begin search");
        System.out.println("key 6 " + rbt.search(6));
        System.out.println("min key  " + rbt.minKey());
        System.out.println("min value  " + rbt.minKeyValue());
        System.out.println("max value  " + rbt.maxKey());
        System.out.println("max value  " + rbt.maxKeyValue());
        System.out.println("12 successor " + rbt.successor(12));
        System.out.println("12 predecessor  " + rbt.predecessor(12));
    }
}
