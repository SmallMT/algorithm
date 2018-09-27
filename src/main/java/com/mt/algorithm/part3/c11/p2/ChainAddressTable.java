package com.mt.algorithm.part3.c11.p2;

import java.util.NoSuchElementException;

/**
 * @author mt
 * 通过散列函数h(k)来计算元素关键字在表中位置.
 * 由于全域U.length > 表T.lenght 所以通过散列函数h(k)
 * 将元素映射到表中时候,就会出现冲突(两个关键字通过h(k)计算出来的值相等)
 * 这也叫哈希碰撞
 * jdk 中HashMap解决哈希碰撞就采用了链地址法
 * 散列函数h(k) 采用除法散列法(取余) jdk中的HashMap也也是采用了除法散列发
 *     h(k) = k mod m
 *     k为关键字   m为表的长度
 * 本类中不使用 % 运算符 因为 % 运算符的效率比较低
 * 使用 & 运算符来代替 % 运算符
 *     k % m = k & (m - 1)
 *     但是有一个前提就是 m 必须是 2的幂次方
 *
 * 这里简单实现一下链地址法
 *
 */
public class ChainAddressTable {

    private static final int DEFAULT_CAPACITY = 1 << 4;

    /** 定长哈希表*/
    private final Node[] table = new Node[DEFAULT_CAPACITY];

    public void insert(int key, String value) {
        putVal(key, value);
    }

    private void putVal(int key, String value) {
        Node node = new Node(key, value, null);
        int index = keyIndex(key);
        Node n = table[keyIndex(key)];
        // 检测该block是否被占用
        if (n == null) {
            // 没有被占用直接将node放入哈希表中
            table[index] = node;
            return;
        }
        // 查找该key是否已经存在表中,存在则更新,不存在则将新元素挂载到链表的尾端
        Node oldNode = findVal(key);
        if (oldNode != null) {
            oldNode.item = value;
        } else {
            Node x = n;
            while (x.next != null) {
                x = x.next;
            }
            x.next = node;
        }

    }

    public String delete(int key) {
        return delVal(key);
    }

    private String delVal(int key) {
        int index = keyIndex(key);
        Node n = table[index];
        if (n == null) {
            throw new NoSuchElementException();
        }
        Node x = n,p = n;
        do {
            if (n.key == key) {
                x = n;
                break;
            }
            p = n;
        } while ((n = n.next) != null);
        if (p == x) {
            table[index] = p.next;
        } else {
            p.next = x.next;
        }
        String value = x.item;
        x.item = null;
        x.next = null;
        return value;
    }

    public String find(int key) {
        return findVal(key).item;
    }

    private Node findVal(int key) {
        int index = keyIndex(key);
        Node node = table[index];
        if (node == null) {
            return null;
        }
        for (Node x = node; x != null; x = x.next) {
            if (x.key == key) {
                return x;
            }
        }
        return null;
    }


    private int keyIndex(int key) {
        return key <= 0 ? 0 : (key & (table.length - 1));
    }

    private Node findNode(int key, String value) {
        return null;
    }

    static class Node {
        int key;
        String item;
        Node next;
        Node(int key, String item, Node next) {
            this.key = key;
            this.item = item;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        ChainAddressTable cat = new ChainAddressTable();
        cat.insert(5, "a");
        cat.insert(2, "b");
        cat.insert(56, "c");
        cat.insert(18, "x");
        String c = cat.delete(56);
        String b = cat.delete(18);
        String a = cat.find(5);
        System.out.println("--------over-------");
    }

}
