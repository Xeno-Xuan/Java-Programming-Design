package com.demo.DailyTest.Basic;

import java.math.BigDecimal;

public class Factorial {
    public static void main(String[] args){
        long i = 26;
        BigDecimal result = BigDecimal.ONE;
        for(long j = 1; j <= i; j ++ ){
            result = result.multiply(new BigDecimal(j+""));
        }
        System.out.println(result);
    }
}
/**
 * a simple program which can be used in order to calculate the result of some numbers' factorial
 * key point: long i, long j;variable of "long" type can be used to calculate with variable of "BigDecimal" type
 */
