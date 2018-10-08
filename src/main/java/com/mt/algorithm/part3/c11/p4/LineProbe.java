package com.mt.algorithm.part3.c11.p4;


import java.util.NoSuchElementException;

/**
 * @author mt Probe
 * 开放寻址法,所有的元素都放在散列表中.每个block或包含一个
 * 动态元素集合,或包含null.
 * 本类模拟线性探查
 * h(k,i) = (h'(k) + i) mod table.length
 *  i = 0,1,2....table.length-1
 * 这里只是简单的完成线性探查的过程,不涉及表的扩容
 * 辅助函数h'(k) = k
 */
public class LineProbe {

    private static final Node[] DEFAULT_TABLE = new Node[11];

    private final Node[] table = DEFAULT_TABLE;

    private static final int DEFAULT_BLOCK = -1;

    private int size;

    public void insert(int key, String value) {
        putVal(key, value);
    }

    private void putVal(int key, String value) {
        Node node = new Node(key, value);
        int index = keyIndex(key);
        int block = DEFAULT_BLOCK;
        Node n;
        // 线性探查新元素应该存放的位置
        for (int i = index; i < table.length; i++) {
            n = table[i];
            if (n == null || key == n.key) {
                block = i;
                break;
            }
        }
        // 当从index的位置往后没有查找到,从数组开始位置重新查找到index位置
        if (block == DEFAULT_BLOCK) {
            for (int i = 0; i < index; i++) {
                n = table[i];
                if (n == null || key == n.key) {
                    block = i;
                    break;
                }
            }
        }
        // 两次查找都没有找到
        if (block == DEFAULT_BLOCK) {
            // throw a exception which the table is full
        }
        table[block] = node;
        size++;
    }

    public void delete(int key) {
        deleteElement(key);
    }

    private void deleteElement(int key) {
        Node node = findNode(key);
        if (node == null) {
            throw new NoSuchElementException();
        }
        table[keyIndex(key)] = null;
        size--;
    }

    public String find(int key) {
        Node node = findNode(key);
        if (node == null) {
            return null;
        }
        return node.item;
    }

    private Node findNode(int key) {
        int index = keyIndex(key);
        Node node;
        Node find = null;
        for (int i = index; i < table.length; i++) {
            node = table[i];
            if (node != null && node.key == key) {
                find = node;
                break;
            }
        }
        if (find == null) {
            for (int i = 0; i < index; i++) {
                node = table[i];
                if (node != null && node.key == key) {
                    find = node;
                    break;
                }
            }
        }
        return find;
    }


    public int size() {
        return size;
    }

    private int keyIndex(int key) {
        return key < 0 ? 0 : (key % table.length);
    }

    class Node {
        int key;
        String item;
        Node(int key, String item) {
            this.key = key;
            this.item = item;
        }
    }

    public static void main(String[] args) {
        // 将关键字10,22,31,4,15,28,17,88,59映射到表中
        LineProbe lp = new LineProbe();
        lp.insert(10, "10");
        lp.insert(22, "22");
        lp.insert(31, "31");
        lp.insert(4, "4");
        lp.insert(15, "15");
        lp.insert(28, "28");
        lp.insert(17, "17");
        lp.insert(88, "88");
        lp.insert(59, "59");
        System.out.println("-----insert over-----");
        String find1 = lp.find(10);
        lp.delete(10);
        System.out.println("-----op over-----");

    }
}
