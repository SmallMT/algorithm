package com.mt.algorithm;

/**
 * @author mt
 * 求解最大子数组问题
 */
public class MaxSubArray {


    public MaxAndMinInteger cossingSubArray(int[] arrs){
        int middle = arrs.length / 2;
        int leftSum = -Integer.MAX_VALUE;
        int rightSum = -Integer.MAX_VALUE;
        int maxLeft = 0;
        int maxRight = 0;
        int sum = 0;
        //求解左边最大子数组的左边下标
        for (int i = middle; i > -1; i--) {
            sum +=arrs[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }
        sum = 0;
        for (int i = middle+1; i < arrs.length; i++) {
            sum+=arrs[i];
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = i;
            }
        }
        return new MaxAndMinInteger(maxLeft,maxRight,leftSum+rightSum);
    }

    public MaxAndMinInteger maxSubArray(int[] arrs,int low,int high){
        if (low == high) {
            return new MaxAndMinInteger(low,high,arrs[low]);
        }else {
            int mid = (low + high) / 2;
            //可能在左边
            MaxAndMinInteger left = maxSubArray(arrs,low,mid);
            //可能在右边
            MaxAndMinInteger right = maxSubArray(arrs,mid+1,high);
            //可能在中间
            MaxAndMinInteger middle = cossingSubArray(arrs);
            if (left.maxSum >= right.maxSum && left.maxSum>= middle.maxSum){
                return left;
            }else if (right.maxSum >= left.maxSum && right.maxSum >= middle.maxSum) {
                return right;
            }else {
                return middle;
            }
        }

    }


    public static void main(String[] args) {
        int[] arrs = {2,-3,5,7,-2,6,-4,100};
        MaxSubArray maxSubArray = new MaxSubArray();
//        MaxAndMinInteger num = maxSubArray.cossingSubArray(arrs);
//        System.out.println(num.left);
//        System.out.println(num.right);
//        System.out.println(num.maxSum);
        MaxAndMinInteger maxSubArr = maxSubArray.maxSubArray(arrs,0,arrs.length-1);
        System.out.println(maxSubArr.left);
        System.out.println(maxSubArr.right);
        System.out.println(maxSubArr.maxSum);
    }

    class MaxAndMinInteger{
        public int left = 0;
        public int right = 0;
        public int maxSum = 0;

        public MaxAndMinInteger(int left,int right,int maxSum){
            this.left = left;
            this.right = right;
            this.maxSum = maxSum;
        }


    }
}
