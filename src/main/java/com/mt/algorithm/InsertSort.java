package com.mt.algorithm;

/**
 * @author mt
 */
public class InsertSort {

    /**
     * 非降序排序
     * @param arrs 需要排序的数组
     */
    public void sortAsc(int[] arrs){
        for (int i = 1; i < arrs.length; i++) {
            int key = arrs[i];
            int j = i-1;
            while (j > -1 && arrs[j] > key) {
                arrs[j+1] = arrs[j];
                j--;
            }
            arrs[j+1] = key;
        }
    }

    /**
     * 非升序排序
     * @param arrs 需要排序的数组
     */
    public void sortDesc(int[] arrs) {
        for (int i = 1; i < arrs.length; i++) {
            int key = arrs[i];
            int j = i-1;
            while (j > -1 && arrs[j] < key){
                arrs[j+1] = arrs[j];
                j--;
            }
            arrs[j+1] = key;
        }
    }

    public static void main(String[] args) {
        int[] nums = {3,2,4,5,2,7,1};
        InsertSort sort = new InsertSort();
        System.out.print("原始数组  ");
        for (int i : nums) {
            System.out.print(i+",");
        }
        System.out.println();
        sort.sortAsc(nums);
        System.out.print("非降序排序后数组  ");
        for (int i : nums) {
            System.out.print(i+",");
        }
        System.out.println();
        sort.sortDesc(nums);
        System.out.print("非升序排序后数组  ");
        for (int i : nums) {
            System.out.print(i+",");
        }
    }
}
