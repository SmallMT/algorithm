package com.mt.algorithm.part3.c10.p1;

/**
 * @author mt 2018.9.7
 * 使用链表结构实现队列
 */
public class LinkedQueue<T> {

    /** 头结点*/
    private Node<T> head;

    /** 尾结点*/
    private Node<T> tail;

    /** 链表中元素数量*/
    private int size;

    public void add(T t) {
        Node<T> node;
        // the next node of last element is always null
        node = newNode(t, null);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public T out(){
        if (isEmpty()) {
//            throw one runtime exception
        }
        Node<T> node = head;
        T t = node.t;
        head = node.next;
        node.next = null;
        size--;
        if (size == 0) {
            tail = null;
        }
        return t;
    }

    public boolean isEmpty(){
        return size == 0;
    }


    private Node<T> newNode(T t, Node<T> next){
        return new Node<T>(t, next);
    }



    class Node<T> {
        T t;
        Node<T> next;
        Node(T t, Node<T> next){
            this.t = t;
            this.next = next;
        }
    }

}
