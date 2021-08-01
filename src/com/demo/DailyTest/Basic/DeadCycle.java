package com.demo.DailyTest.Basic;

public class DeadCycle {
    public static void main(String[] args) {
       int[]a = {1,2,3,4,5};
       int i = 0;
        while(true){
            System.out.println(a[i++]);
        }
    }
}
