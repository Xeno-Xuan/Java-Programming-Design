package com.demo.DailyTest.WrongTopic;

public class Demo1 {
    public static void main(String[] args) {
        int num = 0;
        for(int i = 0; i < 100; i ++){
            num = num++;
        }
        System.out.println("num=" + num);
    }
}
/**
 * 1.需要了解jvm栈帧中的操作数栈
 * 2.无论是否循环，num = num ++；num始终等于0
 **/