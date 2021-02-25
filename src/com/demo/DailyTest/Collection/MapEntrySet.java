package com.demo.DailyTest.Collection;
import java.util.*;
public class MapEntrySet {
    public static void main(String[] args) {
        Map<Integer,String > map = new HashMap();
        map.put(1,"zhangsan");
        map.put(2,"lisi");
        map.put(3,"wangwu");
        Set<Map.Entry<Integer,String>> set = map.entrySet();
    }

}
