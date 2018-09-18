package com.mt.algorithm.part3.c10.p1;


/**
 * @author mt
 * 使用链表实现队列
 * 队列多使用链表实现
 *
 */
public class LinkedQueue<T> {

    private Node<T> head;

    private Node<T> tail;

    private int count;

    public void in(T t){
        enterQueue(t);
    }

    private void enterQueue(T t){
        if (head == null) {
            head = tail = newNode(t, null);
        } else {
            Node<T> node = newNode(t, null);
            tail.next = node;
            tail = node;
        }
        count++;
    }

    public T out(){
        return outQueue();
    }

    private T outQueue(){
        if (head == null) {
            throw new IndexOutOfBoundsException("the Queue is Empty");
        }
        if (head == tail) {
            T t = head.t;
            head = tail = null;
            count--;
            return t;
        }
        Node<T> node = head;
        head = node.next;
        node.next = null;
        count--;
        return node.t;
    }

    public int size(){
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    private Node<T> newNode(T t, Node<T> next) {
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


    public static void main(String[] args) {
        LinkedQueue<Integer> lq = new LinkedQueue<Integer>();
        lq.in(1);
        lq.in(2);
        lq.in(3);
        lq.in(4);
        System.out.println("-----------");
        System.out.println(lq.out());
        System.out.println(lq.out());
//        lq.in(5);
//        lq.in(6);
        System.out.println(lq.out());
        System.out.println(lq.out());
    }
}
