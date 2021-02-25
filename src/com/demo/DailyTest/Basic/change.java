package com.demo.DailyTest.Basic;

public class change {
    String str = new String("Not change!!!");
    Integer []arr = {1,2,3};
    StringBuilder strbu = new StringBuilder("StringBuilder!!!~");
    void changeString(String str){
        str = "is changed!!*";
    }
    void changeInt(Integer  x){
        x = 3;
    }
    void changeIntArr(Integer[] x){
        x[0]=100000;
        x[1] = 2021;
    }
    void changeStringBuilder(StringBuilder strb){
        strb.replace(0,strb.length(),"!!**StringBuilder");
    }

    public static void main(String[] args) {
        change ch = new change();

        //测试Integer类对象修改形参是否反映至实参
        Integer a = 2;
        System.out.println("before:  "+ a);
        ch.changeInt(a);
        System.out.println("after:  "+ a);

        //测试String类对象修改形参是否反映至实参
        ch.changeString(ch.str);
        System.out.println(ch.str);

        //测试数组对象修改形参是否反映至实参
        ch.changeIntArr(ch.arr);
        System.out.println(ch.arr[1]);

        //测试StringBuilder对象修改形参是否反映至实参
        ch.changeStringBuilder(ch.strbu);
        System.out.println(ch.strbu);
    }
}
