package com.demo.DailyTest.Basic;

public class StaticBlockTest {
    public static void main(String[] args) {
        StaticSon ss = new StaticSon();
    }

}
class StaticFather {
    static int value =  100;
    static {
        System.out.println("父类静态代码块1！！！");
    }
        static {
        value ++;
    }
    {
        System.out.println("父类实例代码块");
//        System.out.println("value = " + value);
    }
//    public StaticFather(){
//    }
//    public StaticFather(int m){
//    }
}
class StaticSon extends StaticFather {
    static{
        System.out.println("子类static");
    }
    public StaticSon(){
        super();
        System.out.println("子类构造方法");
    }
}
/**
 * 子类继承父类要保证能够调用到父类的构造方法（1.存在   2.可调用）
 * 子类中需要使用super（paramlist）调用父类构造方法，若没有写入super语句，则JVM默认在子类第一行自动写入
 */