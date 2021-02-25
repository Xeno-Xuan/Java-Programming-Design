package com.demo.DailyTest.Basic;

public class ExtendsTest {
    public static void main(String[] args) {
        Father f = new Father();
        f.doSome();

        Father s = new Son();
        s.doSome();

    }
}
class Father {
    public static void doSome(){
        System.out.println("父类方法");
    }
    public static void doSome(int i){
        System.out.println("重写字儿");
    }
}
class Son extends Father{
    public static void doSome(){
        System.out.println("子类方法");
    }
}