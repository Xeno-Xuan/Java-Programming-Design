package com.demo.DailyTest.Collection;

import java.util.*;

public class ForEachTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(100);
        list.add(1000);
        list.add(10000);
        list.add(100000);
        for(int i: list){
            i++;
            System.out.println(i);
        }
        for(int i = 0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        System.out.println("===========================================");
        int []a = {1,2,3,4,5};
        for(int j:a){
            j++;
            System.out.println(j);
        }
        for(int j =0;j<a.length;j++){
            System.out.println(a[j]);
        }
    }

}
