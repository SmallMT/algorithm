package com.mt.algorithm.part3.c10.p1;

/**
 * @author mt 2018.9.7
 * 使用数组模拟实现一个队列(循环队列)
 * 线性队列存在"假溢出"现象
 */
public class ArrayQueue<T> {

    /** 头指针*/
    private int front;

    /** 尾指针*/
    private int rear;

    /** 队列容量实际多出一个容量来判断队列是满的还是空的*/
    private int capacity;

    /** 数组队列*/
    private T[] queue;

    private static final int DEFAULT_CAPACITY = 10;

    public ArrayQueue(){
        this(DEFAULT_CAPACITY);
    }

    public ArrayQueue(int initCapacity){
        // 浪费掉一个容量来标记队列是满的还是空的
        this.capacity = initCapacity + 1;
        queue = newArray(capacity);
    }

    @SuppressWarnings("unchecked")
    private T[] newArray(int size){
        return (T[]) new Object[size];
    }

    public void add(T t) {
        int newRear = (rear + 1) % capacity;
        if (newRear == front) {
            throw new IndexOutOfBoundsException("Queue is full");
        }
        queue[rear] = t;
        rear = newRear;
    }

    public T out(){
        if (front == rear) {
            throw new IndexOutOfBoundsException("Queue is empty");
        }
        T t = queue[front];
        queue[front] = null;
        front = (front + 1) % capacity;
        return t;
    }

    public int size(){
        int size = rear - front;
        return size > 0 ? size : (size + capacity);
    }

}
