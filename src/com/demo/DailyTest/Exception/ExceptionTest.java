package com.demo.DailyTest.Exception;

import java.security.spec.RSAOtherPrimeInfo;

public class ExceptionTest {
    public static void main(String[] args) throws ClassNotFoundException {
        doSome1();
    }
    public static void doSome1() throws ClassNotFoundException{
        System.out.println("doSomeBegin");
        doSome2();
        System.out.println("doSomeEnd");
    }
    public static void doSome2() throws ClassNotFoundException{
        System.out.println("dosome2");
    }
}
