package com.mt.algorithm.part3.c10.p2;


import java.util.NoSuchElementException;

/**
 * @author mt
 * 单链表
 */
public class SimpleLink<T> {
    /** 头结点*/
    private Node<T> head;

    /** 尾结点*/
    private Node<T> tail;

    /** 链表中存储元素的个数*/
    private int size;


    public void addLast(T t){
        linkLast(t);
    }

    public void addFirst(T t){
        linkFirst(t);
    }

    public void addAfter(int index, T t) {
        linkAfter(index, t);

    }

    private void linkAfter(int index, T t) {
        checkIndex(index);
        if (index == (size - 1)) {
            linkLast(t);
        } else {
            Node<T> target = node(index);
            target.next = newNode(t, target.next);
        }
        size++;
    }

    private void checkIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= (size - 1);
    }

    public void addBefore(int index, T t) {
        linkBefore(index, t);
    }

    private void linkBefore(int index, T t) {
        checkIndex(index);
        if (index == 0) {
            linkFirst(t);
        } else {
            Node<T> target = node(index - 1);
            target.next = newNode(t, target.next);
        }
    }


    public T remove(T t) {
        checkIsEmpty();
        return unlink(t);
    }

    public T removeFirst() {
        checkIsEmpty();
        return unlinkLast();
    }

    public T removeLast() {
        checkIsEmpty();
        return unlinkLast();
    }

    private void checkIsEmpty() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("the link is empty");
        }
    }

    private T unlink(T t) {
        Node<T> node = node(t);
        if (node == null) {
            throw new NoSuchElementException();
        }
        if (node == head) {
            return unlinkFirst();
        }
        if (node == tail) {
            return unlinkLast();
        }
        return unlinkMiddle(node);
    }

    private T unlinkFirst() {
        T element = head.t;
        Node<T> node = head;
        head = node.next;
        if (head == null) {
            tail = null;
        }
        node.next = null;
        node.t = null;
        size--;
        return element;
    }

    private T unlinkLast() {
        Node<T> node;
        // 查找倒数第二个节点
        for (node = head; node.next != tail; node = node.next) {
            if (node.next == null) {
                return unlinkFirst();
            }
        }
        T t = tail.t;
        node.next = null;
        tail = node;
        size--;
        return t;
    }

    private T unlinkMiddle(Node<T> node) {
        Node<T> next = node.next;
        if (next == tail) {
            tail = node;
        }
        node.next = next.next;
        next.next = null;
        node.t = next.t;
        size--;
        return node.t;
    }

    private Node<T> node(T t) {
        Node<T> node;
        for (node = head; node != null; node = node.next) {
            if (t.equals(node.t)) {
               return node;
            }
        }
        return null;
    }

    public T getIndex(int index) {
        checkIndex(index);
        return node(index).t;
    }

    private Node<T> node(int index) {
        if (index == (size - 1)) {
            return tail;
        }
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    public T getLast(){
       if (tail == null) {
           throw new IndexOutOfBoundsException("the link is empty");
       }
       return tail.t;
    }

    public T getFirst(){
        if (head == null) {
            throw new IndexOutOfBoundsException("the link is empty");
        }
        return head.t;
    }

    private void linkLast(T t){
        final Node<T> l = tail;
        final Node<T> newNode = newNode(t, null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void linkFirst(T t){
        final Node<T> f = head;
        final Node<T> newNode = newNode(t, f);
        head = newNode;
        if (f == null) {
            tail = newNode;
        } else {
            newNode.next = f;
        }
        size++;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    /**
     * 反转链表,返回反转后的链表头节点
     * @return 头结点
     */
    public void reverse() {
        Node<T> last = head;
        Node<T> first = reverseLink(head);
        // 反转链表后重置首尾节点
        head = first;
        tail = last;
    }

    private Node<T> reverseLink(Node<T> parent) {
        // the last node
        if (parent == null || parent.next == null) {
            return parent;
        }
        Node<T> first = reverseLink(parent.next);
        parent.next.next = parent;
        parent.next = null;
        return first;
    }

    private Node<T> newNode(T t, Node<T> next){
        return new Node<T>(t, next);
    }

    private static class Node<T> {
        T t;
        Node<T> next;
        Node(T t, Node<T> next) {
            this.t = t;
            this.next = next;
        }
    }


    public static void main(String[] args) {
        SimpleLink<Integer> sl = new SimpleLink<Integer>();
        sl.addLast(1);
        sl.addLast(2);
        sl.addLast(3);
        sl.addLast(4);
        sl.reverse();
        sl.addLast(0);
        sl.reverse();
        System.out.println("----------------");
    }
}