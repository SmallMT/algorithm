package com.mt.algorithm;

/**
 * @author mt
 * 冒泡排序算法实现
 */
public class BubbleSort {

    /**
     * 非降序排序
     * @param arrs
     */
    public void sortAsc(int[] arrs){
        for (int i = 0; i < arrs.length-1; i++) {
            for (int j = i+1; j< arrs.length; j++) {
                if (arrs[i] > arrs[j]) {
                    int temp = arrs[i];
                    arrs[i] = arrs[j];
                    arrs[j] = temp;
                }
            }
        }
    }

    /**
     * 非升序排序
     * @param arrs
     */
    public void sortDesc(int[] arrs) {
        for (int i = 0; i < arrs.length-1; i++) {
            for (int j = i+1; j< arrs.length; j++) {
                if (arrs[i] < arrs[j]) {
                    int temp = arrs[i];
                    arrs[i] = arrs[j];
                    arrs[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {4,2,5,6,1,2,9};
        BubbleSort sort = new BubbleSort();
        System.out.print("乱序的数组  ");
        for (int i : nums) {
            System.out.print(i + ",");
        }
        System.out.println();
        System.out.print("非降序排序后数组  ");
        sort.sortAsc(nums);
        for (int i : nums) {
            System.out.print(i + ",");
        }
        System.out.println();
        System.out.print("非升序排序后数组  ");
        sort.sortDesc(nums);
        for (int i : nums) {
            System.out.print(i + ",");
        }

    }

}
