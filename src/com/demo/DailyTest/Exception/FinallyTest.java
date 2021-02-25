package com.demo.DailyTest.Exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FinallyTest {
    private static void method(){
        int i = 100;
        try{
            FileInputStream fis = new FileInputStream("D:/");

        }catch(FileNotFoundException  e){

        }catch(IOException e){

        }
        finally{
            i++;
            System.out.println("finally...");
        }
    };
    public static void main(String []args){

    }
}