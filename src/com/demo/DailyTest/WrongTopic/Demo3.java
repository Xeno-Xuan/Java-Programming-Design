package com.demo.DailyTest.WrongTopic;

public class Demo3 {
    public static void main(String[] args) {
        System.out.println(4&5);
    }
}
/**
 *  "&"的可以用作逻辑运算符，也可用于位运算：
 *  因此4&5  实际上为      0100
 *                  &     0101
 *                -------------
 *                        0100
 *故输出结果为4
 **/