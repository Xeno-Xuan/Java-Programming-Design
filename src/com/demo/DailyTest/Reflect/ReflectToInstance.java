package com.demo.DailyTest.Reflect;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

public class ReflectToInstance {
    public static void main(String[] args) throws Exception{
        //通过IO流读取classinfo.properties文件
        //FileReader reader = new FileReader("classinfo.properties");

        //修改为通用操作,注意将文件放在类路径下
        String path = Thread.currentThread().getContextClassLoader()
                .getResource("classinfo.properties").getPath();
        FileReader reader = new FileReader(path);

        //直接以流的形式返回，不需要先行获取绝对路径
        //InputStream reader = Thread.currentThread().getContextClassLoader().getResourceAsStream("classinfo.properties");

        //创建集合对象
        Properties pros = new Properties();

        //加载
        pros.load(reader);

        //关闭流
        reader.close();

        //通过key获取value
        String className =  pros.getProperty("classname");

        //通过反射机制创建对象
        Class c = Class.forName(className);
        Object obj = c.newInstance();
        System.out.println(obj);
    }
}
/**
 * 代码不需任何修改，仅需要修改属性配置文件的内容，即可创建不同实例对象
 * 灵活性，符合OCP原则，对拓展开放，对修改关闭
 */
