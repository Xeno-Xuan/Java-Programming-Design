package com.demo.DailyTest.Basic;

public class CloneTest implements Cloneable{
    private int id;
    private String brand;

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneTest c1 = new CloneTest();
        CloneTest c2 = (CloneTest) c1.clone();
        System.out.println(c1.equals(c2));
    }
    public void doSome() throws CloneNotSupportedException {
        clone();
    }
}
/**
 * 调用clone()方法必须实现Cloneable接口
 * 该接口没有任何代码，只是提供一个标志的作用
 */
