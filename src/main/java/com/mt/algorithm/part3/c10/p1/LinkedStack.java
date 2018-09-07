package com.mt.algorithm.part3.c10.p1;

import java.util.EmptyStackException;

/**
 * @author mt 2018.9.7
 * 使用链表模拟栈数据结构
 */
public class LinkedStack<T> {

    /** 栈顶元素*/
    private Node<T> top;

    /** 栈中元素个数*/
    private int size;

    private void pushStack(T t){
        Node<T> node;
        if (top == null) {
            node = new Node<T>(t,null);
        } else {
            node = new Node<T>(t,top);
        }
        top = node;
        size++;
    }


    public void push(T t) {
        pushStack(t);
    }


    public T pop(){
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        Node<T> node = top;
        T t = node.current;
        top = node.next;
        size--;
        return t;
    }

    public boolean isEmpty(){
        return size == 0;
    }


    class Node<T> {
        T current;
        Node<T> next;
        public Node(T current, Node<T> next){
            this.current = current;
            this.next = next;
        }
    }


    public static void main(String[] args) {
        LinkedStack<String> ls = new LinkedStack<String>();
        ls.pop();
        ls.push("a");
        ls.push("b");
        ls.push("c");
        ls.push("d");
        while (!ls.isEmpty()) {
            System.out.println(ls.pop());
        }
    }
}
