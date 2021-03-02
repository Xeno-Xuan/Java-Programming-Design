package com.demo.DailyTest.WrongTopic;

public class Demo2 {
    public static void main(String[] args) {
        float f = func1();
    }
    static float func1(){
        long i = 2147483647;
        return i;
    }
//    static float func1(){
//        byte b = 2;
//        return b;
//    }
//    static float func1(){
//        short s = 2;
//        return s;
//    }
//    static float func1(){
//        long l  = 2L;
//        return l;
//    }
}
/**
 * float类型数据表示数值的范围大于long类型，
 * 故返回值类型为float的函数，其返回语句可以返回一个long类型数据
 **/