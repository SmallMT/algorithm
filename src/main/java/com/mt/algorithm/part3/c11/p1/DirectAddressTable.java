package com.mt.algorithm.part3.c11.p1;

/**
 * @author mt
 * 直接寻址表
 * 当关键字的全域U比较小,动态集合中每个元素的关键字都是取自于
 * 全域U中,并且没有两个元素的关键字相同.
 * 此时使用直接寻址是一种简单有效的技术
 * 如果全域U很大,则在一台标准的计算机可用内存容量中,存储一张大小为U.length
 * 的一张表T不太现实.还有实际存储的关键字集合相对于全域来说可能想小的多
 * 这就导致分配给表T的大部分空间都将浪费掉.
 */
public class DirectAddressTable<E> {

    /** 直接寻址表全域*/
    private static final Integer[] U = {0,1,2,3,4,5,6,7,8,9};

    private final Object[] elements = new Object[U.length];

    public void insert(Integer key, E e) {
        Entity<E> entity = new Entity(key, e);
        elements[key] = entity;
    }

    public void delete(Integer key) {
        elements[key] = null;
    }

    public E search(Integer key) {
        Entity entity = (Entity) elements[key];
        return (E) entity.item;
    }

    static class Entity<E> {
        Integer key;
        E item;
        Entity(Integer key, E item) {
            this.key = key;
            this.item = item;
        }
    }

    public static void main(String[] args) {
        DirectAddressTable<String> dat = new DirectAddressTable<String>();
        dat.insert(U[2], "a");
        dat.insert(U[7], "b");
        dat.insert(U[4], "c");
        String str = dat.search(7);
        dat.delete(2);
        dat.delete(2);
        System.out.println("-----over------");
    }

}
