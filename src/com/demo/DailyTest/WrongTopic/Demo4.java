package com.demo.DailyTest.WrongTopic;

public class Demo4 {
    /**
     * java中提供三种数组定义方式
     * 1. Type[ ]  ArrayName = new Type[Num];
     * 2. Type[ ]  ArrayName = {..., ..., ...};
     * 3. Type[ ]  ArrayNane = new Type[ ]{..., ..., ...};
     * 第三种建议用于实参传递，如func1(new int[]{1, 2, 3})
     */
    int[] arr1 = new int[3];
    int[] arr2 = {1, 2, 3};
    int[] arr3 = new int[]{1, 2, 3};
}
