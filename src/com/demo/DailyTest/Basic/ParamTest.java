package com.demo.DailyTest.Basic;
import java.util.*;
public class ParamTest {
    public static void main(String[] args){
        int i = 100;
        int []arr = new int[1];
        arr[0] = 50;
        changeArray(arr,27);
        changeNum(i, 55);

        System.out.print("方法执行结束  主方法arr[0] = ");
        for(int index = 0; index < arr.length; index ++){
            System.out.println(arr[index]);
        }
        System.out.println("方法执行结束  主方法i = " + i);
    }
    public static void changeArray(int []arr, int key) {
        for(int i = 0; i < arr.length; i++){
            arr[i] = key;
            System.out.println("changeArray方法修改参数arr[0]= " + arr[i]);
        }
    }
    public static void changeNum(int a,int key){
        a = key;
        System.out.println("changeNum方法修改参数a = " + a);
    }
}
/**
 *
 */