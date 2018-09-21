package com.mt.algorithm.part3.c10.p2;

import java.util.NoSuchElementException;

/**
 * @author mt
 * 双端链表
 */
public class DoubleEndLink<E> {

    /** 链表头结点*/
    private Node<E> first;

    /** 链表尾节点*/
    private Node<E> last;

    /** 链表存储元素个数*/
    private int size;


    public void add(E e) {
        linkLast(e);
    }

    public void addLast(E e) {
        linkLast(e);
    }

    public void addFirst(E e) {
        linkFirst(e);
    }


    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<E>(last, e, null);
        last = newNode;
        // before link,the link list is empty
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<E>(null, e, f);
        first = newNode;
        // before link,the link list is empty
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    public void addBefore(int index, E e) {
        checkIndex(index);
        linkBefore(index, e);
    }

    public E removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("the link is empty");
        }
        return unlinkFirst();
    }

    private E unlinkFirst() {
        E element = first.item;
        Node<E> next = first.next;
        first.item = null;
        first.next = null;
        first = next;
        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }
        size--;
        return element;
    }

    public E removeLast() {
        if (last == null) {
            throw new NoSuchElementException("the link is empty");
        }
        return unlinkLast();
    }

    private E unlinkLast() {
        E element = last.item;
        Node<E> pred = last.prev;
        last.item = null;
        last.prev = null;
        last = pred;
        if (pred == null) {
            first = null;
        } else {
            pred.next = null;
        }
        size--;
        return element;
    }

    public E remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (node.item == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (o.equals(node.item)) {
                    unlink(node);
                    return true;
                }
            }
        }
        return false;
    }


    private E unlink(Node<E> node) {
        E element = node.item;
        node.item = null;
        final Node<E> pred = node.prev;
        final Node<E> next = node.next;
        if (pred == null) {
            // node is the first node
            first = next;
        } else {
            pred.next = next;
            node.prev = null;
        }
        if (next == null) {
            // node is the last node
            last = pred;
        } else {
            next.prev = pred;
            node.next = null;
        }
        size--;
        return element;
    }

    private void linkBefore(int index, E e) {
        Node<E> node = node(index);
        final Node<E> pred = node.prev;
        final Node<E> newNode = new Node<E>(pred, e, node);
        node.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private Node<E> node(int index) {
        Node<E> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    public void addAfter(int index, E e) {
        checkIndex(index);
        linkAfter(index, e);
    }

    private void linkAfter(int index, E e) {
        Node<E> node = node(index);
        final Node<E> next = node.next;
        final Node<E> newNode = new Node<E>(node, e, next);
        node.next = newNode;
        if (next == null) {
            last = newNode;
        } else {
            next.prev = newNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(" index: " + index);
        }
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= (size - 1);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;
        Node(Node<E> prev, E item, Node<E> next){
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }


    public static void main(String[] args) {
        DoubleEndLink<String> del = new DoubleEndLink<String>();
        del.add("x");
        del.removeLast();
        System.out.println("-----------------");
    }
}
