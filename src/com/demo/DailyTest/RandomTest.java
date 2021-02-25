package com.demo.DailyTest;

import java.util.Random;

public class RandomTest {
    public static void main(String[]args){
        Random random  = new Random();
        int num;
        for(int i = 0; i < 50; i++) {
            num = random.nextInt(2);
            System.out.println(num);
        }
    }
}
/**
 * draw a conclusion:the method will return a random number which isn't minus(>=0)
 * Obviously,the conclusion I drew had been written in the official book I learn (an expensive trash)
 */
