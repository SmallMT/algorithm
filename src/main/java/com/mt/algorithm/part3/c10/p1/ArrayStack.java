package com.mt.algorithm.part3.c10.p1;


import java.util.EmptyStackException;

/**
 * @author mt 2018.9.6
 * 使用数组实现栈数据结构
 */
public class ArrayStack<T> {

    /** 数组模拟的栈*/
    private Object[] stack;

    /** 栈中存储元素的个数*/
    private int size;

    /** 栈顶元素的索引 初始化为指向数组的前一个位置*/
    private int top = -1;

    /** 默认栈容量为 10*/
    private static final int DEFAULT_CAPACITY = 10;

    /** 默认的空栈*/
    private static final Object[] DEFAULT_EMPTY_STACK = {};

    /** 栈容量*/
    private int capacity;

    public ArrayStack(int initialCapacity){
        if (initialCapacity > 0) {
            stack = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            stack = DEFAULT_EMPTY_STACK;
        } else {
            throw new IllegalArgumentException("illegal capacity " + initialCapacity);
        }
        this.capacity = initialCapacity;
    }

    public ArrayStack(){
        // 当不指定容量时,默认容量为10
        this(DEFAULT_CAPACITY);
    }


    private void resize(int ensureSize){
        if (stack == DEFAULT_EMPTY_STACK) {
            capacity = DEFAULT_CAPACITY;
            stack = new Object[capacity];
        }
        // 超出了数组容量,进行扩容
        if (ensureSize > capacity) {
            // 容量每次增长为原来的一倍
            capacity = stack.length << 1;
            Object[] newStack = new Object[capacity];
            System.arraycopy(stack,0,newStack,0,stack.length);
            stack = newStack;
        }

    }

    public void push(T t){
        resize(size+1);
        stack[++top] = t;
        size++;
    }

    public T pop(){
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T t = (T) stack[top];
        size--;
        // let gc do its work
        stack[top--] = null;
        return t;
    }

    public T top(){
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T) stack[top];
    }

    public void clear(){
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        clearStack();
    }

    private void clearStack(){
        for (int i = 0; i < size; i++) {
            stack[i] = null;
        }
        size = 0;
        top = -1;
    }


    private boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    /**
     * 判断栈是否为空栈
     * @return true if stack has any value
     */
    public boolean isEmptyStack(){
        return isEmpty();
    }

    public static void main(String[] args) {
        ArrayStack<Integer> as = new ArrayStack<Integer>(3);
        as.push(1);
        as.push(2);
        as.push(3);
        as.push(4);
        System.out.println(as.top());
        as.clear();
        System.out.println(as.top());
//        while (!as.isEmptyStack()) {
//            System.out.println(as.pop());
//        }
    }
}
