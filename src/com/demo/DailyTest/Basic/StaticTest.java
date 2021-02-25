package com.demo.DailyTest.Basic;

import java.sql.SQLOutput;

public class StaticTest {
    static int value =  100;
    static {
        System.out.println("父类静态代码块1！！！");
    }
//    static {
//        value ++;
//    }
    {
        System.out.println("父类实例代码块");
        System.out.println("value = " + value);
    }
    public StaticTest(){

    }
    public StaticTest(int m){

    }
    public static void main(String[] args) {
        System.out.println("主方法执行报告");
    }

}
class StaticExtends extends StaticTest{
    static{
        System.out.println("子类static");
    }
    public StaticExtends(){
        System.out.println("子类构造方法");
    }
}

/**
 * 【执行结果】
 * 静态代码块1！！！
 * 静态代码块3，value = 101
 * 主方法执行报告
 * 【结论】
 * 静态代码块在类加载时就执行了
 */